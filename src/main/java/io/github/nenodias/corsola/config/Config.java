package io.github.nenodias.corsola.config;

import com.netflix.graphql.dgs.client.MonoGraphQLClient;
import com.netflix.graphql.dgs.client.WebClientGraphQLClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class Config {

    @Bean
    public WebClient webClient(final @Value("${pokemon.graphql.url}") String url) {
        return WebClient.create(url);
    }

    @Bean
    public WebClientGraphQLClient webClientGraphQLClient(final @Autowired WebClient webClient) {
        return MonoGraphQLClient.createWithWebClient(webClient);
    }
}
