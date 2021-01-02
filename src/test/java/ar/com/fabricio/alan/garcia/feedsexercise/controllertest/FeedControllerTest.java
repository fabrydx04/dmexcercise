package ar.com.fabricio.alan.garcia.feedsexercise.controllertest;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import ar.com.fabricio.alan.garcia.feedsexercise.dto.response.FeedResponseDto;
import ar.com.fabricio.alan.garcia.feedsexercise.model.Feed;
import ar.com.fabricio.alan.garcia.feedsexercise.repository.FeedRepository;
import ar.com.fabricio.alan.garcia.feedsexercise.service.impl.FeedServiceImpl;
import ar.com.fabricio.alan.garcia.feedsexercise.utils.JsonResponseUtil;
import ar.com.fabricio.alan.garcia.feedsexercise.utils.ObjectProvider;
import ar.com.fabricio.alan.garcia.feedsexercise.utils.PlaceHolderValues;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource({ "classpath:application-test.properties" })
public class FeedControllerTest {
	private static final String SIZE = "size";
	private static final String PAGE = "page";

	@Autowired
	private MockMvc mvc;

	@MockBean
	private FeedRepository feedRepository;

	@MockBean
	private FeedServiceImpl feedService;

	@Test
	public void whenFindAllFeedsThenReturnAPageWithFeeds() throws Exception {
		doReturn(ObjectProvider.pageOfFeeds(0, 1)).when(feedRepository).findAll(PageRequest.of(0, 1));
		doReturn(ObjectProvider.pageOfFeeds(0, 1)).when(feedService).getPaginatedFeeds(PageRequest.of(0, 1));
		getMvc().perform(get(PlaceHolderValues.BASE_URL + PlaceHolderValues.GET_PATH)
				.contentType(MediaType.APPLICATION_JSON).param(PAGE, "0").param(SIZE, "1")).andExpect(status().isOk())
				.andExpect(jsonPath(JsonResponseUtil.JSON_FEED_ID_P0, is(PlaceHolderValues.ID.intValue())))

				.andExpect(jsonPath(JsonResponseUtil.JSON_FEED_TITLE_P0, is(PlaceHolderValues.TITLE)))
				.andExpect(jsonPath(JsonResponseUtil.JSON_FEED_DESCRIPTION_P0, is(PlaceHolderValues.DESCRIPTION)))
				.andExpect(jsonPath(JsonResponseUtil.JSON_FEED_IMAGE_P0, is(PlaceHolderValues.IMAGE)))

				.andDo(MockMvcResultHandlers.print());
	}

	public MockMvc getMvc() {
		return mvc;
	}

}
