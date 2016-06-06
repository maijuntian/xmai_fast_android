package com.mai.xmai_fast_lib.exception;

/**
 * Created by mai on 16/6/6.
 */
public class ServerException extends RuntimeException{

    private int code;

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public ServerException(String msg){
        super(msg);
    }
}
