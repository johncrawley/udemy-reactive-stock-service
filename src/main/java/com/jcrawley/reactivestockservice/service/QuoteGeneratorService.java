package com.jcrawley.reactivestockservice.service;

import java.time.Duration;

import com.jcrawley.reactivestockservice.model.Quote;

import reactor.core.publisher.Flux;

public interface QuoteGeneratorService {

	Flux<Quote> fetchQuoteStream(Duration period);
	
}
