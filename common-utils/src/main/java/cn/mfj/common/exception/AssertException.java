package cn.mfj.common.exception;

/**
 * 自定义断言异常，一般是供断言工具类使用
 *
 * @author mfj on 2021/11/17
 */
public class AssertException extends RuntimeException {

    public AssertException() {
        super();
    }

    public AssertException(String message) {
        super(message);
    }

    public AssertException(Integer errorCode, String message) {
        super(message);
    }

    public AssertException(String message, Throwable cause) {
        super(message, cause);
    }

    public AssertException(Throwable cause) {
        super(cause);
    }

    protected AssertException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
