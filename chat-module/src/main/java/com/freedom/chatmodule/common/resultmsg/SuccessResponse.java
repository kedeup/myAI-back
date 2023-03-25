package com.freedom.chatmodule.common.resultmsg;

import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * @Author kede·W  on  2023/3/20
 */
@Data
public class SuccessResponse <T>{
    private int code;
    private String message;
    private T data;
    private long timestamp ;


//    构造方法
    public SuccessResponse() {
        this.timestamp = System.currentTimeMillis();
    }

    public SuccessResponse(int code, String message) {
        this.code = code;
        this.message = message;
        this.timestamp = System.currentTimeMillis();
    }

    public SuccessResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.timestamp = System.currentTimeMillis();
    }

    public static <T> SuccessResponse<T> success() {
        return success(null);
    }

    public static <T> SuccessResponse<T> success(T data) {
        return new SuccessResponse<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), data);
    }

//    public static <T> SuccessResponse<T> error(String message) {
//        return new SuccessResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), message);
//    }
//
//    public static <T> SuccessResponse<T> error(HttpStatus status, String message) {
//        return new SuccessResponse<>(status.value(), message);
//    }







}
