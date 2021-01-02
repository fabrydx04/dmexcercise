package ar.com.fabricio.alan.garcia.feedsexercise.exception;

import javassist.NotFoundException;

public class ContinuosFetchingSystemException extends NotFoundException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ContinuosFetchingSystemException(String msg) {
		super(msg);
	}

}
