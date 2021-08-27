package com.jcrawley.reactivestockservice;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.jcrawley.reactivestockservice.model.Quote;

@SpringBootTest
class UdemyReactiveStockServiceApplicationTests {


	@Autowired WebTestClient webTestClient;
	
	@Test
	public void testFetchQuotes() {
		webTestClient.get()
			.uri("/quotes?size=20")
			.accept(MediaType.APPLICATION_JSON)
			.exchange()
			.expectStatus().isOk()
			.expectHeader().contentType(MediaType.APPLICATION_JSON)
			.expectBodyList(Quote.class)
			.hasSize(20)
			.consumeWith(allQuotes -> {
				assertThat(allQuotes.getResponseBody())
				.allSatisfy(quote -> assertThat(quote.getPrice()).isPositive());
				assertThat(allQuotes.getResponseBody()).hasSize(20);
			});
	}
	
	@Test
	public void testStreamQuotes() throws InterruptedException {

		int numberOfItems = 12;
		CountDownLatch countDownLatch = new CountDownLatch(numberOfItems);
				
		webTestClient.get()
			.uri("/quotes")
			.accept(MediaType.APPLICATION_NDJSON)
			.exchange()
			.returnResult(Quote.class)
			.getResponseBody()
			.take(numberOfItems)
			.subscribe( quote -> {
				assertThat(quote.getPrice()).isPositive();
				countDownLatch.countDown();
			});
			countDownLatch.await(numberOfItems * 250, TimeUnit.MILLISECONDS);
			assertThat(countDownLatch.getCount()).isEqualTo(0);
	}
	

}
