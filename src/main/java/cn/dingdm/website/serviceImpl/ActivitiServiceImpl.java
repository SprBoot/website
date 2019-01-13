package cn.dingdm.website.serviceImpl;

import cn.dingdm.website.entities.*;
import cn.dingdm.website.mapper.MformMapper;
import cn.dingdm.website.service.ActivitiService;
import cn.dingdm.website.service.BaseService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ActivitiServiceImpl implements ActivitiService {

    @Autowired
    RepositoryService repositoryService;
    @Autowired
    RuntimeService runtimeService;
    @Autowired
    TaskService taskService;
    @Autowired
    MformMapper mformMapper;
    @Autowired
    BaseService baseService;

    @Override
    public List<ActDeployment> getAllDeployMent(WorkFlow workFlow) {
        String deploy = workFlow.getName()!=null?workFlow.getName():"";
        List<Deployment> deployments = repositoryService.createDeploymentQuery().deploymentNameLike("%" + deploy + "%").list();
        List<ActDeployment> actDeployments = new ArrayList<ActDeployment>();
        for(Deployment deployment : deployments){
            ActDeployment actDeployment = new ActDeployment();
            BeanUtils.copyProperties(deployment,actDeployment);
            actDeployments.add(actDeployment);
        }
        return actDeployments;
    }

    @Override
    public long getDeploymentCount(WorkFlow workFlow) {
        String deploy = workFlow.getName()!=null?workFlow.getName():"";
        long count = repositoryService.createDeploymentQuery().deploymentNameLike("%" + deploy + "%").count();
        return count;
    }

    @Override
    public int getProcDefCount(WorkFlow workFlow) {
        String deploy = workFlow.getName()!=null?workFlow.getName():"";
        long count = repositoryService.createProcessDefinitionQuery().processDefinitionNameLike("%" + deploy + "%").count();
        return (int)count;
    }

    @Override
    public List<ActProcdef> getAllProcDef(Page page, WorkFlow workFlow) {
        String deploy = workFlow.getName()!=null?workFlow.getName():"";
        int start = page.getCurrentResult();
        List<ProcessDefinition> processDefinitions = repositoryService.createProcessDefinitionQuery().processDefinitionNameLike("%" + deploy + "%").listPage(start, page.getLimit());
        List<ActProcdef> actProcdefs = new ArrayList<ActProcdef>();
        for(ProcessDefinition processDefinition : processDefinitions){
            ActProcdef actProcdef = new ActProcdef();
            BeanUtils.copyProperties(processDefinition,actProcdef);
            actProcdefs.add(actProcdef);
        }
        return actProcdefs;
    }

    @Override
    public void deleteDeployment(String deploymentId) {
        repositoryService.deleteDeployment(deploymentId);
    }

    @Override
    public void startProcess(String deploymentId, String outcome,String username,int id) {
        //根据流程定义的KEY去启用流程
        Deployment deployment = repositoryService.createDeploymentQuery().deploymentName(deploymentId).singleResult();
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).singleResult();
        String processDefinitionKey = processDefinition.getKey();
        BackAppl backAppl = mformMapper.getBackAppl(id);
        //根据流程定义的KEY去启用流程，并设置流程变量 variables必须是Map<String,Object>的对象
        Map<String,Object> variables = new HashMap<>();
        variables.put(processDefinition.getId(),backAppl);
        variables.put("outcome",outcome);
        variables.put("username",username);
        variables.put("info",backAppl);
        runtimeService.startProcessInstanceByKey(processDefinitionKey, variables);
    }

    @Override
    public void complete(String taskId) {
        //通过流程变量控制连线的走向

        taskService.complete(taskId);
    }

    @Override
    public void completeTaskWithVariables(String taskId, String variable) {
        //得到任务的Service
        Vuser currentVuser = baseService.getCurrentVuser();
        taskService.claim(taskId,currentVuser.getUsername());
        Map<String,Object> variables = new HashMap<>();
        variables.put("outcome",variable);
        //通过流程变量控制连线的走向
        taskService.complete(taskId,variables);
    }

    @Override
    public String viewProcess(String deploymentid) {
        Deployment deployment = repositoryService.createDeploymentQuery().deploymentName(deploymentid).singleResult();
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).singleResult();
        Task task = taskService.createTaskQuery().processDefinitionId(processDefinition.getId()).singleResult();
        return task.getName();
    }

    @Override
    public boolean isProcessComplete(String processInstanceId) {
        Execution execution = runtimeService.createExecutionQuery().processInstanceId(processInstanceId).singleResult();
        try{
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(execution.getProcessInstanceId()).singleResult();
        }catch (NullPointerException e){
            e.printStackTrace();
            return true;
        }
        return false;
    }

    @Override
    public String dowmRequestProcessInstanceId(String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        return processInstanceId;
    }

}
