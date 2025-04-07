package items.Factories;

import exceptions.InvalidIDException;
import exceptions.InvalidPriceException;
import items.Items;
import items.ItemsFactory;
import items.Pastry;

public class PastryFactory implements ItemsFactory {
    @Override
    public Items createItem(String id, String name, float price, String desc) throws InvalidPriceException, InvalidIDException {
        return new Pastry(id, name, price, desc);
    }
}
