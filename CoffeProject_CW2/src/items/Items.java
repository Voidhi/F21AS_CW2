package items;

import exceptions.*;

public abstract class Items {
	
	/**
	 * Attributes
	 */
	private String _ID;
	private float _pricePerUnit;
	private String _description;
	private String _Name;
	
	/**
	 * Constructor 1
	 * @param id
	 * @param price
	 * @param desc
	 * @param name
	 * @throws InvalidIDException 
	 * @throws InvalidPriceException 
	 */
	public Items(String id,String name,float price,String desc) throws InvalidIDException, InvalidPriceException{
		if( id.length() != 6)
			throw new InvalidIDException("Invalid ID : ID length must be of 6");
		// to check if ends with 3 numbers :
		try {
			Integer.parseInt(id.substring(id.length()-3, id.length()-1));
		}catch(NumberFormatException e) {
			throw new InvalidIDException("Invalid ID : does not end with numbers");
		}	
		if( price<=0 )
			throw new InvalidPriceException(price);		
		
		this._ID = id;
		this._pricePerUnit = price;
		this._description = desc;
		this._Name = name;
	}
	/**
	 * Constructor 2
	 * @param id
	 * @param price
	 * @param name
	 * @throws InvalidPriceException 
	 * @throws InvalidIDException 
	 */
	public Items(String id, String name, float price) throws InvalidIDException, InvalidPriceException {
		this(id,name,price,"");
	}
	
	/*
	 * Getters and Setters :
	 */
	public String get_ID() {
		return _ID;
	}
	public float get_pricePerUnit() {
		return _pricePerUnit;
	}
	public void set_pricePerUnit(float _pricePerUnit) {
		this._pricePerUnit = _pricePerUnit;
	}
	public String get_description() {
		return _description;
	}
	public void set_description(String _description) {
		this._description = _description;
	}
	public String get_Name() {
		return _Name;
	}
	public void set_Name(String _Name) {
		this._Name = _Name;
	}

	
}