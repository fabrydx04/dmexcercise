package ar.com.fabricio.alan.garcia.feedsexercise.servicetest;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URL;
import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.util.ReflectionTestUtils;

import ar.com.fabricio.alan.garcia.feedsexercise.adapter.NosJournalFeedAdapter;
import ar.com.fabricio.alan.garcia.feedsexercise.model.Feed;
import ar.com.fabricio.alan.garcia.feedsexercise.service.impl.NosJournalConsumerService;

@SpringBootTest
@TestPropertySource({ "classpath:application-test.properties" })
public class NosJournalConsumerServiceTest {

	@InjectMocks
	private NosJournalConsumerService service;

	@Autowired
	private NosJournalFeedAdapter adapter;

	@Mock
	private MessageSource messageSource;

	@Autowired
	@Qualifier("fixedThreadPool")
	private ExecutorService executorService;

	@Test
	public void whenGetAllFeedsThenReturnAnNonEmptyCollectionOfFields() throws Exception {
		ReflectionTestUtils.setField(service, "feedSourceUrl", new URL("http://feeds.nos.nl/nosjournaal?format=xml"));

		ReflectionTestUtils.setField(service, "nosJournalFeedAdapter", adapter);

		Future<Collection<Feed>> lookForNewFeeds = executorService.submit(service);
		Collection<Feed> feeds = lookForNewFeeds.get((1000) + 500, TimeUnit.MILLISECONDS);

		assertTrue(!feeds.isEmpty());

	}

}
