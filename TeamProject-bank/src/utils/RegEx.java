package utils;

import java.util.regex.Pattern;

public class RegEx {


    /** 정규표현식 규칙 검사 클래스 **/

    //region Methods

    /**
     * Menu번호 정규표현식 체크
     * @param menuNumber
     * @return
     */
    public boolean checkMenuRegEx(String menuNumber) {
        return Pattern.matches("\\d{1}",menuNumber);
    }

    /**
     * 패스워드 정규표현식 체크
     * @param password
     * @return
     */
    public boolean checkPasswordRegEx(String password) {
        return Pattern.matches( "\\d{4}",password);
    }

    /**
     * 이름 정규표현식 체크
     * @param name
     * @return
     */
    public boolean checkNameRegEx(String name) {
        return Pattern.matches("^[가-힣]*$",name) || Pattern.matches("^[a-zA-Z]*$",name);
    }

    /**
     * 입출금금액 정규표현식 체크
     * @param number
     * @return
     */
    public boolean checkNumberRegEx(long number) {
        return Pattern.matches("^[0-9]*$",String.valueOf(number));
    }

    /**
     * 계좌번호 정규표현식 체크
     * @param accountNumber
     * @return
     */
    public boolean checkAccountRegEx(String accountNumber) {
        return Pattern.matches("^\\d{3}-\\d{4}-\\d{4}$",accountNumber);
    }

    //endregion
}
