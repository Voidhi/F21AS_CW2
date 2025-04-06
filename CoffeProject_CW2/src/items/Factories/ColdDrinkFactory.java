package items.Factories;

import exceptions.InvalidIDException;
import exceptions.InvalidPriceException;
import items.ColdDrink;
import items.Items;
import items.ItemsFactory;

public class ColdDrinkFactory implements ItemsFactory {
    @Override
    public Items createItem(String id, String name, float price, String desc) throws InvalidPriceException, InvalidIDException {
        return new ColdDrink(id, name, price, desc);
    }
}
