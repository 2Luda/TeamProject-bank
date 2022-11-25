
import service.BankService;
import presentation.UserInterface;

public class Main {
    public static void main(String[] args) {

        BankService kbBankService = new BankService("국민은행", 500);
        initAccount(kbBankService);  //init with test data

        UserInterface UI = new UserInterface(kbBankService);

        while(UI.run());



    }

    private static void initAccount(BankService kbBankService) {
        String[] bankOwnerName = {"조성락", "곽두영", "김태이", "배지호", "김지환"};
        String[] bankAccountNumber = {"111-1111-1111", "222-2222-2222", "333-3333-3333", "444-4444-4444", "555-5555-5555"};
        long[] bankBalance = {1000000, 5000, 100000, 0, 100};
        String[] bankPassword = {"1212", "3030", "2412", "4039", "1241"};

        for (int i = 0; i < bankAccountNumber.length; i++)
            kbBankService.addAccount(bankOwnerName[i], bankAccountNumber[i], bankBalance[i], bankPassword[i]);
    }




}
