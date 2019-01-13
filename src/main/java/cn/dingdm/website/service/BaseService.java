package cn.dingdm.website.service;

import cn.dingdm.website.entities.ActDeployment;
import cn.dingdm.website.entities.MsgRabbitMq;
import cn.dingdm.website.entities.Vuser;
import cn.dingdm.website.entities.WorkFlow;
import net.sf.json.JSONObject;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public interface BaseService {
    public Vuser getCurrentVuser();
    public String getCurrentTime();
    public String getUUID();
    /**
     * @author dinggc
     * Description //TODO 
     * Date 下午12:25 18-12-29
     * Param [uuid, email]
     * return void
     **/
    public void sendEmail(String uuid,String email);
    public JSONObject uploadFile(MultipartFile file, String path, HttpSession session, HttpServletRequest request);
    public void send(String exchange,String queueName,Object Context);
    public MsgRabbitMq recv(String str, String uuid);
    public void createExchange(String exchange,String queueName);
    public void deployprocess(String zipname,String processname);
    public void getProcessImg(HttpServletRequest request,HttpServletResponse response);
    /**
     * @author dinggc
     * Description //TODO 
     * Date 下午12:25 18-12-29
     * Param [processDefinitionEntity, historicActivityInstances]
     * return java.util.List<java.lang.String>
     **/
    public List<String> getHighLightedFlows(ProcessDefinitionEntity processDefinitionEntity, List<HistoricActivityInstance> historicActivityInstances);
    public List<ActDeployment> getAllDeployMent(WorkFlow workFlow);
    /**
     * @author dinggc
     * Description //TODO
     * Date 下午12:24 18-12-29
     * Param [file]
     * return java.lang.String
     **/
    public JSONObject saveToFtp(MultipartFile file);
    /***
     * @author dinggc
     * Description //获取request中的文件列表
     * Date 上午10:35 19-1-2
     * Param [request]
     * return java.util.List<org.springframework.web.multipart.MultipartFile>
     **/
    public List<MultipartFile> getMultipartFileList(HttpServletRequest request);
    /***
     * @author dinggc
     * Description //切割图片大小
     * Date 上午8:25 19-1-3
     * Param [path, filename, width, height, request, modify]
     * return java.lang.Object
     **/
    public Object ImageChange(String path,String filename,int width,int height,HttpServletRequest request,String modify)throws Exception;
}

