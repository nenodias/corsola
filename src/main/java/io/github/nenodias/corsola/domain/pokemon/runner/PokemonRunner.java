package io.github.nenodias.corsola.domain.pokemon.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import io.github.nenodias.corsola.ShutdownManager;
import io.github.nenodias.corsola.domain.pokemon.generated.types.Pokemon;
import io.github.nenodias.corsola.domain.pokemon.service.PokemonService;
import io.github.nenodias.corsola.domain.pokemon.utils.PokemonGenerations;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Sinks;

@Slf4j
@RequiredArgsConstructor
@Component
public class PokemonRunner implements CommandLineRunner {

    private final PokemonService pokemonService;

    private final PokemonGenerations pokemonGenerations;

    private final Sinks.Many<Pokemon> sinkPokemon;

    private final ShutdownManager shutdownManager;

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
        pokemonGenerations.generateKanto().take(150)
                .as(pokemonService::fetchPokemon)
                .map(this::produceKafkaPokemon)
                .doOnComplete(() -> {
                    log.info("I=FINISHED");
                    shutdownManager.initiateShutdown(0);
                }).subscribe();
    }

    private Pokemon produceKafkaPokemon(final Pokemon pokemon){
        sinkPokemon.emitNext(pokemon, Sinks.EmitFailureHandler.FAIL_FAST);
        return pokemon;
    }
}
