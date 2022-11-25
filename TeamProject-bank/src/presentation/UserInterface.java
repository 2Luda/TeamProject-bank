package presentation;



import exceptions.IllegalRegexExpressionException;
import service.BankService;
import utils.RegEx;

import java.util.InputMismatchException;
import java.util.Scanner;

public class UserInterface {

    private final BankService bankService;
    private Scanner scanner;
    private final RegEx regEx = new RegEx();

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

            switch (menuNum) {
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
        try {
            System.out.println("");
            System.out.println("[계좌 생성]");

            System.out.print("고객님의 성명을 입력해주세요 : ");
            String bankOwnerName = scanner.nextLine();

            if(!regEx.checkNameRegEx(bankOwnerName))
                throw new IllegalRegexExpressionException("올바른 이름 형식이 아닙니다.");

            System.out.print("계좌번호를 입력해 주세요 :");
            String bankAccountNumber = scanner.nextLine();

            if (!regEx.checkAccountRegEx(bankAccountNumber))
                throw new IllegalRegexExpressionException("올바른 계좌 형식이 아닙니다.");
            
            if(bankService.isExistBankAccountNumber(bankAccountNumber) == true)
                throw new Exception("해당 계좌번호가 이미 존재합니다.");

            System.out.print("입금하실 금액을 알려주세요 :");
            String input = scanner.nextLine();

            int bankBalance = Integer.parseInt(input);

            if(!regEx.checkNumberRegEx(bankBalance))
                throw new IllegalRegexExpressionException("올바른 숫자가 아닙니다.");

            System.out.print("비밀번호를 입력해 주세요 : ");
            String bankPassword = scanner.nextLine();

            if(!regEx.checkPasswordRegEx(bankPassword))
                throw  new IllegalRegexExpressionException("올바른 비밀번호 형식이 아닙니다.");

            bankService.addAccount(bankOwnerName, bankAccountNumber, bankBalance, bankPassword);

            System.out.println();
            System.out.println("계속하시려면 아무 키를 입력해주세요");
            scanner.nextLine();
        } catch (IllegalRegexExpressionException e) {
            System.out.println(e.getMessage());
        }catch (NumberFormatException e) {
            System.out.println("1회 최대 입금금액은 214748647원 입니다.");
        }
    }
    private void depositMenu(){
        try{
            System.out.println("");
            System.out.println("[입금]");
            System.out.print("입금하실 계좌번호를 알려주세요 :  ");
            String depositNumber = scanner.nextLine();

            if (!regEx.checkAccountRegEx(depositNumber))
                throw new IllegalRegexExpressionException("올바른 계좌 형식이 아닙니다.");

            System.out.print("입금하실 금액을 적어주세요 :");
            String input = scanner.nextLine();

            int depositBalance = Integer.parseInt(input);

            if(!regEx.checkNumberRegEx(depositBalance))
                throw new IllegalRegexExpressionException("올바른 숫자를 입력해주세요.");

            if(bankService.depositMoney(depositNumber, depositBalance))
                System.out.println("입금이 완료되었습니다.");

            System.out.println();
            System.out.println("계속하시려면 아무 키를 입력해주세요");
            scanner.nextLine();
        }catch (IllegalRegexExpressionException e) {
            System.out.println(e.getMessage());
        }catch (NumberFormatException e){
            System.out.println("1회 최대 입금금액은 214748647원 입니다.");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private void withdrawMenu(){
        try {
            System.out.println("");
            System.out.println("[출금]");

            System.out.print("출금하실 계좌번호를 입력해 주세요. ");
            String withdrawNumber = scanner.nextLine();

            if (!regEx.checkAccountRegEx(withdrawNumber))
                throw new IllegalRegexExpressionException("올바른 계좌 형식이 아닙니다.");

            System.out.print("출금하실 금액을 적어주세요 (수수료는 " + bankService.getCommission() + "원 입니다.) :");
            String input = scanner.nextLine();

            int withdrawBalance = Integer.parseInt(input);

            if(!regEx.checkNumberRegEx(withdrawBalance))
                throw new IllegalRegexExpressionException("올바른 숫자를 입력해주세요.");

            System.out.print("비밀번호를 입력해주세요 :");
            String withdrawPassword = scanner.nextLine();

            if(!regEx.checkPasswordRegEx(withdrawPassword))
                throw new IllegalRegexExpressionException("올바른 비밀번호 형식이 아닙니다.");

            if (bankService.withdrawMoeny(withdrawNumber, withdrawBalance, withdrawPassword))
                System.out.println("출금이 완료되었습니다.");

            System.out.println();
            System.out.println("계속하시려면 아무 키를 입력해주세요");
            scanner.nextLine();
        }catch (IllegalRegexExpressionException e) {
            System.out.println(e.getMessage());
        }catch (NumberFormatException e) {
            System.out.println("1회 최대 출금금액은 214748647원 입니다.");
        }
    }
    private void checkBalanceMenu() {
        try {
            System.out.println("");
            System.out.println("[잔고확인]");
            System.out.print("잔고 확인을 하고 싶은 계좌 번호를 입력해주세요 : ");
            String bankAccountName = scanner.nextLine();

            if (!regEx.checkAccountRegEx(bankAccountName))
                throw new IllegalRegexExpressionException("올바른 계좌 형식이 아닙니다.");

            System.out.print("비밀번호를 입력해주세요: ");
            String bankAccountPassword = scanner.nextLine();

            if(!regEx.checkPasswordRegEx(bankAccountPassword))
                throw new IllegalRegexExpressionException("올바른 비밀번호 형식이 아닙니다.");

            long bankMoney = bankService.getAccountBalance(bankAccountName, bankAccountPassword);
            if (bankMoney > 0) {
                System.out.println("현재 잔액 :" + bankMoney);
            }

            System.out.println();
            System.out.println("계속하시려면 아무 키를 입력해주세요");
            scanner.nextLine();
        } catch (IllegalRegexExpressionException e) {
            System.out.println(e.getMessage());
        }
    }
    private void manageAccountMenu(){
        try {
            System.out.println("");
            System.out.println("[계좌관리]");
            System.out.println("비밀번호 수정은 1번, 계좌 삭제는 2번을 눌러주세요");

            int Choice = scanner.nextInt();
            scanner.nextLine();

            if (Choice == 1) {
                System.out.print("계좌번호를 입력해주세요 : ");
                String modifiedAccountNumber = scanner.nextLine();

                if (!regEx.checkAccountRegEx(modifiedAccountNumber))
                    throw new IllegalRegexExpressionException("올바른 계좌 형식이 아닙니다.");

                System.out.print("현재 비밀번호를 입력해주세요");
                String currentPassword = scanner.nextLine();

                if(!regEx.checkPasswordRegEx(currentPassword))
                    throw new IllegalRegexExpressionException("올바른 비밀번호 형식이 아닙니다.");

                System.out.print("변경할 비밀번호를 입력해주세요 ");
                String nextPassword = scanner.nextLine();

                if(!regEx.checkPasswordRegEx(nextPassword))
                    throw new IllegalRegexExpressionException("올바른 비밀번호 형식이 아닙니다.");

                if (bankService.changePassword(modifiedAccountNumber, currentPassword, nextPassword))
                    System.out.println("수정이 완료되었습니다.");

            } else if (Choice == 2) {
                System.out.print("삭제하실 계좌번호를 입력해주세요. :");
                String deleteAccount = scanner.nextLine();

                if (!regEx.checkAccountRegEx(deleteAccount))
                    throw new IllegalRegexExpressionException("올바른 계좌 형식이 아닙니다.");

                System.out.print("비밀번호를 입력해주세요: ");
                String deletePassword = scanner.nextLine();

                if(!regEx.checkPasswordRegEx(deletePassword))
                    throw new IllegalRegexExpressionException("올바른 비밀번호 형식이 아닙니다.");

                if (bankService.deleteAccount(deleteAccount, deletePassword))
                    System.out.println("삭제되었습니다. ");
            }

            System.out.println();
            System.out.println("계속하시려면 아무 키를 입력해주세요");
            scanner.nextLine();
        }catch ( IllegalRegexExpressionException e) {
            System.out.println(e.getMessage());
        }
    }

    private void searchAccountMenu(){
        try {
            System.out.println("");
            System.out.println("[계좌 조회]");
            System.out.println("이름으로 계좌 조회를 하고싶으시면 1번, 계좌 번호로 계좌 조회를 하고싶으시면 2번을 눌러주세요.");
            int searchChoice = scanner.nextInt();
            scanner.nextLine();


            if (searchChoice == 1) {
                System.out.print("이름을 입력해주세요 : ");
                String nameSearch = scanner.nextLine();

                if(!regEx.checkNameRegEx(nameSearch))
                    throw new IllegalRegexExpressionException("올바른 이름 형식이 아닙니다.");

                System.out.print("조회 결과: ");
                bankService.searchAccountByName(nameSearch);
            } else if (searchChoice == 2) {
                System.out.print("계좌 번호를 입력해주세요 : ");
                String numberSearch = scanner.nextLine();

                if (!regEx.checkAccountRegEx(numberSearch))
                    throw new IllegalRegexExpressionException("올바른 계좌 형식이 아닙니다.");

                System.out.print("조회 결과: ");
                bankService.searchAccountByNumber(numberSearch);
            }
            System.out.println();
            System.out.println("계속하시려면 아무 키를 입력해주세요");
            scanner.nextLine();
        }catch (IllegalRegexExpressionException e) {
            System.out.println(e.getMessage());
        }
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
