package models;

public class Server {
	public String name;
	public Customer isServingWho;
	
	public void makeAvailable() {
		this.isServingWho = null;
	}
	
}
