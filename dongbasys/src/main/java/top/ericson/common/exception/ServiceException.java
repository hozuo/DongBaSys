package top.ericson.common.exception;

public class ServiceException extends RuntimeException {

    private static final long serialVersionUID = 6080887096742828769L;

    public ServiceException() {
        super();
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }
}
