package com.jcrawley.reactivestockservice.service;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.jcrawley.reactivestockservice.model.Quote;

import reactor.core.publisher.Flux;

public class QuoteGeneratorServiceImplTest {

	
	QuoteGeneratorService service;
	
	@BeforeEach
	void setup() {
		service = new QuoteGeneratorServiceImpl();
	}
	
	
	@Test
	void fetchQuoteStream() throws InterruptedException {
		Flux<Quote> quotesFlux = service.fetchQuoteStream(Duration.ofMillis(200l));
		Consumer<Quote> quoteConsumer = System.out::println;
		Consumer<Throwable> throwableConsumer = e -> System.out.println(e.getMessage());
		
		CountDownLatch countDownLatch = new CountDownLatch(1);
		Runnable done = () -> countDownLatch.countDown();
		
		quotesFlux.take(30)
			.subscribe(quoteConsumer, throwableConsumer, done);
		
		countDownLatch.await();
	}
}
