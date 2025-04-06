package items;

import exceptions.InvalidIDException;
import exceptions.InvalidPriceException;

public class ColdDrink extends Items{

	public ColdDrink(String id, String name, float price, String desc) throws InvalidIDException, InvalidPriceException {
		super(id, name, price, desc);
		if(!id.startsWith("CDR"))
			throw new InvalidIDException(id, this.getClass() );
	}

	public ColdDrink(String id, String name, float price) throws InvalidIDException, InvalidPriceException {
		this(id, name, price,"");
	}	
	
}
