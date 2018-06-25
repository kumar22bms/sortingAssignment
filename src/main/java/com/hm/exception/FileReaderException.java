package com.hm.exception;

public class FileReaderException  extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public FileReaderException() {
		super();
	}

	public FileReaderException(final String s) {
		super(s);
	}
	
	public FileReaderException(final Throwable t) {
		super(t);
	}
	
	public FileReaderException(final String s, final Throwable t) {
		super(s, t);
	}


}
