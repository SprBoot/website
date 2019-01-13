package cn.dingdm.website.controller;

import cn.dingdm.website.config.GeetestConfig;
import cn.dingdm.website.config.GeetestLib;
import cn.dingdm.website.entities.Vuser;
import cn.dingdm.website.service.BaseService;
import cn.dingdm.website.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
/***
 * @author dinggc
 * Description //登录与注册Controller
 * Date 下午5:33 18-12-31
 * Param
 * return
 **/
@Controller
public class LoginController {

    @Autowired
    UserService userService;
    @Autowired
    BaseService baseService;
    /**
     * @author dinggc
     * Description //跳转到登录页面
     * Date 下午12:23 18-12-29
     * Param []
     * return java.lang.String
     **/
    @RequestMapping(value = "sign")
    public String sign(){
        return "login";
    }
    /***
     * @author dinggc
     * Description //shiro对登录进行拦截并进行权限认证
     * Date 下午5:34 18-12-31
     * Param [username, password, rememberMe, model, session, request]
     * return java.lang.String
     **/
    @RequestMapping(value="/login")
    public String login(String username, String password,boolean rememberMe,Model model,HttpSession session,HttpServletRequest request){
        UsernamePasswordToken token = new UsernamePasswordToken(username, password,rememberMe);
        try {
            SecurityUtils.getSubject().login(token);
            Vuser vuser = baseService.getCurrentVuser();
            session.setAttribute("username",vuser.getUsername());
            session.setAttribute("id",vuser.getId());
            SavedRequest savedRequest = WebUtils.getSavedRequest(request);
            if(savedRequest==null){
                return "main";
            }else{
                return "redirect:" + savedRequest.getRequestUrl();
            }
        }catch (UnknownAccountException e){
            model.addAttribute("msg","用户不存在或邮箱尚未验证");
            return "login";
        }catch (IncorrectCredentialsException e1){
            model.addAttribute("msg","用户名或密码错误");
            return "login";
        }catch (Exception e2){
            model.addAttribute("msg","未知错误");
            return "login";
        }
    }
    /***
     * @author dinggc
     * Description //登出操作
     * Date 下午5:34 18-12-31
     * Param [request, response, model]
     * return java.lang.String
     **/
    @RequestMapping(value = "/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response, Model model){
        Subject subject = SecurityUtils.getSubject();
        if(subject!=null){
            SecurityUtils.getSubject().logout();
        }
        return "login";
    }
    /***
     * @author dinggc
     * Description //跳转到注册页面
     * Date 下午5:35 18-12-31
     * Param []
     * return java.lang.String
     **/
    @RequestMapping(value = "/signup")
    public String signup(){
        return "register";
    }
    /***
     * @author dinggc
     * Description //用户是否已经注册的判断
     * Date 下午5:35 18-12-31
     * Param [request]
     * return java.lang.String
     **/
    @RequestMapping(value = "/usercheck")
    @ResponseBody
    public String usercheck(HttpServletRequest request){
        String username = request.getParameter("username");
        Vuser vuser = userService.getVuserToCheckRepeat(username);
        if(vuser!=null){
            return "yes";
        }else{
            return "no";
        }
    }
    /***
     * @author dinggc
     * Description //用户邮箱是否认证的判断
     * Date 下午5:35 18-12-31
     * Param [request]
     * return java.lang.String
     **/
    @RequestMapping(value = "/emailcheck")
    @ResponseBody
    public String emailcheck(HttpServletRequest request){
        String email = request.getParameter("email");
        try{
            Vuser vuser = userService.getVuserToCheckRepeatEmail(email);
            if(vuser!=null){
                return "yes";
            }else{
                return "no";
            }
        }catch (Exception e){
            e.printStackTrace();
            return "other";
        }
    }
    /***
     * @author dinggc
     * Description //TODO
     * Date 用户注册实现逻辑 18-12-31
     * Param [vuser]
     * return java.lang.String
     **/
    @RequestMapping(value = "/register")
    public String register(Vuser vuser){
        try {
            String date = baseService.getCurrentTime();
            String uuid = baseService.getUUID();
            int emailconfirm = 1;
            vuser.setUuid(uuid);
            vuser.setEmailconfirm(emailconfirm);
            vuser.setDate(date);
            vuser.setDel_flag(0);
            userService.insertVuser(vuser);
            String email = vuser.getEmail();
            baseService.sendEmail(uuid,email);
            return "login";
        }catch (Exception e){
            e.printStackTrace();
            return "register";
        }
    }
    /***
     * @author dinggc
     * Description //邮箱验证
     * Date 下午5:36 18-12-31
     * Param [request, model]
     * return java.lang.String
     **/
    @RequestMapping(value = "/email")
    public String email(HttpServletRequest request,Model model){
        String uuid = request.getParameter("uuid");
        Vuser vuser = userService.getVuserByUUID(uuid);
        int emailconfirm = 0;
        vuser.setEmailconfirm(emailconfirm);
        try{
            userService.updateVuserByUUID(vuser);
            model.addAttribute("msg","验证成功！");
        }catch (Exception e){
            model.addAttribute("msg","验证失败！");
            e.printStackTrace();
        }
        return "mail";
    }
    /***
     * @author dinggc
     * Description //滑动验证码的验证
     * Date 下午5:36 18-12-31
     * Param [request, response]
     * return void
     **/
    @RequestMapping(value = "/StartCaptchaServlet")
    public void StartCaptchaServlet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        GeetestLib gtSdk = new GeetestLib(GeetestConfig.getGeetest_id(), GeetestConfig.getGeetest_key(),
                GeetestConfig.isnewfailback());

        String resStr = "{}";

        String userid = "test";

        //自定义参数,可选择添加
        HashMap<String, String> param = new HashMap<String, String>();
        param.put("user_id", userid);
        param.put("client_type", "web");
        param.put("ip_address", "127.0.0.1");

        //进行验证预处理
        int gtServerStatus = gtSdk.preProcess(param);

        //将服务器状态设置到session中
        request.getSession().setAttribute(gtSdk.gtServerStatusSessionKey, gtServerStatus);
        //将userid设置到session中
        request.getSession().setAttribute("userid", userid);

        resStr = gtSdk.getResponseStr();

        PrintWriter out = response.getWriter();
        out.println(resStr);
    }
}

