package service;

import domain.BankAccount;
import domain.Transaction;
import repository.BankAccountRepository;
import repository.TransactionRepository;
import exceptions.IllegalRegexExpressionException;
import exceptions.NoAccountException;
import exceptions.NotEnoughMoneyException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;

public class BankService{

    //region Fields

    private final String bankName;
    private final int commission;
    private final BigDecimal interestRate;
    private BankAccountRepository bankAccountRepository;
    private TransactionRepository transactionRepository;

    //endregion


    //region Constructor
    public BankService(String bankName, int commission, BigDecimal interestRate) {
        this.bankName = bankName;
        this.commission = commission;
        this.interestRate = interestRate;
        bankAccountRepository = BankAccountRepository.getInstance();
        transactionRepository = TransactionRepository.getInstance();
    }

    //endregion


    //region Getters

    public BigDecimal getInterestRate(){
        return this.interestRate;
    }
    public int getCommission() {
        return this.commission;
    }

    public String getBankName(){return this.bankName;}

    //endregion


    //region Manage Account Methods

    /**
     * 계좌 등록 메소드
     *
     * @param bankOwnerName     (String)
     * @param bankAccountNumber (String)
     * @param bankBalance       (long)
     */
    public void addAccount(String bankOwnerName, String bankAccountNumber, long bankBalance, String password) {
        bankAccountRepository.addAccount(this.bankName, bankOwnerName, bankAccountNumber, bankBalance, password);
    }

    /**
     * 계좌번호 중복검사
     * @param bankAccountNumber
     * @return
     */
    public boolean isExistBankAccountNumber(String bankAccountNumber){
        if(this.bankAccountRepository.searchAccountsByNumber(bankAccountNumber) == null)
            return false;
        return true;
    }

