package items.Factories;

import exceptions.InvalidIDException;
import exceptions.InvalidPriceException;
import items.Items;
import items.ItemsFactory;
import items.Others;

public class OthersFactory implements ItemsFactory {
    @Override
    public Items createItem(String id, String name, float price, String desc) throws InvalidPriceException, InvalidIDException {
        return new Others(id, name, price, desc);
    }
}
