package items;

import exceptions.*;

public class WarmDrink extends Items{

	public WarmDrink(String id,String name,float price,String desc) throws InvalidIDException, InvalidPriceException {	
		super(id, name, price, desc);			
		if(!id.startsWith("WDR"))
			throw new InvalidIDException(id, this.getClass() );
	}

	public WarmDrink(String id, String name, float price) throws InvalidIDException, InvalidPriceException {
		this(id, name, price,"");
	}
	
}