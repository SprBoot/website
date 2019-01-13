package cn.dingdm.website.controller;

import cn.dingdm.website.entities.*;
import cn.dingdm.website.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/***
 * @author dinggc
 * Description //用户Controller
 * Date 下午5:54 18-12-31
 * Param
 * return
 **/
@Controller
public class UserController {

    @Autowired
    UserService userService;
    /***
     * @author dinggc
     * Description //封装我的信息并跳转到个人信息显示页面
     * Date 下午5:55 18-12-31
     * Param [model]
     * return java.lang.String
     **/
    @RequiresPermissions("info:view")
    @RequestMapping(value = "/userInfo")
    public String userInfo(Model model){
        try{
            User user = userService.getUserById();
            List<KnowLedge> knowLedges = userService.getKnowledge();
            List<Abilities> abilities = userService.getAbility();
            model.addAttribute("user",user);
            model.addAttribute("knowledge",knowLedges);
            model.addAttribute("abilities",abilities);
        }catch (Exception e){

        }
        return "me";
    }

}
