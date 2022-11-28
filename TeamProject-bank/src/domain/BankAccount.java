package domain;

/**
 * BankAccount 도메인 클래스
 * BankAccount 객체의 속성 명세
 */
public class BankAccount {


    //region Fields
    private String bankName;
    private String bankOwnerName;
    private String bankAccountNumber;
    private long bankBalance;
    private String bankPassword;

    //endregion

    //region Constructor
    public BankAccount(String bankName, String bankOwnerName, String bankAccountNumber, long bankBalance, String bankPassword){
        this.bankName = bankName;
        this.bankOwnerName = bankOwnerName;
        this.bankAccountNumber = bankAccountNumber;
        this.bankBalance = bankBalance;
        this.bankPassword = bankPassword;
    }

    //endregion

    //region Getter
    public String getBankName() {
        return bankName;
    }
    public String getBankOwnerName() {
        return bankOwnerName;
    }
    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public long getBankBalance() {
        return bankBalance;
    }
    public String getBankPassword() {
        return bankPassword;
    }

    //endregions

    //region Methods
    public boolean checkOwnerName(String bankOwnerName){
        if(this.bankOwnerName.compareTo(bankOwnerName) == 0)
            return true;
        else
            return false;
    }
    public boolean checkPassword(String bankPassword){
        if(this.bankPassword.compareTo(bankPassword) == 0)
            return true;
        else
            return false;
    }


    @Override
    public String toString() {

        String value = "";

        value += "-----------------------------\n";
        value += "은행이름 : " + this.bankName + "\n";
        value += "소유자명 : " + this.bankOwnerName + "\n";
        value += "계좌번호 : " + this.bankAccountNumber + "\n";
        value += "잔   고 : " + this.bankBalance + "\n";

        return value;
    }

    //endregion

}
