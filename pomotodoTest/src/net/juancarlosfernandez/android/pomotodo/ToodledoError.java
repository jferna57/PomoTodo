package net.juancarlosfernandez.android.pomotodo;

public class ToodledoError {
	
	private int id;
	private String message;

	public void setId(int value) {
		id = value;
	}
	
	public int getId(){
		return id;
	}

	public void setMessage(String value) {
		message = value;
	}
	
	public String getMessage(){
		return message;
	}

}
