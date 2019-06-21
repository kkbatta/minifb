package com.interview.pocketfb.error;

public class CustomException extends Exception{
	private static final long serialVersionUID = 0L;

    private ErrorCode errorCode;

    public CustomException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public CustomException(String message, Throwable rootCause, ErrorCode errorCode) {
        super(message, rootCause);
        this.errorCode = errorCode;
    }

    public CustomException(Throwable rootCause, ErrorCode errorCode) {
        super(rootCause);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String toString() {
        return "ErrorCode: " + getErrorCode() + ", ErrorMessage: " + getMessage();
    }
}
