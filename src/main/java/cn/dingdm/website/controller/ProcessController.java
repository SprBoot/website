package cn.dingdm.website.controller;

import cn.dingdm.website.Util.JsonDateValueProcessor;
import cn.dingdm.website.entities.ActDeployment;
import cn.dingdm.website.entities.ActProcdef;
import cn.dingdm.website.entities.Page;
import cn.dingdm.website.entities.WorkFlow;
import cn.dingdm.website.service.ActivitiService;
import cn.dingdm.website.service.BaseService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/***
 * @author dinggc
 * Description //流程管理Controller
 * Date 下午5:53 18-12-31
 * Param
 * return
 **/
@Controller
@RequestMapping(value = "/process")
public class ProcessController {

    @Autowired
    ActivitiService activitiService;
    @Autowired
    BaseService baseService;

    /***
     * @author dinggc
     * Description //跳转到流程查看页面
     * Date 下午5:53 18-12-31
     * Param []
     * return java.lang.String
     **/
    @RequestMapping(value = "/processView")
    public String processView(){

        return "processView";
    }
    /***
     * @author dinggc
     * Description //跳转到流程部署页面
     * Date 下午5:53 18-12-31
     * Param []
     * return java.lang.String
     **/
    @RequestMapping(value = "/processDeploy")
    public String processDeploy(){
        return "processDeploy";
    }
    /***
     * @author dinggc
     * Description //得到所有的部署流程并返回
     * Date 下午5:53 18-12-31
     * Param [workFlow]
     * return java.lang.Object
     **/
    @RequestMapping(value = "/getDeploymentTable")
    @ResponseBody
    public Object getDeploymentTable(WorkFlow workFlow){
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
        List<ActDeployment> deployments = activitiService.getAllDeployMent(workFlow);
        JSONArray jsonArray = JSONArray.fromObject(deployments,jsonConfig);
        return jsonArray;
    }
    /***
     * @author dinggc
     * Description //获取所有的流程定义并返回
     * Date 下午5:53 18-12-31
     * Param [start, limit, nowPage, Number, workFlow]
     * return java.lang.Object
     **/
    @ResponseBody
    @RequestMapping("/getAllProcDef")
    public Object getAllProcDef(Integer start, Integer limit, Integer nowPage, Integer Number, WorkFlow workFlow){
        Page page = new Page(start,limit);
        page.setCurrentPage(nowPage);
        page.setTotal(activitiService.getProcDefCount(workFlow));
        List<ActProcdef> actProcdefs = activitiService.getAllProcDef(page,workFlow);
        page.setRoot(actProcdefs);
        return page;
    }
    /***
     * @author dinggc
     * Description //删除部署的流程
     * Date 下午5:54 18-12-31
     * Param [request]
     * return java.lang.String
     **/
    @RequestMapping(value = "/deleteDeployment")
    public String deleteDeployment(HttpServletRequest request){
        String deploymentId = request.getParameter("deploymentId");
        activitiService.deleteDeployment(deploymentId);
        return "redirect:/process/processView";
    }
    /***
     * @author dinggc
     * Description //上传zip文件
     * Date 下午5:54 18-12-31
     * Param [file, request, session]
     * return java.lang.Object
     **/
    @RequestMapping(value = "/saveZip")
    @ResponseBody
    public Object saveFile(MultipartFile file, HttpServletRequest request, HttpSession session){
        JSONObject jsonObject = new JSONObject();
        try{
            //jsonObject = baseService.uploadFile(file, path, session, request);
            baseService.saveToFtp(file);
        }catch (Exception e){
            e.printStackTrace();
        }
        return jsonObject;
    }
    /***
     * @author dinggc
     * Description //部署流程
     * Date 下午5:54 18-12-31
     * Param [request]
     * return java.lang.String
     **/
    @RequestMapping(value = "/deployProcess")
    public String deployProcess(HttpServletRequest request){
        String name = request.getParameter("processname");
        String zip = request.getParameter("zipname");
        baseService.deployprocess(zip,name);
        return "redirect:/process/processView";
    }
    /***
     * @author dinggc
     * Description //获取流程图
     * Date 下午5:54 18-12-31
     * Param [request, response]
     * return void
     **/
    @RequestMapping(value = "/getProcessImg")
    public void getProcessImg(HttpServletRequest request, HttpServletResponse response){
        baseService.getProcessImg(request,response);
    }
}
