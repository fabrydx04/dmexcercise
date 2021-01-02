package ar.com.fabricio.alan.garcia.feedsexercise.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import ar.com.fabricio.alan.garcia.feedsexercise.dto.response.FeedResponseDto;

public interface FeedService {

	void processFeeds() throws Exception;

	Page<FeedResponseDto> getPaginatedFeeds(PageRequest pageRequest);

}