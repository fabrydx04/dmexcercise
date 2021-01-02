package ar.com.fabricio.alan.garcia.feedsexercise.servicetest;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;
import java.util.concurrent.ExecutorService;

import org.apache.logging.log4j.Logger;
import org.hibernate.exception.JDBCConnectionException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.util.ReflectionTestUtils;

import ar.com.fabricio.alan.garcia.feedsexercise.adapter.NosJournalFeedAdapter;
import ar.com.fabricio.alan.garcia.feedsexercise.dto.response.FeedResponseDto;
import ar.com.fabricio.alan.garcia.feedsexercise.exception.DatabaseConnectionException;
import ar.com.fabricio.alan.garcia.feedsexercise.exception.FeedErrorException;
import ar.com.fabricio.alan.garcia.feedsexercise.exception.PeriodicalFetchingSystemException;
import ar.com.fabricio.alan.garcia.feedsexercise.model.Feed;
import ar.com.fabricio.alan.garcia.feedsexercise.repository.FeedRepository;
import ar.com.fabricio.alan.garcia.feedsexercise.service.impl.FeedServiceImpl;
import ar.com.fabricio.alan.garcia.feedsexercise.service.impl.NosJournalConsumerService;
import ar.com.fabricio.alan.garcia.feedsexercise.utils.ObjectProvider;

@SpringBootTest
@TestPropertySource({ "classpath:application-test.properties" })
public class FeedServiceTest {
	@InjectMocks
	private NosJournalConsumerService service;

	@Autowired
	private NosJournalFeedAdapter adapter;

	@Autowired
	private Logger logger;

	@Autowired
	private ModelMapper modelMapper;

	@Mock
	private FeedRepository repository;

	@InjectMocks
	private FeedServiceImpl feedService;

	@Mock
	private MessageSource messageSource;

	@Autowired
	@Qualifier("fixedThreadPool")
	private ExecutorService executorService;

	private void setFields() throws MalformedURLException {
		ReflectionTestUtils.setField(service, "feedSourceUrl", new URL("http://feeds.nos.nl/nosjournaal?format=xml"));
		ReflectionTestUtils.setField(service, "nosJournalFeedAdapter", adapter);
		ReflectionTestUtils.setField(feedService, "logger", logger);
		ReflectionTestUtils.setField(feedService, "executorService", executorService);
		ReflectionTestUtils.setField(feedService, "nosJournalConsumer", service);
		ReflectionTestUtils.setField(feedService, "modelMapper", modelMapper);
		ReflectionTestUtils.setField(feedService, "refreshIntervalInMiliSeconds", 5000);
		ReflectionTestUtils.setField(feedService, "timeout", 500);
	}

	@Test
	public void whenGetAllFeedsDontThrowAnyException() throws Exception {
		setFields();
		doReturn(Optional.of(ObjectProvider.createFeed())).when(repository).findByTitle(Mockito.anyString());
		doReturn(ObjectProvider.createFeed()).when(repository).save(Mockito.any(Feed.class));
		feedService.processFeeds();
	}

	@Test
	public void whenGetAllFeedsThenReturnAPageOfFeeds() throws Exception {
		setFields();
		doReturn(ObjectProvider.pageOfFeeds(0, 1)).when(repository).findAll(PageRequest.of(0, 1));
		Page<FeedResponseDto> feeds = feedService.getPaginatedFeeds(PageRequest.of(0, 1));
		assertTrue(feeds.getNumberOfElements() != 0);
	}

	@Test
	public void whenTryToSaveANewFeedAndDatabaseConnectionIsDownThenThrowDatabaseConnectionException()
			throws MalformedURLException {
		setFields();
		doThrow(new JDBCConnectionException("", null)).when(repository).save(Mockito.any(Feed.class));
		assertThrows(DatabaseConnectionException.class, () -> feedService.processFeeds());
	}

	@Test
	public void whenSomeThreadWasKilledThenThrowPeriodicalFetchingSystemException() throws MalformedURLException {
		setFields();
		Thread.currentThread().interrupt();
		assertThrows(PeriodicalFetchingSystemException.class, () -> feedService.processFeeds());
	}

	@Test
	public void whenSomeThingWasWrongTryingTogetNewSourcesThenThrowPeriodicalFetchingSystemException()
			throws MalformedURLException {
		setFields();
		ReflectionTestUtils.setField(service, "feedSourceUrl", new URL("http://feeeeds.nos.nl/nosjournaal?format=xml"));
		assertThrows(PeriodicalFetchingSystemException.class, () -> feedService.processFeeds());
	}

	@Test
	public void whenWeHaveAnUnknownErrorThenThrowFeedErrorException() throws MalformedURLException {
		setFields();
		ReflectionTestUtils.setField(feedService, "executorService", null);
		assertThrows(FeedErrorException.class, () -> feedService.processFeeds());
	}

}
