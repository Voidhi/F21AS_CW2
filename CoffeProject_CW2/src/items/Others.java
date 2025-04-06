package items;

import exceptions.*;

public class Others extends Items{

	public Others(String id,String name,float price,String desc) throws InvalidIDException, InvalidPriceException {
		super(id, name, price, desc);
		if(!id.startsWith("OTH"))
			throw new InvalidIDException(id, this.getClass() );
	}

	public Others(String id, String name, float price) throws InvalidIDException, InvalidPriceException {
		this(id, name, price,"");
	}
	
}
