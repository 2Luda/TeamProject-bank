package presentation;



import service.BankService;
import java.util.Scanner;

public class UserInterface {

    private final BankService bankService;
    private Scanner scanner;

    public UserInterface(BankService bankService) {
        this.bankService = bankService;
        scanner = new Scanner(System.in);
    }

    public boolean run(){
        MainMenu();
        boolean flag = RouteInterface();
        return flag;
    }

    private boolean RouteInterface(){

        System.out.print("메뉴를 선택해 주세요 : ");
        int menuNum = scanner.nextInt();
        scanner.nextLine();

        switch (menuNum){
            case 1:
                this.addAcountMenu();
                break;
            case 2:
                this.depositMenu();
                break;
            case 3:
                this.withdrawMenu();
                break;
            case 4:
                this.checkBalanceMenu();
                break;
            case 5:
                this.manageAccountMenu();
                break;
            case 6:
                this.searchAccountMenu();
                break;
            case 7:
                listTransactionMenu();
                break;
            case 8:
                listAccountMenu();
                break;
            case 9:
                System.out.println("출금시스템이 종료되었습니다.");
                return false;
        }
        return true;
    }
    private void MainMenu() {
        clearScreen();
        System.out.println("[ " + bankService.getBankName() + " 관리 시스템 ]");
        System.out.println(" 1. 계좌 개설");
        System.out.println(" 2. 입금");
        System.out.println(" 3. 출금");
        System.out.println(" 4. 잔액조회");
        System.out.println(" 5. 계좌 관리");
        System.out.println(" 6. 계좌 이름으로 검색 / 번호로 검색");
        System.out.println(" 7. 거래 내역 조회");
        System.out.println(" 8. 모든 계좌 조회");
        System.out.println(" 9. 나가기");
    }
    private void addAcountMenu(){
        System.out.println("");
        System.out.println("[계좌 생성]");

        System.out.print("고객님의 성명을 입력해주세요 : ");
        String bankOwnerName = scanner.nextLine();

        System.out.print("계좌번호를 입력해 주세요 :");
        String bankAccountNumber = scanner.nextLine();

        System.out.print("입금하실 금액을 알려주세요 :");
        long bankBalance = scanner.nextInt();
        scanner.nextLine();

        System.out.print("비밀번호를 입력해 주세요 : ");
        String bankPassword = scanner.nextLine();

        bankService.addAccount(bankOwnerName, bankAccountNumber, bankBalance, bankPassword);

        System.out.println();
        System.out.println("계속하시려면 아무 키를 입력해주세요");
        scanner.nextLine();
    }
    private void depositMenu(){
        System.out.println("");
        System.out.println("[입금]");

        System.out.print("입금하실 계좌번호를 알려주세요 :  ");
        String depositNumber = scanner.nextLine();

        System.out.print("입금하실 금액을 적어주세요 :");
        int depositBalance = scanner.nextInt();
        scanner.nextLine();

        if(bankService.depositMoney(depositNumber, depositBalance));
            System.out.println("입금이 완료되었습니다.");


        System.out.println();
        System.out.println("계속하시려면 아무 키를 입력해주세요");
        scanner.nextLine();

    }
    private void withdrawMenu(){
        System.out.println("");
        System.out.println("[출금]");

        System.out.print("출금하실 계좌번호를 입력해 주세요. ");
        String withdrawNumber = scanner.nextLine();

        System.out.print("출금하실 금액을 적어주세요 (수수료는 " + bankService.getCommission() + "원 입니다.) :");
        int withdrawBalance = scanner.nextInt();
        scanner.nextLine();

        System.out.print("비밀번호를 입력해주세요 :");
        String withdrawPassword = scanner.nextLine();

        if(bankService.withdrawMoeny(withdrawNumber, withdrawBalance, withdrawPassword))
            System.out.println("출금이 완료되었습니다.");

        System.out.println();
        System.out.println("계속하시려면 아무 키를 입력해주세요");
        scanner.nextLine();

    }
    private void checkBalanceMenu() {
        System.out.println("");
        System.out.println("[잔고확인]");
        System.out.print("잔고 확인을 하고 싶은 계좌 번호를 입력해주세요 : ");
        String bankAccountName = scanner.nextLine();

        System.out.print("비밀번호를 입력해주세요: ");
        String bankAccountPassword = scanner.nextLine();

        long bankMoney = bankService.getAccountBalance(bankAccountName, bankAccountPassword);
        if (bankMoney > 0) {
            System.out.println("현재 잔액 :" + bankMoney);
        }

        System.out.println();
        System.out.println("계속하시려면 아무 키를 입력해주세요");
        scanner.nextLine();
    }
    private void manageAccountMenu(){
        System.out.println("");
        System.out.println("[계좌관리]");
        System.out.println("비밀번호 수정은 1번, 계좌 삭제는 2번을 눌러주세요");
        
        int Choice = scanner.nextInt();
        scanner.nextLine();
        
        if (Choice == 1) {
            System.out.print("계좌번호를 입력해주세요 : ");
            String modifiedAccountNumber = scanner.nextLine();

            System.out.print("현재 비밀번호를 입력해주세요");
            String currentPassword = scanner.nextLine();

            System.out.print("변경할 비밀번호를 입력해주세요 ");
            String nextPassword = scanner.nextLine();

            if(bankService.changePassword(modifiedAccountNumber,currentPassword,nextPassword))
                System.out.println("수정이 완료되었습니다.");

        }
        else if (Choice == 2) {
            System.out.print("삭제하실 계좌번호를 입력해주세요. :");
            String deleteAccount = scanner.nextLine();

            System.out.print("비밀번호를 입력해주세요: ");
            String deletePassword = scanner.nextLine();

            if(bankService.deleteAccount(deleteAccount, deletePassword))
            System.out.println("삭제되었습니다. ");
        }

        System.out.println();
        System.out.println("계속하시려면 아무 키를 입력해주세요");
        scanner.nextLine();

    }
    private void searchAccountMenu(){
        System.out.println("");
        System.out.println("[계좌 조회]");
        System.out.println("이름으로 계좌 조회를 하고싶으시면 1번, 계좌 번호로 계좌 조회를 하고싶으시면 2번을 눌러주세요.");
        int searchChoice = scanner.nextInt();
        scanner.nextLine();


        if (searchChoice == 1) {
            System.out.print("이름을 입력해주세요 : ");
            String nameSearch = scanner.nextLine();
            System.out.print("조회 결과: ");
            bankService.searchAccountByName(nameSearch);
        }
        else if (searchChoice == 2) {
            System.out.print("계좌 번호를 입력해주세요 : ");
            String numberSearch = scanner.nextLine();
            System.out.print("조회 결과: ");
            bankService.searchAccountByNumber(numberSearch);
        }
        System.out.println();
        System.out.println("계속하시려면 아무 키를 입력해주세요");
        scanner.nextLine();

    }
    private void listTransactionMenu(){

        System.out.println("[모든 거래내역 조회]");
        System.out.println("조회 결과");
        bankService.listAllOfTransactions();

        System.out.println();
        System.out.println("계속하시려면 아무 키를 입력해주세요");
        scanner.nextLine();
    }
    private void listAccountMenu(){
        System.out.println("[모든 계좌정보 조회]");
        System.out.println("조회 결과");
        bankService.listAllOfAccounts();

        System.out.println();
        System.out.println("계속하시려면 아무 키를 입력해주세요");
        scanner.nextLine();
    }


    private static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

}