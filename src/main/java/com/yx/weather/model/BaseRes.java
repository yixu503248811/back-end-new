package com.yx.weather.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
/**
 *  类的功能描述：
 *
 * @ClassName:  BaseRes
 * @Author  yx
 * @Date 2019-10-31 12:25:56
 */
@Getter
@Setter
public class BaseRes<T> implements Serializable {

    private Integer code;

    private T data;

    private String message;

    public BaseRes() {
    }

    public BaseRes(Integer code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public BaseRes(Integer code, T data) {
        this.code = code;
        this.data = data;
    }

    public BaseRes(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public BaseRes(Integer code) {
        this.code = code;
    }

    public BaseRes(T data) {
        this.code = 100;
        this.data = data;
        this.message = null;
    }
}
