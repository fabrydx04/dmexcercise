package ar.com.fabricio.alan.garcia.feedsexercise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ar.com.fabricio.alan.garcia.feedsexercise.dto.response.FeedResponseDto;
import ar.com.fabricio.alan.garcia.feedsexercise.service.FeedService;

@RestController
@RequestMapping(value = "${api.base.url}", produces = "application/json;charset=UTF-8")
public class FeedController {
	@Autowired
	private FeedService feedService;

	@GetMapping("${get.feeds.url}")
	public ResponseEntity<Page<FeedResponseDto>> getAllFeeds(
			@RequestParam(name = "page", defaultValue = "0", required = false) int page,
			@RequestParam(name = "size", defaultValue = "30", required = false) int size) {
		return ResponseEntity.status(HttpStatus.OK).body(feedService.getPaginatedFeeds(PageRequest.of(page, size)));
	}
}
