package ar.com.fabricio.alan.garcia.feedsexercise.dto.response;

import java.util.Date;

public class FeedResponseDto {
	private long feedId;

	private String title;

	private String description;

	private String image;

	private Date publishedDate;

	public FeedResponseDto() {

	}

	public FeedResponseDto(long feedId, String title, String description, String image, Date publishedDate) {
		this.feedId = feedId;
		this.title = title;
		this.description = description;
		this.image = image;
		this.publishedDate = publishedDate;
	}

	public long getFeedId() {
		return feedId;
	}

	public void setFeedId(long feedId) {
		this.feedId = feedId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Date getPublishedDate() {
		return publishedDate;
	}

	public void setPublishedDate(Date publishedDate) {
		this.publishedDate = publishedDate;
	}

}
