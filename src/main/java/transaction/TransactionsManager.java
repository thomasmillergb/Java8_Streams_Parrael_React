package transaction;

import common.person.Person;
import common.transactions.Transaction;
import common.transactions.TransactionItem;

/**
 * The interface Transactions manager.
 *
 * @author Thomas          Created by Thomas on 25/10/2016.
 */
public interface TransactionsManager {
    Transaction performAction(TransactionItem transactionItem, Person person);

}
