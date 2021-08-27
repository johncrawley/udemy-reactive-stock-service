package com.jcrawley.reactivestockservice.web;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_NDJSON;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;


@Configuration
public class QuoteRouterConfig {
	
	/*
	 * 
	 * NB to see results when server running,
	 *  you can run:
	 *  
	 *  curl http://localhost:8080/quotes -i -H "Accept: application/x-ndjson"
	 * 
	 */
	
	public static final String QUOTES_PATH = "/quotes";

	@Bean
	public RouterFunction<ServerResponse> quoteRoutes(QuoteHandler handler){
		return route().GET(QUOTES_PATH, accept(APPLICATION_JSON), handler::fetchQuotes)
					  .GET(QUOTES_PATH, accept(APPLICATION_NDJSON), handler::streamQuotes)
				.build();
	}
	
	
}
