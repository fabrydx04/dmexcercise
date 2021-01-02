package ar.com.fabricio.alan.garcia.feedsexercise.exception;

import javassist.NotFoundException;

public class PeriodicalFetchingSystemException extends NotFoundException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PeriodicalFetchingSystemException(String msg) {
		super(msg);
	}

}
