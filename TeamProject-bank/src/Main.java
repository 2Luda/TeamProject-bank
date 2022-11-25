
import service.BankService;
import presentation.UserInterface;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        BankService kbBankService = new BankService("국민은행", 500);
        


        UserInterface UI = new UserInterface();
        Scanner scan = new Scanner(System.in);


        boolean isExit = false;


        initAccount(kbBankService);  //init with test data

        do {
            boolean flag = false;
            UI.run();
            int menu = scan.nextInt();
            scan.nextLine();
            switch (menu) {
                case 1:
                    System.out.println("고객님의 성명,계좌번호, 입금하실 금액을 알려주세요.");
                    System.out.println("고객님의 성명 : ");
                    String bankOwnerName = scan.nextLine();
                    System.out.println("계좌번호를 입력해 주세요 :");
                    String bankAccountNumber = scan.nextLine();
                    System.out.println("입금하실 금액을 알려주세요 :");
                    long bankBalance = scan.nextInt();
                    scan.nextLine();
                    System.out.println("비밀번호를 입력해 주세요 : ");
                    String bankPassword = scan.nextLine();
                    kbBankService.addAccount(bankOwnerName, bankAccountNumber, bankBalance, bankPassword);
                    break;
                case 2:
                    System.out.println("입금하실 계좌번호를 알려주세요 :  ");
                    String depositNumber = scan.nextLine();
                    System.out.println("입금하실 금액을 적어주세요 :");
                    int depositBalance = scan.nextInt();

                    if(kbBankService.depositMoney(depositNumber, depositBalance));
                        System.out.println("입금이 완료되었습니다.");

                        break;
                case 3:
                    System.out.println("출금하실 계좌번호를 입력해 주세요. ");
                    String withdrawNumber = scan.nextLine();

                    System.out.println("출금하실 금액을 적어주세요 (수수료는 " + kbBankService.getCommission() + "원 입니다.) :");
                    int withdrawBalance = scan.nextInt();
                    scan.nextLine();

                    System.out.println("비밀번호를 입력해주세요 :");
                    String withdrawPassword = scan.nextLine();

                    if(kbBankService.withdrawMoeny(withdrawNumber, withdrawBalance, withdrawPassword))
                        System.out.println("출금이 완료되었습니다.");

                        break;
                case 4:
                    System.out.println("계좌 수정은 1번, 계좌 삭제는 2번, 계좌 잔고 확인을 하고싶으시면 3번을 눌러주세요.");
                    int Choice = scan.nextInt();
                    scan.nextLine();
                    if (Choice == 1) {
                        System.out.println("수정하실 고객님의 성명을 입력해주세요 : ");
                        String modifiedname = scan.nextLine();
                        System.out.println("수정 전 이름으로 등록되어있는 계좌번호를 입력해주세요 : ");
                        String modifiedAccountNumber = scan.nextLine();
                        System.out.println("비밀번호를 입력해주세요: ");
                        String modifiedPassword = scan.nextLine();

                        if(kbBankService.modifyAccount(modifiedname, modifiedAccountNumber, modifiedPassword));
                            System.out.println("수정이 완료되었습니다.");

                    } else if (Choice == 2) {
                        System.out.println("삭제하실 계좌번호를 입력해주세요. :");
                        String deleteAccount = scan.nextLine();
                        System.out.println("비밀번호를 입력해주세요: ");
                        String deletePassword = scan.nextLine();

                        if(kbBankService.deleteAccount(deleteAccount, deletePassword));
                            System.out.println("삭제되었습니다. ");
                    }
                    if (Choice == 3) {
                        System.out.println("잔고 확인을 하고 싶은 계좌 번호를 입력해주세요 : ");
                        String bankAccountName = scan.nextLine();
                        System.out.println("비밀번호를 입력해주세요: ");
                        String bankAccountPassword = scan.nextLine();

                        long bankMoney = kbBankService.getAccountBalance(bankAccountName, bankAccountPassword);
                        if (bankMoney > 0) {
                            System.out.println("현재 잔액 :" + bankMoney);
                        }
                    }
                    break;
                case 5:
                    System.out.println("이름으로 계좌 조회를 하고싶으시면 1번, 계좌 번호로 계좌 조회를 하고싶으시면 2번을 눌러주세요.");
                    int searchChoice = scan.nextInt();
                    scan.nextLine();
                    if (searchChoice == 1) {
                        System.out.println("이름을 입력해주세요 : ");
                        String nameSearch = scan.nextLine();
                        System.out.println("조회 결과: ");
                        kbBankService.searchAccountByName(nameSearch);
                    }
                    if (searchChoice == 2) {
                        System.out.println("계좌 번호를 입력해주세요 : ");
                        String numberSearch = scan.nextLine();
                        System.out.println("조회 결과: ");
                        kbBankService.searchAccountByNumber(numberSearch);
                    }

                    break;
                case 6:
                    System.out.println("조회 결과");
                    kbBankService.listAllOfTransactions();
                    break;
                case 7:
                    System.out.println("조회 결과");
                    kbBankService.listAllOfAccounts();

                    break;
                case 8:
                    System.out.println("이용해 주셔서 감사합니다.");
                    isExit = true;
                    break;
            }
        } while (!isExit);


    }

    private static void initAccount(BankService kbBankService) {
        String[] bankOwnerName = {"조성락", "곽두영", "김태이", "배지호", "김지환"};
        String[] bankAccountNumber = {"111-111-1111", "222-222-2222", "333-333-3333", "444-444-4444", "555-555-5555"};
        long[] bankBalance = {1000000, 5000, 100000, 0, 100};
        String[] bankPassword = {"1212", "3030", "2412", "4039", "1241"};

        for (int i = 0; i < bankAccountNumber.length; i++)
            kbBankService.addAccount(bankOwnerName[i], bankAccountNumber[i], bankBalance[i], bankPassword[i]);
    }
}
