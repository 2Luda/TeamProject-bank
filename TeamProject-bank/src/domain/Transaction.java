package domain;

import java.time.LocalDateTime;


/**
 * Transaction 도메인 클래스
 * Transaction 객체의 속성 명세
 */

public class Transaction {

    //region Fields
    private String nameOfBank;
    private String accountNumber;
    private long amountOfTransaction;
    private LocalDateTime transactionTime;

    //endregion


    //region Constructor
    public Transaction(String nameOfBank, String accountNumber, long amountOfTransaction, LocalDateTime transactionTime){
        this.nameOfBank = nameOfBank;
        this.accountNumber = accountNumber;
        this.amountOfTransaction = amountOfTransaction;
        this.transactionTime = transactionTime;
    }
    //endregion


    //region Methods
    @Override
    public String toString() {

        String value = "";
        value += "-----------------------------\n";
        value += "은행명 : " + this.nameOfBank + "\n";
        value += "계좌번호 : " + this.accountNumber + "\n";
        value += "입출금내역 : " + this.amountOfTransaction + "\n";
        value += "거래일시 : " + this.transactionTime.toString() + "\n";
        return value;
    }
    //endregion

}
