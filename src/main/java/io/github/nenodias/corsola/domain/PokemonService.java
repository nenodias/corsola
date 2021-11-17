package io.github.nenodias.corsola.domain;

import io.github.nenodias.corsola.generated.types.Pokemon;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PokemonService {

    Mono<Pokemon> fetchOnePokemon(final Integer number);

    Flux<Pokemon> fetchPokemon(final Flux<Integer> number);

}
