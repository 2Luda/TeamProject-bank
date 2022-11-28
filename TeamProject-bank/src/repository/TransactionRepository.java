package repository;

import domain.Transaction;

import java.time.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *  Transaction Repository 클래스
 *  Transaction doamin 객체인스턴스 저장 및 관리
 */


public class TransactionRepository {

    //region Singletone Pattern
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

    //endregion

    //region Field
    private ArrayList<Transaction> transactionLists;

    //endregion

    //region Methods

    /**
     * Transaction 객체 transactionLists에 추가
     * @param nameOfBank
     * @param accountNumber
     * @param amountOfTransaction
     * @param transactionTime
     */
    public void addTransaction(String nameOfBank, String accountNumber, long amountOfTransaction, LocalDateTime transactionTime) {
        Transaction transaction = new Transaction(
                nameOfBank,
                accountNumber,
                amountOfTransaction,
                transactionTime
        );

        this.transactionLists.add(transaction);
    }

    /**
     * Transaction 객체 List 리턴
     * @return
     */
    public ArrayList<Transaction> getListOfTransaction() {

        ArrayList<Transaction> value = new ArrayList<>();
        Iterator<Transaction> iteratorOfTransaction = this.transactionLists.iterator();
        while (iteratorOfTransaction.hasNext()) {
            Transaction transaction = iteratorOfTransaction.next();
            value.add(transaction);
        }
        return value;
    }

    //endregion
}


