package cn.dingdm.website.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ClassName AliController
 * @Description 支付宝支付
 * @author dinggc
 * Date 19-1-11 下午2:54
 * @Version 1.0
 **/
@Controller(value = "alipay")
public class AliController {
    /***
     * author dinggc
     * Description //跳转到支付宝支付接口
     * Date 下午2:56 19-1-11
     * Param []
     * return java.lang.String
     **/
    @RequestMapping(value = "entrance")
    public String entrance(){
        return "aliPay";
    }
}
