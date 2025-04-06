package exceptions;

public class InvalidIDException extends Exception{
	
	public InvalidIDException(String mess) {
		super(mess);
	}
	public <T> InvalidIDException(String id, T classType) {
		super("ID: " + id + " is not a valid ID for " + classType);
	}
}
