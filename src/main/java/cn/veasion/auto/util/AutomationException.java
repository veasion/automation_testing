package cn.veasion.auto.util;

/**
 * AutomationException
 *
 * @author luozhuowei
 * @date 2020/8/1
 */
public class AutomationException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public AutomationException() {
        super();
    }

    public AutomationException(String message) {
        super(message);
    }

    public AutomationException(String message, Throwable cause) {
        super(message, cause);
    }

    public AutomationException(Throwable cause) {
        super(cause);
    }

}
