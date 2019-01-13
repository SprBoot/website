package cn.dingdm.website.config;

import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
/***
 * @author dinggc
 * Description //自定义的异常拦截器
 * Date 下午5:00 18-12-31
 * Param
 * return
 **/
@ControllerAdvice
public class MyExceptionHandler {

    /**
     * @author dinggc
     * Description //拦截shiro的权限异常，并跳转到相应的错误显示页面
     * Date 下午5:00 18-12-31
     * Param [e, request]
     * return java.lang.String
     **/
    @ExceptionHandler(UnauthorizedException.class)
    public String handleException(Exception e, HttpServletRequest request){
        //传入我们自己的错误状态码  4xx 5xx，否则就不会进入定制错误页面的解析流程
        request.setAttribute("javax.servlet.error.status_code",403);
        //转发到/error
        return "authorization";
    }
}
