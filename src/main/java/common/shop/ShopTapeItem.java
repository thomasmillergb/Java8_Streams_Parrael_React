package common.shop;

/**
 * @author Thomas
 *         Created by Thomas on 25/10/2016.
 */
public class ShopTapeItem {
    private ShopAction shopAction;
    private ShopItem shopItem;

    public ShopTapeItem(ShopAction shopAction, ShopItem shopItem) {
        this.shopAction = shopAction;
        this.shopItem = shopItem;
    }

    public ShopAction getShopAction() {
        return shopAction;
    }

    public ShopItem getShopItem() {
        return shopItem;
    }
}
