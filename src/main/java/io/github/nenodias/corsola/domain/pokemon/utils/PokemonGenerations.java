package io.github.nenodias.corsola.domain.pokemon.utils;

import org.springframework.stereotype.Component;

import java.util.stream.IntStream;

import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

@Component
public class PokemonGenerations {

    public Flux<Integer> generateKanto() {
        return Flux.create((FluxSink<Integer> fluxSink) -> {
            IntStream.range(1, 151)
                    .forEach(fluxSink::next);
        });
    }

    public Flux<Integer> generateJohto() {
        return Flux.create((FluxSink<Integer> fluxSink) -> {
            IntStream.range(151, 251)
                    .forEach(fluxSink::next);
        });
    }
}
