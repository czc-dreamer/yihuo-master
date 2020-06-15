package com.yihuo.myexception;

/**
 * @Author: 98050
 * @Time: 2018-11-05 16:07
 * @Feature:
 */
public class MyException extends RuntimeException {

    public MyException(YhException exception) {
        super(exception.toString());
    }
}
