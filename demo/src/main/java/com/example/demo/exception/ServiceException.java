package com.example.demo.exception;




/*自定义异常*/


public class ServiceException extends RuntimeException {
    private Integer code;

    public ServiceException(Integer code, String message) {
        super(message);
        this.code = code;
    }







    public Integer getCode() {
        return code;
    }


    public void setCode(Integer code) {
        this.code = code;
    }

}
