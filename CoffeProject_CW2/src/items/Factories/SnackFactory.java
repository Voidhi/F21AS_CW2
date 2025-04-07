package items.Factories;

import exceptions.InvalidIDException;
import exceptions.InvalidPriceException;
import items.Items;
import items.ItemsFactory;
import items.Snack;

public class SnackFactory implements ItemsFactory {
    @Override
    public Items createItem(String id, String name, float price, String desc) throws InvalidPriceException, InvalidIDException {
        return new Snack(id, name, price, desc);
    }
}
