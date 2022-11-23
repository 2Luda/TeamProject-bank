package service;


import Exceptions.NoAccountException;
import Exceptions.NotEnoughException;

import repository.BankAccountRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

//계좌 관리
public class BankAccountService
{

    private static BankAccountService service;
    private BankAccountService(){
        bankAccountsList = new HashMap();
    }
    public static BankAccountService getInstance(){
        if(service == null){
            service = new BankAccountService();
        }
        return service;
    }


    private HashMap<String,BankAccountRepository> bankAccountsList;



    public void addAccount(String bankName, String bankOwnerName, String bankAccountNumber, long bankBalance){
        BankAccountRepository newAccount = new BankAccountRepository(
                bankName,
                bankOwnerName,
                bankAccountNumber,
                bankBalance
        );
        bankAccountsList.put(newAccount.getBankAccountNumber(), newAccount);
    }
    public boolean deleteAccount(String bankAccountNumber){

        try{
            BankAccountRepository account = bankAccountsList.get(bankAccountNumber);

            if(account == null)
                throw new NoAccountException("해당 계좌가 존재하지 않습니다.");

            boolean flag = bankAccountsList.remove(account.getBankAccountNumber(), account);

            if(flag == false)
                throw new NoAccountException("삭제 실패");

            return true;
        }
        catch (NoAccountException e){
            System.out.println(e.getMessage());
            return false;
        }

    }
    public long getAccountBalance(String bankAccountNumber){

        try{
            BankAccountRepository account = bankAccountsList.get(bankAccountNumber);

            if(account == null)
                throw new NoAccountException("계좌가 존재하지 않습니다.");

            return account.getBankBalance();
        }
        catch (NoAccountException e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }
    public boolean depositAndWithdraw(String bankAccountNumber , int amount){
        try {
            BankAccountRepository bankAccount = bankAccountsList.get(bankAccountNumber);

            if (bankAccount == null)
                throw new NoAccountException("계좌가 존재하지 않습니다.");

            if (amount < 0 && bankAccount.getBankBalance() < amount)
                throw new NotEnoughException("잔액이 충분하지 않습니다.");

            bankAccount.addBankBalance(amount);

            return true;

        } catch (NoAccountException e) {
            System.out.println(e.getMessage());
            return false;
        } catch (NotEnoughException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }



    public ArrayList<BankAccountRepository> getAccountsByName(String name){
        ArrayList<BankAccountRepository> result = new ArrayList<>();

        Iterator<String> iteratorOfAccount = this.bankAccountsList.keySet().iterator();
        while (iteratorOfAccount.hasNext()) {
            BankAccountRepository Account = this.bankAccountsList.get(iteratorOfAccount.next());

            if(Account.getBankOwnerName().compareTo(name) == 0){
                result.add(Account);
            }
        }

        return result;
    }
    public BankAccountRepository getAccountsByBankAccountNumber(String bankAccountNumber){
        try {
            BankAccountRepository account = bankAccountsList.get(bankAccountNumber);

            if (account == null)
                throw new NoAccountException("해당 계좌가 존재하지 않습니다.");

            return account;
        }
        catch (NoAccountException e){
            System.out.println(e.getMessage());
            return null;
        }

    }
    public void listAccounts() {
        Iterator<String> iteratorOfAccount = this.bankAccountsList.keySet().iterator();

        while (iteratorOfAccount.hasNext()) {
            BankAccountRepository account = this.bankAccountsList.get(iteratorOfAccount.next());

        }
    }
}
