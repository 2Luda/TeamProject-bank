package repository;

import domain.BankAccount;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;



/**
 *  BankAccount Repository 클래스
 *  BankAccount doamin 객체인스턴스 저장 및 관리
 */
public class BankAccountRepository {

    //region Singletone Pattern

    private static BankAccountRepository bankAccountService;
    private BankAccountRepository(){
        bankAccountsList = new HashMap();
    }
    public static BankAccountRepository getInstance(){
        if(bankAccountService == null){
            bankAccountService = new BankAccountRepository();
        }
        return bankAccountService;
    }

    //endregion

    //region Fields
    private HashMap<String,BankAccount> bankAccountsList;

    //endregion

    //region Methods

    /**
     * Account 객체를 HashMap에 추가
     * @param bankAccount
     */
    private void addAccount(BankAccount bankAccount){
        this.bankAccountsList.put(bankAccount.getBankAccountNumber(), bankAccount);
    }
    /**
     * Account 객체를 HashMap에 추가
     * @param bankName
     * @param bankOwnerName
     * @param bankAccountNumber
     * @param bankBalance
     * @param password
     */

    public void addAccount(String bankName, String bankOwnerName, String bankAccountNumber, long bankBalance, String password){
        BankAccount newAccount = new BankAccount(
                bankName,
                bankOwnerName,
                bankAccountNumber,
                bankBalance,
                password
        );
        addAccount(newAccount);
    }

    /**
     * Account 객체를 HashMap에서 제거
     * @param bankAccountNumber
     */
    public boolean deleteAccount(String bankAccountNumber){
        BankAccount account = this.searchAccountsByNumber(bankAccountNumber);
        return bankAccountsList.remove(bankAccountNumber, account);
    }

    /**
     * Account 계좌정보 수정(비밀번호 수정)
     * @param bankAccountNumber
     * @param Password
     * @return
     */
    public boolean modifyAccount(String bankAccountNumber, String Password){

        BankAccount account = this.bankAccountsList.get(bankAccountNumber);

        BankAccount newAccount = new BankAccount(
                account.getBankName(),
                account.getBankOwnerName(),
                account.getBankAccountNumber(),
                account.getBankBalance(),
                Password
        );
        this.addAccount(newAccount);

        this.bankAccountsList.remove(bankAccountNumber,account);

        return true;
    }

    /**
     * Account 계좌정보 수정(잔고수정)
     * @param bankAccountNumber
     * @param bankBanlance
     * @return
     */
    public boolean modifyAccount(String bankAccountNumber, long bankBanlance){

            BankAccount account = this.bankAccountsList.get(bankAccountNumber);

            BankAccount newAccount = new BankAccount(
                    account.getBankName(),
                    account.getBankOwnerName(),
                    account.getBankAccountNumber(),
                    bankBanlance,
                    account.getBankPassword()
            );
            this.addAccount(newAccount);

            this.bankAccountsList.remove(bankAccountNumber,account);

            return true;
    }

    /**
     * Account 객체 리스트를 ownerName으로 검색하여 반환
     * @param ownerName
     * @return
     */
    public ArrayList<BankAccount> searchAccountsByName(String ownerName){
        ArrayList<BankAccount> result = new ArrayList<>();

        Iterator<String> iteratorOfAccount = this.bankAccountsList.keySet().iterator();
        while (iteratorOfAccount.hasNext()) {
            BankAccount account = this.bankAccountsList.get(iteratorOfAccount.next());

            if(account.checkOwnerName(ownerName) == true){
                result.add(account);
            }
        }

        return result;
    }

    /**
     * Account 객체를 bankAccountNumber으로 검색하여 반환
     * @param bankAccountNumber
     * @return
     */
    public BankAccount searchAccountsByNumber(String bankAccountNumber){
            return bankAccountsList.get(bankAccountNumber);
    }

    /**
     * HashMap에 등록되어있는 모든 객체를 ArrayList로 반환
     * @return
     */
    public ArrayList<BankAccount> getListOfAccounts() {
        ArrayList<BankAccount> value = new ArrayList<>();

        Iterator<String> iteratorOfAccount = this.bankAccountsList.keySet().iterator();

        while (iteratorOfAccount.hasNext()) {
            BankAccount account = this.bankAccountsList.get(iteratorOfAccount.next());
            value.add(account);
        }
        return value;
    }

    //endregion

}