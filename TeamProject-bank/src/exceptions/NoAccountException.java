package exceptions;

public class NoAccountException extends Exception {
    public NoAccountException(){
        super("계좌가 존재하지 않습니다.");
    }
    public NoAccountException(String msg){
        super(msg);
    }
}
