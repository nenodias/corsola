package io.github.nenodias.corsola.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import io.github.nenodias.corsola.domain.PokemonService;
import io.github.nenodias.corsola.utils.PokemonGenerations;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class ExampleRunner implements CommandLineRunner {

    private final PokemonService pokemonService;

    private final PokemonGenerations pokemonGenerations;

    @Override
    public void run(String... args) throws Exception {
        this.consumeMany();
    }

    public void consumeOne(){
        final Integer number = 1;
        pokemonService.fetchOnePokemon(number)
                .doOnEach(System.out::println)
                .block();
    }

    private void consumeMany(){
        pokemonGenerations.generateKanto()
                .as(pokemonService::fetchPokemon)
                .collectList()
                .block()
                .stream()
                .peek(System.out::println);
    }
}
