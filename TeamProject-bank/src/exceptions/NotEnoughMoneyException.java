package exceptions;

public class NotEnoughMoneyException extends Exception {
    public NotEnoughMoneyException(){
        super("잔액이 충분하지 않습니다");
    }
    public NotEnoughMoneyException(String msg){
        super(msg);
    }
}