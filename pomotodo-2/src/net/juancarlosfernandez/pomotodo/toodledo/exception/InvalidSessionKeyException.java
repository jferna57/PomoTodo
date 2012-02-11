package net.juancarlosfernandez.pomotodo.toodledo.exception;

public class InvalidSessionKeyException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2391857158280593882L;

	public InvalidSessionKeyException(String substring) {
		super(substring);
	}
}
