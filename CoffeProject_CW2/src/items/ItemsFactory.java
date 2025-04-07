package items;

import exceptions.InvalidIDException;
import exceptions.InvalidPriceException;

public interface ItemsFactory {
    Items createItem(String id,String name,float price,String desc) throws InvalidPriceException, InvalidIDException;
}
