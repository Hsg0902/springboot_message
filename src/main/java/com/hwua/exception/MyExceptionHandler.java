package com.hwua.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice/*配置一個全局异常处理类*/
public class MyExceptionHandler {
    @ExceptionHandler(Exception.class)
    public String handlerException(Exception e, HttpServletRequest request){
        SysException se =null;
        if(e instanceof  SysException){
            se= (SysException)e;
        }else{
            se = new SysException(e.getMessage());
        }
        request.setAttribute("javax.servlet.error.status_code",451);
        Map<String,Object> map = new HashMap<>();//存放我的定制错误信息
        map.put("name","frank");
        map.put("mymessage",se.getMessage());
        map.put("test","测试信息");
        request.setAttribute("ext",map);
        return "forward:/error";//交给springboot默认的BasicErrorController来进行处理，定制的map中错误信息是不会显示的
    }

}
