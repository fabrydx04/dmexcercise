package ar.com.fabricio.alan.garcia.feedsexercise.exception;

import javassist.NotFoundException;

public class DatabaseConnectionException extends NotFoundException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DatabaseConnectionException(String msg) {
		super(msg);
	}

}
