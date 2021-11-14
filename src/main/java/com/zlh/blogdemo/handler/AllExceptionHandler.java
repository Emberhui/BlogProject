package com.zlh.blogdemo.handler;

import com.zlh.blogdemo.vo.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName AllExceptionHandler
 * @Description TODO
 * @Author Ember_hui Email:Ember_hui@163.com
 * @Date 2021/11/12 13:28
 * @Version 1.0
 */

//对加了@Controller的注解的方法进行拦截处理  AOP实现
@ControllerAdvice
public class AllExceptionHandler {
//    进行异常处理，处理Exception.class的异常
    @ExceptionHandler(Exception.class)
    @ResponseBody    //返回json数据
    public Result doException(Exception e){
        e.printStackTrace();
        return Result.fail(-999,"系统异常");
    }
}
