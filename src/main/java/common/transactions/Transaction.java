package common.transactions;

/**
 * @author Thomas
 *         Created by Thomas on 25/10/2016.
 */
public class Transaction {
    private TransactionResult transactionResult;

    public Transaction(TransactionResult transactionResult) {
        this.transactionResult = transactionResult;
    }

    public TransactionResult getTransactionResult() {
        return transactionResult;
    }

}
