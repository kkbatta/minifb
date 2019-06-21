package com.interview.pocketfb.sample.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import com.google.gson.Gson;
import com.interview.pocketfb.error.ErrorCode;
import com.interview.pocketfb.error.StatusEnum;

public class BaseResponse {

    private static final Logger log = (Logger) LogManager.getLogger(BaseResponse.class.getName());

    // Status
    private StatusEnum status;
    private int statusCode;
    /**
     * TODO good to have metrics. will implement if time permits
     *
     */
    private long time;
    private String timeTaken;
    private String action;

    // Error
    private ErrorCode errorCode ;
    private String errorMessage;
    private String stackTrace;

    // Result
    private Object result;

    public BaseResponse() {
        
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

   
    public long getTime() {
        return time;
    }

    public String getTimeTaken() {
        return timeTaken;
    }

    public void setTimeTaken(String timeTaken) {
        this.timeTaken = timeTaken;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    

	public String getMsg() {
        return action;
    }

    public void setMsg(String msg) {
        this.action = msg;
    }

   
    public void setTime(long time) {
        this.time = time;
    }

    public String toString() {
        return new Gson().toJson(this);
    }
}