package com.atguigu.handler;

import com.atguigu.config.MyException;
import com.atguigu.config.response.RetVal;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Date:2022/7/3
 * Author:zh
 * Description:
 */
@ControllerAdvice
public class MyExceptionHandler {

    /**
     * 自定义异常处理器
     * @param e
     * @return
     */
    @ExceptionHandler(MyException.class)
    @ResponseBody
    public RetVal customExceptionHandler(MyException e){
        //打印异常
        e.printStackTrace();
        return RetVal.error().message(e.getMessage());
    }
}
