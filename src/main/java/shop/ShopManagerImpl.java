package shop;

import common.person.Person;
import common.shop.ShopAction;
import common.shop.ShopItem;
import common.shop.ShopTapeItem;
import common.transactions.Transaction;
import common.transactions.TransactionResult;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.springframework.beans.factory.annotation.Autowired;
import transaction.TransactionsManager;
import transaction.TransactionsManagerImpl;

import java.util.List;
import java.util.Optional;

/**
 * @author Thomas Created by Thomas on 25/10/2016.
 */
public class ShopManagerImpl implements ShopManager {

    @Autowired
    TransactionsManager transactionsManager;

    public ShopManagerImpl(long delay, double chance) {
        transactionsManager = new TransactionsManagerImpl(delay, chance);
    }

    public Transaction buy(ShopItem shopItem, Person person) {
        person.setBalance(person.getBalance() - shopItem.getPrice());
        //System.out.println("Person: " + person.getName() + "Sold a item for " + shopItem.getPrice() + " the balance is now " + person.getBalance());
        return transactionsManager.performAction(shopItem, person);
    }

    public Transaction sell(ShopItem shopItem, Person person) {
        person.setBalance(person.getBalance() + shopItem.getPrice());
//        System.out.println("Person: " + person.getName() + "Sold a item for " + shopItem.getPrice() + " the balance is now " + person.getBalance());
        return transactionsManager.performAction(shopItem, person);
    }

    public Transaction refund(ShopItem shopItem, Person person) {
        person.setBalance(person.getBalance() + shopItem.getPrice());
//        System.out.println("Person: " + person.getName() + "Sold a item for " + shopItem.getPrice() + " the balance is now " + person.getBalance());
        return transactionsManager.performAction(shopItem, person);
    }

    public TransactionResult buyBulk(List<ShopItem> shopItems, Person person) {
        System.out.println("------------ Bulk Action  buyBulk------------");
        Optional<Transaction> reduce = shopItems.stream()
                .map(item -> buy(item, person))
                .filter(trams -> trams.getTransactionResult().equals(TransactionResult.FAIL))
                .limit(1)
                .findFirst();

        System.out.println("------------ End Bulk Action buyBulk ------------");
        return getResult(reduce);
    }

    public TransactionResult buyParallelBulk(List<ShopItem> shopItems, Person person) {
        System.out.println("------------ Bulk Action  buyBulk Parallel------------");
        Optional<Transaction> reduce = shopItems.parallelStream()
                .map(item -> buy(item, person))
                .filter(trams -> trams.getTransactionResult().equals(TransactionResult.FAIL))
                .limit(1)
                .findFirst();

        System.out.println("------------ End Bulk Action buyBulk Parallel ------------");
        return getResult(reduce);
    }

    public TransactionResult sellBulk(List<ShopItem> shopItems, Person person) {
        System.out.println("------------ Bulk Action  sellBulk------------");
        Optional<Transaction> reduce = shopItems.parallelStream()
                .map(item -> sell(item, person))
                .filter(trams -> trams.getTransactionResult().equals(TransactionResult.FAIL))
                .limit(1)
                .findFirst();

        System.out.println("------------ End Bulk Action sellBulk ------------");
        return getResult(reduce);
    }

    public TransactionResult refundBulk(List<ShopItem> shopItems, Person person) {
        System.out.println("------------ Bulk Action  refundBulk------------");
        Optional<Transaction> reduce = shopItems.parallelStream()
                .map(item -> refund(item, person))
                .filter(trams -> trams.getTransactionResult().equals(TransactionResult.FAIL))
                .limit(1)
                .findFirst();

        System.out.println("------------ End Bulk Action refundBulk ------------");
        return getResult(reduce);
    }

    public TransactionResult getResult(Optional<Transaction> transaction) {
        return transaction.isPresent() ? transaction.get().getTransactionResult() : TransactionResult.SUCCESS;
    }
    public TransactionResult tapeBulk(final List<ShopTapeItem> shopItem, Person person) {

        Optional<Transaction> reduce = shopItem.parallelStream()
                .filter(item -> ShopAction.BUY.equals(item.getShopAction()))
                .map(item -> buy(item.getShopItem(), person))
                .filter(trams -> trams.getTransactionResult().equals(TransactionResult.FAIL))
                .limit(1)
                .findFirst();

        Optional<Transaction> reduce1 = shopItem.parallelStream()
                .filter(item -> ShopAction.SELL.equals(item.getShopAction()))
                .map(item -> sell(item.getShopItem(), person))
                .filter(trams -> trams.getTransactionResult().equals(TransactionResult.FAIL))
                .limit(1)
                .findFirst();

        Optional<Transaction> reduce2 = shopItem.parallelStream()
                .filter(item -> ShopAction.REFUND.equals(item.getShopAction()))
                .map(item -> refund(item.getShopItem(), person))
                .filter(trams -> trams.getTransactionResult().equals(TransactionResult.FAIL))
                .limit(1)
                .findFirst();


        return getResult(reduce) == TransactionResult.FAIL || getResult(reduce1) == TransactionResult.FAIL || getResult(reduce2) == TransactionResult.FAIL ? TransactionResult.FAIL: TransactionResult.SUCCESS ;
    }
    public TransactionResult tapeBulkV2(final List<ShopTapeItem> shopItem, Person person) {

        Optional<Transaction> reduce = shopItem.parallelStream()
                .map(item -> tapeAction(item, person))
                .filter(trams -> trams.getTransactionResult().equals(TransactionResult.FAIL))
                .limit(1)
                .findFirst();

        return null;
    }
    private Transaction tapeAction(ShopTapeItem shopTapeItem, Person person) {
        Transaction transaction = null;

        switch (shopTapeItem.getShopAction()) {
            case REFUND:
                transaction = refund(shopTapeItem.getShopItem(), person);
                break;
            case BUY:
                transaction = buy(shopTapeItem.getShopItem(), person);
                break;
            case SELL:
                transaction = sell(shopTapeItem.getShopItem(), person);
                break;
        }
        return transaction;

    }

}
