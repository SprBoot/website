package cn.dingdm.website.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
/***
 * @author dinggc
 * Description //相册Controller
 * Date 下午5:52 18-12-31
 * Param
 * return
 **/
@Controller
public class PhotoController {
    /***
     * @author dinggc
     * Description //跳转到相册显示页面
     * Date 下午5:52 18-12-31
     * Param []
     * return java.lang.String
     **/
    @RequestMapping(value = "/photo")
    public String photo(){
        return "photo";
    }
    /***
     * @author dinggc
     * Description //忘了
     * Date 下午5:52 18-12-31
     * Param []
     * return java.lang.String
     **/
    @RequestMapping(value = "/single")
    public String single(){
        return "single";
    }
}
