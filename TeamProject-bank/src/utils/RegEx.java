package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegEx {

    public boolean checkPasswordRegEx(String password) {
        return checkRegEx(password, "\\d{4}$");
    }

    public boolean checkNameRegEx(String name) {
        return checkRegEx(name, "^[가-힣]*$") || checkRegEx(name, "^[a-zA-Z]*$");
    }

    public boolean checkNumberRegEx(long number) {
        return  checkRegEx(String.valueOf(number), "^[0-9]*$");
    }

    public boolean checkAccountRegEx(String accountNumber) {
        return checkRegEx(accountNumber, "^\\d{3}-\\d{4}-\\d{4}$");
    }

    public boolean checkRegEx(String value, String regEx) {
        Pattern pattern = Pattern.compile(regEx);

        Matcher matcher = pattern.matcher(value);

        return matcher.find();
    }
}
