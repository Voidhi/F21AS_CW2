package models;

public class Server {
	public String name;
	public String getName() {return name;}	
	
	public Customer isServingWho;
	public Customer getIsServingWho() {return isServingWho;}
	
	public Server(String name) {
		this.name = name;
	}
	
	
	
	public void assignNewCustomer(Customer c) throws InterruptedException {
		this.isServingWho = c;
	}
	public void makeAvailable() {
		this.isServingWho = null;
	}

	
	
	
	
}
