package ar.com.fabricio.alan.garcia.feedsexercise.adapter;

import org.springframework.stereotype.Component;

import com.rometools.rome.feed.synd.SyndEntry;

import ar.com.fabricio.alan.garcia.feedsexercise.model.Feed;

@Component
public class NosJournalFeedAdapter {

	public Feed adaptFeed(SyndEntry syndEntry) {
		Feed feed = new Feed();
		feed.setTitle(syndEntry.getTitle());
		feed.setDescription(syndEntry.getDescription().getValue());
		feed.setImage(syndEntry.getEnclosures().get(0).getUrl());
		feed.setPublishedDate(syndEntry.getPublishedDate());
		return feed;
	}

}
