package io.github.nenodias.corsola.domain.pokemon.event;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Supplier;

import io.github.nenodias.corsola.domain.pokemon.generated.types.Pokemon;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Configuration
@Slf4j
public class PokemonKafkaEvent {

    @Bean
    public Sinks.Many<Pokemon> sinkPokemon() {
        return Sinks.many().unicast().onBackpressureBuffer();
    }

    @Bean
    public Supplier<Flux<Pokemon>> importPokemon(Sinks.Many<Pokemon> sinkPokemon) {
        return () -> sinkPokemon.asFlux()
                .doOnNext(m -> log.info("I=Manually sending message, message={}", m))
                .doOnError(t -> log.error("E=Error encountered, error={}", t));
    }

}
