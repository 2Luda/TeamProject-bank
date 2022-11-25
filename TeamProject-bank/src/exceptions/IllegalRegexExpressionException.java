package exceptions;

public class IllegalRegexExpressionException extends Exception{
    public IllegalRegexExpressionException(){
        super("정규식 표현에 올바르지 않습니다.");
    }
    public IllegalRegexExpressionException(String msg){
        super(msg);
    }
}
