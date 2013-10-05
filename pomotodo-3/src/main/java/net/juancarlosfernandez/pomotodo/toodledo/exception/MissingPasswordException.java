package net.juancarlosfernandez.pomotodo.toodledo.exception;

public class MissingPasswordException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 3834583632051746271L;

    public MissingPasswordException(String substring) {
        super(substring);
    }
}
