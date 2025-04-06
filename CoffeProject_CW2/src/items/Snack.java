package items;

import exceptions.*;


public class Snack extends Items{

	public Snack(String id,String name,float price,String desc) throws InvalidIDException, InvalidPriceException {
		super(id, name, price, desc);
		if(!id.startsWith("SNK"))
			throw new InvalidIDException(id, this.getClass() );
	}

	public Snack(String id, String name, float price) throws InvalidIDException, InvalidPriceException {
		this(id, name, price,"");
	}
	
}
