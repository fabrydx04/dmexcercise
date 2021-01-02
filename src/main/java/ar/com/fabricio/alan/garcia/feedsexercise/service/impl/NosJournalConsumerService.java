package ar.com.fabricio.alan.garcia.feedsexercise.service.impl;

import java.net.URL;
import java.util.Collection;
import java.util.Locale;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

import ar.com.fabricio.alan.garcia.feedsexercise.adapter.NosJournalFeedAdapter;
import ar.com.fabricio.alan.garcia.feedsexercise.exception.FeedSourceException;
import ar.com.fabricio.alan.garcia.feedsexercise.model.Feed;
import ar.com.fabricio.alan.garcia.feedsexercise.service.FeedConsumerService;

@Service("nosjournal")
public class NosJournalConsumerService implements FeedConsumerService {
	@Autowired
	private NosJournalFeedAdapter nosJournalFeedAdapter;

	@Value("${nos.journal.source.url}")
	private URL feedSourceUrl;

	@Autowired
	private MessageSource source;

	@Override
	public Collection<Feed> call() throws Exception {

		try (XmlReader reader = new XmlReader(feedSourceUrl)) {
			SyndFeed nosJournalFeeds = new SyndFeedInput().build(reader);

			// I use treeset, because I implemented a compareTo in the feed class which
			// allows sorting by date
			Collection<Feed> feeds = new TreeSet<>();

			for (int i = 0; i < nosJournalFeeds.getEntries().size(); i++) {
				// We could save the first 10 here, but if we had several data sources we should
				// change that logic and we don't want to do that

				feeds.add(nosJournalFeedAdapter.adaptFeed(nosJournalFeeds.getEntries().get(i)));
			}
			return feeds;
		} catch (Exception e) {
			throw new FeedSourceException(source.getMessage("feed.source.error", null, Locale.ENGLISH));
		}
	}
	
	

}
