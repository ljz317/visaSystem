package cn.covn.visasystem.comment.ex;


import cn.covn.visasystem.comment.response.StatusCode;
import lombok.Getter;

//1.自定义业务异常类
@Getter
public class ServiceException extends RuntimeException{
    private StatusCode statusCode;

    public ServiceException(StatusCode statusCode) {
        this.statusCode = statusCode;
    }
}
