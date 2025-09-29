package com.tnahsin.answerService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class AnswerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnswerServiceApplication.class, args);
	}


    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        return builder.build();
    }

    @Bean
    @LoadBalanced
    public WebClient userServiceWebClient(WebClient.Builder webClientBuilder) {
        return webClientBuilder
                .baseUrl("http://USER-SERVICE")
                .build();
    }

    @Bean
    @LoadBalanced
    public WebClient.Builder questionServiceWebClientBuilder() {
        return WebClient.builder()
                .baseUrl("http://QUESTION-SERVICE");
    }
}
