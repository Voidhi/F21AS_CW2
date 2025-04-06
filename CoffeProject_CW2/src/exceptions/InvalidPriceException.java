package exceptions;

public class InvalidPriceException extends Exception{
	
	public InvalidPriceException(float p) {
		super("Price: " + p + "£ is not a valid number");
	}
	
}
