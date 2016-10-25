import common.person.Person;
import common.shop.ShopAction;
import common.shop.ShopItem;
import common.shop.ShopTapeItem;
import org.springframework.beans.factory.annotation.Autowired;
import shop.ShopManager;
import shop.ShopManagerImpl;

import java.util.LinkedList;
import java.util.List;


/**
 * @author Thomas Created by Thomas on 25/10/2016.
 */
public class Starter
{
    @Autowired
    ShopManager shopManager;
    List<ShopItem> shopItems;
    List<ShopTapeItem> shopTapeItems;
    //The amount of items to be processed
//    final int ITEMS = 100;
    final int ITEMS = 10000;
    // The transation Dealy
//    final long TRANSATION_DEALY = 100000;
    final long TRANSATION_DEALY = 10;
//    final long TRANSATION_DEALY = 0;
    //CHANCE of failure
    final double CHANCE = 0.000001;
//    final double CHANCE = 0.90;

    public Starter() {
        shopItems = genItems();
        shopTapeItems = genTape();
        shopManager = new ShopManagerImpl(TRANSATION_DEALY, CHANCE);
    }



    public static void main(String[] args)
    {
        Person thomas = new Person("Thomas Miller", 10000000.0f);
        Starter starter = new Starter();
        buyTest(thomas, starter);
//        blukTest(thomas, starter);

    }
    private static void blukTest(Person thomas, Starter starter) {
        long startTime = System.nanoTime();
        starter.shopManager.tapeBulk(starter.shopTapeItems, thomas);
        long endTime = System.nanoTime();
        System.out.println((endTime - startTime) / 1000000 + "ms");
        System.out.println(thomas.getBalance());


        startTime = System.nanoTime();
        starter.shopManager.tapeBulkV2(starter.shopTapeItems, thomas);
        endTime = System.nanoTime();
        System.out.println((endTime - startTime) / 1000000 + "ms");
        System.out.println(thomas.getBalance());
    }

    private static void buyTest(Person thomas, Starter starter) {
        long startTime = System.nanoTime();
        starter.shopManager.buyBulk(starter.shopItems, thomas);
        long endTime = System.nanoTime();
        System.out.println((endTime - startTime) / 1000000 + "ms");
        System.out.println(thomas.getBalance());


        startTime = System.nanoTime();
        starter.shopManager.buyParallelBulk(starter.shopItems, thomas);
        endTime = System.nanoTime();
        System.out.println((endTime - startTime) / 1000000 + "ms");
        System.out.println(thomas.getBalance());
    }

    private List<ShopItem> genItems(){
        List<ShopItem> items = new LinkedList();
        for(int i = 0; i < ITEMS; i++){
            items.add(new ShopItem("Item" + i, randomWithRange(1.0f, 100.0f)));
        }
        return items;
    }

    private List<ShopTapeItem> genTape() {
        List<ShopTapeItem> items = new LinkedList();
        for(int i = 0; i < ITEMS; i++){
            ShopItem shopItem = new ShopItem("Item" + i, randomWithRange(1.0f, 100.0f));

            items.add(new ShopTapeItem(randomAction(), shopItem));
        }
        return items;
    }


    float randomWithRange(float min, float max)
    {
        float range = Math.abs(max - min);
        return (float)(Math.random() * range) + (min <= max ? min : max);
    }

    private ShopAction randomAction(){
        double random = Math.random();
        return random < 0.4 ? ShopAction.BUY : random < 0.8 ? ShopAction.SELL : ShopAction.REFUND;
    }
}