    /**
     * 계좌 수정 메소드
     *
     * @param bankAccountNumber     (String)
     * @param currentPassword (String)
     * @param nextPassowrd          (String)
     */
    public boolean changePassword(String bankAccountNumber, String currentPassword, String nextPassowrd) {

        try {
            BankAccount account = this.bankAccountRepository.searchAccountsByNumber(bankAccountNumber);

            if (account == null)
                throw new NoAccountException();
            if (account.checkPassword(currentPassword) == false)
                throw new NoAccountException("비밀번호가 일치하지 않습니다");

            return bankAccountRepository.modifyAccount(bankAccountNumber,nextPassowrd);

        } catch (NoAccountException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * 계좌 삭제 메소드
     * @param bankAccountNumber (String)
     */
    public boolean deleteAccount(String bankAccountNumber, String password) {
        try {
            BankAccount bankAccount = this.bankAccountRepository.searchAccountsByNumber(bankAccountNumber);

            if (bankAccount == null)
                throw new NoAccountException();
            if(bankAccount.checkPassword(password) == false)
                throw new NoAccountException("패스워드가 틀렸습니다.");

            return this.bankAccountRepository.deleteAccount(bankAccountNumber);

        } catch (NoAccountException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * 이율 적용 메소드
     * @param amount
     * @return
     */
    public long applyInterest(long amount){

        return this.interestRate.add(BigDecimal.valueOf(1)) //(1 + 이율)
                .multiply(BigDecimal.valueOf(amount)) // x 입금금액
                .longValue(); //long값으로 반환
    }

    /**
     * 계좌 입금 메소드
     *
     * @param BankAccountNumber (String)
     * @param amount            (int)
     */
    public boolean depositMoney(String BankAccountNumber, int amount){
        long max_value = 1000000000;
        try {
            BankAccount bankAccount = this.bankAccountRepository.searchAccountsByNumber(BankAccountNumber);

            if (bankAccount == null)
                throw new NoAccountException();


            long newBankBalance = bankAccount.getBankBalance() + applyInterest(amount); // 기존잔액 + 입금금액

            if(newBankBalance > max_value)
                throw new Exception("저축가능한 범위를 넘어섰습니다. 창구에 문의 바랍니다.");
            this.bankAccountRepository.modifyAccount(BankAccountNumber,newBankBalance);

            //트렌젝션 기록
            LocalDateTime date = LocalDateTime.now();
            transactionRepository.addTransaction(
                    this.bankName,
                    BankAccountNumber,
                    amount,
                    date);
            return true;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * 계좌 출금 메소드
     *
     * @param BankAccountNumber (String)
     * @param amount            (int)
     * @param password          (String)
     */
    public boolean withdrawMoney(String BankAccountNumber, int amount, String password) {

        BankAccount bankAccount = this.bankAccountRepository.searchAccountsByNumber(BankAccountNumber);

        try {
            if (bankAccount == null)
                throw new NoAccountException();
            if(bankAccount.checkPassword(password) == false)
                throw new NoAccountException("패스워드가 틀렸습니다.");

            if (amount < 0)
                throw new IllegalRegexExpressionException("양수로 입력해 주세요.");


            long newBankBalance = bankAccount.getBankBalance() - amount - this.commission;
            this.bankAccountRepository.modifyAccount(BankAccountNumber, newBankBalance);

            if (newBankBalance < 0)
                throw new NotEnoughMoneyException();

            //잔고 변동시 트렌젝션 기록
            LocalDateTime date = LocalDateTime.now();
            transactionRepository.addTransaction(
                    this.bankName,
                    BankAccountNumber,
                    amount*(-1),
                    date);

            return true;
        }catch (Exception e) { //일단은 모든 exception 받기
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * 잔액 조회 메소드
     *
     * @param bankAccountNumber (String)
     * @param password          (String)
     */
    public long getAccountBalance(String bankAccountNumber, String password) {

        try {
            BankAccount account = this.bankAccountRepository.searchAccountsByNumber(bankAccountNumber);
            if (account == null)
                throw new NoAccountException("계좌가 존재하지 않습니다.");
            if (account.checkPassword(password) == false)
                throw new NoAccountException("패스워드가 일치하지 않습니다");

            return account.getBankBalance();

        } catch (NoAccountException e) { //일단은 모든 exception 받기
            System.out.println(e.getMessage());
            return -1;
        }
    }

    //endregion


    //region Search Account Methods

    /**
     * 계좌 이름으로 검색 메소드
     *
     * @param name (String)
     */
    public void searchAccountByName(String name) {

        try {
            ArrayList<BankAccount> listOfAccount = this.bankAccountRepository.searchAccountsByName(name);


            if (listOfAccount.size() == 0)
                throw new NoAccountException("해당 이름으로 조회된 계좌가 존재하지 않습니다.");

            Iterator<BankAccount> iteratorOfAccount = listOfAccount.iterator();

            while (iteratorOfAccount.hasNext()) {
                System.out.println(iteratorOfAccount.next().toString());
            }
        }
        catch (NoAccountException e){
            System.out.println(e.getMessage());
        }


    }

    /**
     * 계좌 번호로 검색 메소드
     *
     * @param bankAccountNumber
     */
    public void searchAccountByNumber(String bankAccountNumber) {
        try {
            BankAccount bankAccount = this.bankAccountRepository.searchAccountsByNumber(bankAccountNumber);

            if (bankAccount == null)
                throw new NoAccountException();

            System.out.println(bankAccount.toString());
        }
        catch (Exception e){ //일단은 모든 exception 받기
            System.out.println(e.getMessage());
        }
    }

    //endregion


    //region List Account Methods

    /**
     * 모든 계좌 리스트
     */
    public void listAllOfAccounts() {

        ArrayList<BankAccount> listOfAccounts = this.bankAccountRepository.getListOfAccounts();
        Iterator<BankAccount> iteratorOfAccounts = listOfAccounts.iterator();

        while(iteratorOfAccounts.hasNext()){
            System.out.println(iteratorOfAccounts.next().toString());
        }
    }

    /**
     * 모든 거래내역 리스트
     */
    public void listAllOfTransactions() {
        ArrayList<Transaction> listOfTransaction = this.transactionRepository.getListOfTransaction();
        Iterator<Transaction> iteratorOfTransaction = listOfTransaction.iterator();

        while(iteratorOfTransaction.hasNext()){
            System.out.println(iteratorOfTransaction.next().toString());
        }

    }

    //endregion

}
