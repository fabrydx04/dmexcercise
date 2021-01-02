package ar.com.fabricio.alan.garcia.feedsexercise.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import ar.com.fabricio.alan.garcia.feedsexercise.model.Feed;

public class ObjectProvider {
	public static Feed createFeed() {
		Feed feed = new Feed();
		feed.setFeedId(1);
		feed.setTitle(PlaceHolderValues.TITLE);
		feed.setDescription(PlaceHolderValues.DESCRIPTION);
		feed.setImage(PlaceHolderValues.IMAGE);
		feed.setPublishedDate(PlaceHolderValues.CREATIONDATE);
		return feed;
	}

	public static List<Feed> collectionOfFeeds() {
		ArrayList<Feed> list = new ArrayList<>();
		Feed feed = createFeed();
		list.add(feed);
		return list;
	}

	public static Page<Feed> pageOfFeeds(int page, int size) {
		PageRequest pageRequest = PageRequest.of(page, size);
		return new PageImpl<Feed>(collectionOfFeeds(), pageRequest, collectionOfFeeds().size());
	}
}
