package cn.dingdm.website.service;

import cn.dingdm.website.entities.ActDeployment;
import cn.dingdm.website.entities.ActProcdef;
import cn.dingdm.website.entities.WorkFlow;
import cn.dingdm.website.entities.Page;
import org.activiti.engine.repository.Deployment;

import java.util.List;
import java.util.Map;

public interface ActivitiService {
    /**
     * 获取所有的部署流程
     */
    public List<ActDeployment> getAllDeployMent(WorkFlow workFlow);

    public long getDeploymentCount(WorkFlow workFlow);

    /**
     * 获取流程定义详细信息
     */
    public int getProcDefCount(WorkFlow workFlow);

    public List<ActProcdef> getAllProcDef(Page page,WorkFlow workFlow);

    /**
     * 删除部署的流程
     */
    public void deleteDeployment(String deploymentId);
    //启动流程
    public void startProcess(String deploymentId,String outcome,String username,int id);
    //完成任务
    public void complete(String taskId);
    //使用流程变量完成任务
    public void completeTaskWithVariables(String taskId,String variable);
    //查看当前审批的阶段
    public String viewProcess(String deploymentid);
    //判断流程是否结束
    public boolean isProcessComplete(String processInstanceId);
    //获取task中被驳回的请求处理人的用户名的任务实例Id
    public String dowmRequestProcessInstanceId(String taskId);
}
