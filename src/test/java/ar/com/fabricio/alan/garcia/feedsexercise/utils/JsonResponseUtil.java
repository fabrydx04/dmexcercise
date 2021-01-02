package ar.com.fabricio.alan.garcia.feedsexercise.utils;

public class JsonResponseUtil {
	private static final String FEEDID = "feedId";
	private static final String TITLE = "title";
	private static final String DESCRIPTION = "description";
	private static final String IMAGE = "image";

	private static final String CONTENT = "$.content[0].";

	public static final String JSON_FEED_ID_P0 = CONTENT + FEEDID;
	public static final String JSON_FEED_TITLE_P0 = CONTENT + TITLE;
	public static final String JSON_FEED_DESCRIPTION_P0 = CONTENT + DESCRIPTION;
	public static final String JSON_FEED_IMAGE_P0 = CONTENT + IMAGE;

}
