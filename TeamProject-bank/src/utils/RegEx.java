package utils;

import java.util.regex.Pattern;

public class RegEx {

    public boolean checkPasswordRegEx(String password) {
        return Pattern.matches(password, "\\d{4}");
    }

    public boolean checkNameRegEx(String name) {
        return Pattern.matches(name, "^[가-힣]*$") || Pattern.matches(name, "^[a-zA-Z]*$");
    }

    public boolean checkNumberRegEx(long number) {
        return  Pattern.matches(String.valueOf(number), "^[0-9]*$");
    }

    public boolean checkAccountRegEx(String accountNumber) {
        return Pattern.matches(accountNumber, "^\\d{3}-\\d{4}-\\d{4}$");
    }
}
