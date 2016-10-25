package transaction;

import common.person.Person;
import common.transactions.Transaction;
import common.transactions.TransactionItem;
import common.transactions.TransactionResult;

import java.util.concurrent.TimeUnit;

/**
 * @author Thomas
 *         Created by Thomas on 25/10/2016.
 */
public class TransactionsManagerImpl implements TransactionsManager{
    private static long delay;
    private static double chance;

    public TransactionsManagerImpl(long delay, double chance) {
        this.delay = delay;
        this.chance = chance;
    }

    @Override
    public Transaction performAction(TransactionItem transactionItem, Person person) {
        try {
            TimeUnit.NANOSECONDS.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new Transaction(randomBoolean() ? TransactionResult.FAIL : TransactionResult.SUCCESS);
    }

    private boolean randomBoolean(){
        return Math.random() < chance;
    }

}
