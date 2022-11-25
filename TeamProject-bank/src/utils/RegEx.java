package utils;

import java.util.regex.Pattern;

public class RegEx {

    public boolean checkPasswordRegEx(String password) {
        return Pattern.matches("\\d{4}", password);
    }

    public boolean checkNameRegEx(String name) {
        return Pattern.matches("^[가-힣]*$", name ) || Pattern.matches("^[a-zA-Z]*$", name );
    }

    public boolean checkNumberRegEx(long number) {
        return  Pattern.matches("^[0-9]*$", String.valueOf(number) );
    }

    public boolean checkAccountRegEx(String accountNumber) {
        return Pattern.matches("^\\d{3}-\\d{4}-\\d{4}$", accountNumber );
    }

}
