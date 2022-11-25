package repository;


//거래내역

import domain.Transaction;

import java.time.*;
import java.util.ArrayList;
import java.util.Iterator;

public class TransactionRepository {

    //싱글톤
    private static TransactionRepository transactionRepository;
    private TransactionRepository() {
        transactionLists = new ArrayList<>();
    }
    public static TransactionRepository getInstance() {
        if (transactionRepository == null) {
            transactionRepository = new TransactionRepository();
        }
        return transactionRepository;
    }

    private ArrayList<Transaction> transactionLists;

    public void addTransaction(String nameOfBank, String accountNumber, long amountOfTransaction, LocalDateTime transactionTime) {
        Transaction transaction = new Transaction(
                nameOfBank,
                accountNumber,
                amountOfTransaction,
                transactionTime
        );

        this.transactionLists.add(transaction);
    }

    public ArrayList<Transaction> getListOfTransaction() {

        ArrayList<Transaction> value = new ArrayList<>();
        Iterator<Transaction> iteratorOfTransaction = this.transactionLists.iterator();

        while (iteratorOfTransaction.hasNext()) {
            Transaction transaction = iteratorOfTransaction.next();
            value.add(transaction);
        }
        return value;
    }
}


