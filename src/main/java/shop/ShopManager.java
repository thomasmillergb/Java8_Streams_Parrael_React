package shop;

import common.person.Person;
import common.shop.ShopItem;
import common.shop.ShopTapeItem;
import common.transactions.Transaction;
import common.transactions.TransactionResult;

import java.util.List;

/**
 * @author Thomas Created by Thomas on 25/10/2016.
 */
public interface ShopManager {
    Transaction buy(ShopItem shopItem, Person person);

    Transaction sell(ShopItem shopItem, Person person);

    Transaction refund(ShopItem shopItem, Person person);

    TransactionResult buyBulk(List<ShopItem> shopItem, Person person);

    TransactionResult buyParallelBulk(List<ShopItem> shopItem, Person person);

    TransactionResult sellBulk(List<ShopItem> shopItem, Person person);

    TransactionResult refundBulk(List<ShopItem> shopItem, Person person);

    TransactionResult tapeBulk(List<ShopTapeItem> shopItem, Person person);
    TransactionResult tapeBulkV2(List<ShopTapeItem> shopItem, Person person);
}
