package ar.com.fabricio.alan.garcia.feedsexercise.exception;

import javassist.NotFoundException;

public class FeedErrorException extends NotFoundException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FeedErrorException(String msg) {
		super(msg);
	}

}
