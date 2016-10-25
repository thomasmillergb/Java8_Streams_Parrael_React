package common.shop;

/**
 * @author Thomas
 *         Created by Thomas on 25/10/2016.
 */
public enum ShopAction {
    BUY("Buy"),
    SELL("Sell"),
    REFUND("Refund");


    private final String action;

    ShopAction(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }
}
