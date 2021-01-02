package ar.com.fabricio.alan.garcia.feedsexercise.exception;

import javassist.NotFoundException;

public class FeedSourceException extends NotFoundException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FeedSourceException(String msg) {
		super(msg);
	}

}
