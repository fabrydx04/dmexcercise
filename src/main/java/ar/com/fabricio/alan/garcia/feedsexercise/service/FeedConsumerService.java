package ar.com.fabricio.alan.garcia.feedsexercise.service;

import java.util.Collection;
import java.util.concurrent.Callable;

import ar.com.fabricio.alan.garcia.feedsexercise.model.Feed;

public interface FeedConsumerService extends Callable<Collection<Feed>> {

	Collection<Feed> call() throws Exception;

}