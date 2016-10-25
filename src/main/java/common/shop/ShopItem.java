package common.shop;

import common.transactions.TransactionItem;

/**
 * @author Thomas Created by Thomas on 25/10/2016.
 */
public class ShopItem implements TransactionItem
{
    private String name;
    private float price;

    public ShopItem(String name, float price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
    }
}
