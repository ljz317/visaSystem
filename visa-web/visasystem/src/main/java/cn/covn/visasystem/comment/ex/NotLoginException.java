package cn.covn.visasystem.comment.ex;

public class NotLoginException extends RuntimeException{

    public NotLoginException(String message) {
        super(message);
    }
}
