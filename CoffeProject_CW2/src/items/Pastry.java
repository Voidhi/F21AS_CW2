package items;

import exceptions.*;

public class Pastry extends Items{

	public Pastry(String id,String name,float price,String desc) throws InvalidIDException, InvalidPriceException {
		super(id, name, price, desc);
		if(!id.startsWith("PST"))
			throw new InvalidIDException(id, this.getClass() );
	}

	public Pastry(String id, String name, float price) throws InvalidIDException, InvalidPriceException {
		this(id, name, price,"");
	}
	
}
