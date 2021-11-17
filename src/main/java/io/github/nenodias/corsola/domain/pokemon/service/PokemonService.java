package io.github.nenodias.corsola.domain.pokemon.service;

import io.github.nenodias.corsola.domain.pokemon.generated.types.Pokemon;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PokemonService {

    Mono<Pokemon> fetchOnePokemon(final Integer number);

    Flux<Pokemon> fetchPokemon(final Flux<Integer> number);

}
