package io.github.nenodias.corsola.domain;

import com.netflix.graphql.dgs.client.GraphQLResponse;
import com.netflix.graphql.dgs.client.WebClientGraphQLClient;
import com.netflix.graphql.dgs.client.codegen.GraphQLQueryRequest;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import io.github.nenodias.corsola.generated.client.PokemonGraphQLQuery;
import io.github.nenodias.corsola.generated.client.PokemonProjectionRoot;
import io.github.nenodias.corsola.generated.types.Pokemon;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Slf4j
@Service
@RequiredArgsConstructor
public class PokemonServiceImpl implements PokemonService {


    private final WebClientGraphQLClient webClientGraphQLClient;

    @Override
    public Mono<Pokemon> fetchOnePokemon(final Integer number) {
        return fetchPokemon(Flux.just(number)).log().singleOrEmpty();
    }

    @NotNull
    private Pokemon extractValueAsPokemon(final GraphQLResponse response){
        return response.extractValueAsObject("pokemon", Pokemon.class);
    }

    @NotNull
    private GraphQLQueryRequest makeRequest(Integer number) {
        return new GraphQLQueryRequest(
                new PokemonGraphQLQuery.Builder()
                        .number(number)
                        .build(),
                new PokemonProjectionRoot()
                        .name());
    }

    @Override
    public Flux<Pokemon> fetchPokemon(Flux<Integer> number) {
        return number
                .log("I=Fetching a number")
                .map(this::makeRequest)
                .log("I=Fetching a request")
                .map(GraphQLQueryRequest::serialize)
                .log("I=Graphql query")
                .flatMap(webClientGraphQLClient::reactiveExecuteQuery)
                .map(this::extractValueAsPokemon)
                .log("I=Is there a next Pokemon?")
                .doOnError(e -> log.error("E= Error when fetching a pokemon, message={}", e.getMessage()));
    }
}
