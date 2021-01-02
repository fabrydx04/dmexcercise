package ar.com.fabricio.alan.garcia.feedsexercise.service.impl;

import java.util.Collection;
import java.util.Locale;
import java.util.TreeSet;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javax.transaction.Transactional;

import org.apache.logging.log4j.Logger;
import org.hibernate.exception.JDBCConnectionException;
import org.jsoup.Jsoup;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.google.common.base.Optional;

import ar.com.fabricio.alan.garcia.feedsexercise.dto.response.FeedResponseDto;
import ar.com.fabricio.alan.garcia.feedsexercise.exception.ContinuosFetchingSystemException;
import ar.com.fabricio.alan.garcia.feedsexercise.exception.DatabaseConnectionException;
import ar.com.fabricio.alan.garcia.feedsexercise.exception.FeedErrorException;
import ar.com.fabricio.alan.garcia.feedsexercise.model.Feed;
import ar.com.fabricio.alan.garcia.feedsexercise.repository.FeedRepository;
import ar.com.fabricio.alan.garcia.feedsexercise.service.FeedConsumerService;
import ar.com.fabricio.alan.garcia.feedsexercise.service.FeedService;

@Service
public class FeedServiceImpl implements FeedService {
	@Autowired
	@Qualifier("nosjournal")
	private FeedConsumerService nosJournalConsumer;

	@Autowired
	@Qualifier("fixedThreadPool")
	private ExecutorService executorService;

	@Value("${pull.feeds.interval.in.miliseconds}")
	private int refreshIntervalInMiliSeconds;

	@Value("${pull.feeds.timeout.in.ms}")
	private int timeout;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private FeedRepository feedRepository;

	@Autowired
	private Logger logger;

	@Autowired
	private MessageSource source;

	@Override
	@Async
	@Scheduled(fixedRateString = "${pull.feeds.interval.in.miliseconds}")
	public void processFeeds() throws Exception {
		// if we have different data sources we should include them here
		try {
			Collection<Feed> feeds = new TreeSet<>();

			logger.info("Looking for new Feeds");
			Future<Collection<Feed>> lookForNewFeeds = executorService.submit(nosJournalConsumer);
			feeds.addAll(lookForNewFeeds.get((refreshIntervalInMiliSeconds) + timeout, TimeUnit.MILLISECONDS));
			logger.info("feeds Fetched: " + feeds.size());

			// we save the last 10 new records of all data sources
			feeds.stream().limit(10).forEach(feed -> {
				storeNewFeeds(feed);
			});
		} catch (JDBCConnectionException e) {
			throw new DatabaseConnectionException(source.getMessage("database.error", null, Locale.ENGLISH));
		} catch (InterruptedException e) {
			throw new ContinuosFetchingSystemException(source.getMessage("fetch.service.error", null, Locale.ENGLISH));
		} catch (ExecutionException e) {
			throw new ContinuosFetchingSystemException(source.getMessage("fetch.service.error", null, Locale.ENGLISH));
		} catch (Exception e) {
			throw new FeedErrorException(source.getMessage("generic.error", null, Locale.ENGLISH));
		}

	}

	@Transactional
	private void storeNewFeeds(Feed feed) {
		feed.setDescription(Jsoup.parse(feed.getDescription()).text());
		
		Optional<Feed> findByTitle = feedRepository.findByTitle(feed.getTitle());
		if(findByTitle.isPresent() && !(findByTitle.get().getDescription().equalsIgnoreCase(feed.getDescription()))) {
			logger.info("Updating feed");
			feedRepository.save(findByTitle.get());
		}
		else if(!findByTitle.isPresent()){
			logger.info("Saving new feed");
			feedRepository.save(feed);
		}
	}

	@Override
	@Transactional
	public Page<FeedResponseDto> getPaginatedFeeds(PageRequest pageRequest) {
		logger.info("Getting Feeds");
		return feedRepository.findAll(pageRequest).map(feed -> modelMapper.map(feed, FeedResponseDto.class));
	}

}
