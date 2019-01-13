package cn.dingdm.website.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
    /**
     * @author dinggc
     * Description //跳转到主页
     * Date 下午12:31 18-12-29
     * Param []
     * return java.lang.String
     **/
    @RequestMapping(value = "/main")
    public String mainEntrance(){
        return "main";
    }
}
