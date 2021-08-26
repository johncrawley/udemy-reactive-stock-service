package com.jcrawley.reactivestockservice.web;

import java.time.Duration;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.jcrawley.reactivestockservice.model.Quote;
import com.jcrawley.reactivestockservice.service.QuoteGeneratorService;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@Component
public class QuoteHandler {

	private final QuoteGeneratorService quoteGeneratorService;
	
	public Mono<ServerResponse> fetchQuotes(ServerRequest request){
		int size = Integer.parseInt(request.queryParam("size").orElse("10"));
		return ok().contentType(MediaType.APPLICATION_JSON)
				.body(quoteGeneratorService.fetchQuoteStream(Duration.ofMillis(200))
					.take(size), Quote.class);		
	}
	
	
	public Mono<ServerResponse> streamQuotes(ServerRequest request){
		return ok().contentType(MediaType.APPLICATION_NDJSON)
				.body(quoteGeneratorService.fetchQuoteStream(Duration.ofMillis(200)), Quote.class);
	}
	
	
}
