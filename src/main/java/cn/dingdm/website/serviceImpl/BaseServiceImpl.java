package cn.dingdm.website.serviceImpl;

import cn.dingdm.website.config.FtpConfig;
import cn.dingdm.website.entities.ActDeployment;
import cn.dingdm.website.entities.MsgRabbitMq;
import cn.dingdm.website.entities.Vuser;
import cn.dingdm.website.entities.WorkFlow;
import cn.dingdm.website.service.BaseService;
import net.sf.json.JSONObject;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.image.ProcessDiagramGenerator;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.amqp.core.*;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.imageio.ImageIO;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.zip.ZipInputStream;

@Service
public class BaseServiceImpl implements BaseService {

    @Autowired
    JavaMailSenderImpl javaMailSender;
    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    AmqpAdmin amqpAdmin;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    RepositoryService repositoryService;
    @Autowired
    HistoryService historyService;
    @Autowired
    ProcessEngineFactoryBean processEngine;
    @Autowired
    ProcessEngineConfiguration processEngineConfiguration;
    @Autowired
    FtpConfig ftpConfig;

    @Override
    public Vuser getCurrentVuser() {
        Subject subject = SecurityUtils.getSubject();
        Vuser principal = (Vuser)subject.getPrincipal();
        return principal;
    }

    @Override
    public String getCurrentTime() {
        Date date = new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String udate= sdf.format(date);
        return udate;
    }

    @Override
    public String getUUID() {
        UUID uuid=UUID.randomUUID();
        String str = uuid.toString();
        String uuidStr=str.replace("-", "");
        return uuidStr;
    }

    @Override
    public void sendEmail(String uuid, String email) {
        String sendTo = email;
        //创建一个复杂的消息邮件
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = null;
        try {
            mimeMessageHelper = new MimeMessageHelper(mimeMessage,true);
            //邮件设置
            mimeMessageHelper.setSubject("个人博客注册邮件");
            mimeMessageHelper.setText("<p>欢迎注册个人博客用户</p><br/><a href='http://www.xuxiaonan.cn/email?uuid="+uuid+"'>点此链接验证完成，再次登录</a>",true);
            mimeMessageHelper.setTo(sendTo);
            mimeMessageHelper.setFrom("88665291@qq.com");
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public JSONObject uploadFile(MultipartFile file, String path, HttpSession session, HttpServletRequest request) {
        // 生成一个文件名16位数字+字母
        String fileRandomNameId = getUUID();
        String fileRandomName = getUUID();
        String realName = file.getOriginalFilename();
        String suffixName = realName.substring(realName.lastIndexOf("."));
        fileRandomName += suffixName;
        path = "/opt/uploadImages/";
        File tempFile = new File(path, fileRandomName);
        if (!tempFile.getParentFile().exists()) {
            tempFile.getParentFile().mkdirs();
        }
        if (tempFile.exists()) {
            tempFile.delete();
        }
        try{
            tempFile.createNewFile();
            file.transferTo(tempFile);
        }catch (IOException e){
            e.printStackTrace();
        }
        session = request.getSession();
        session.setAttribute("picname", tempFile);
        session.setAttribute("multpart", file);
        session.setAttribute("path", path);
        session.setAttribute("fileName", fileRandomName);
        // 封装文件的信息
        JSONObject fileInfor = new JSONObject();
        fileInfor.put("fileId", fileRandomNameId);
        fileInfor.put("fileName", fileRandomName);
        fileInfor.put("fileType", suffixName);
        fileInfor.put("filePath", tempFile.getAbsolutePath());
        return fileInfor;
    }

    @Override
    public void send(String exchange,String queueName,Object Context) {
        rabbitTemplate.convertAndSend(exchange,queueName,Context);
    }

    @Override
    public MsgRabbitMq recv(String queueName, String uuid) {
        MsgRabbitMq msgrabbitmq = new MsgRabbitMq();
        String msg = null;
        Message message = (Message) rabbitTemplate.receive(queueName);
        if(message != null){
            byte[] body = message.getBody();
            msg = new String(body);
            msgrabbitmq.setUuid(uuid);
            msgrabbitmq.setContext(msg);
        }else{
            msgrabbitmq.setContext("暂无消息");
        }
        return msgrabbitmq;
    }

    @Override
    public void createExchange(String exchange, String queueName) {
        amqpAdmin.declareExchange(new DirectExchange(exchange));

        amqpAdmin.declareQueue(new Queue(queueName,true));

        //创建绑定规则
        amqpAdmin.declareBinding(new Binding(queueName, Binding.DestinationType.QUEUE,exchange,queueName,null));
    }

    @Override
    public void deployprocess(String zipname, String processname) {
        //部署
        String zip = "/opt/uploadImages/" + zipname;
        try {
            InputStream is = new BufferedInputStream(new FileInputStream(zip));
            ZipInputStream zipInputStream = new ZipInputStream(is);
            repositoryService.createDeployment().name(processname)//部署的名称
                    .addZipInputStream(zipInputStream)
                    .deploy();//确定部署
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getProcessImg(HttpServletRequest request, HttpServletResponse response) {
        String deploymentId = request.getParameter("deploymentId");
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(deploymentId).singleResult();
        String processInstanceId = processDefinition.getId();
        //获取历史流程实例
        ProcessDefinition processInstance =  repositoryService.createProcessDefinitionQuery().processDefinitionId(processInstanceId).singleResult();
        //获取流程图
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processInstance.getId());
        processEngineConfiguration = processEngine.getProcessEngineConfiguration();
        Context.setProcessEngineConfiguration((ProcessEngineConfigurationImpl) processEngineConfiguration);

        ProcessDiagramGenerator diagramGenerator = processEngineConfiguration.getProcessDiagramGenerator();
        ProcessDefinitionEntity definitionEntity = (ProcessDefinitionEntity)repositoryService.getProcessDefinition(processInstance.getId());

        List<HistoricActivityInstance> highLightedActivitList =  historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstanceId).list();
        //高亮环节id集合
        List<String> highLightedActivitis = new ArrayList<String>();

        //高亮线路id集合
        List<String> highLightedFlows = getHighLightedFlows(definitionEntity,highLightedActivitList);

        for(HistoricActivityInstance tempActivity : highLightedActivitList){
            String activityId = tempActivity.getActivityId();
            highLightedActivitis.add(activityId);
        }

       //中文显示的是口口口，设置字体就好了
        InputStream imageStream = diagramGenerator.generateDiagram(bpmnModel, "png", highLightedActivitis,highLightedFlows,"宋体","宋体","宋体",null,1.0);
        //单独返回流程图，不高亮显示
        //InputStream imageStream = diagramGenerator.generatePngDiagram(bpmnModel);
        // 输出资源内容到相应对象
        byte[] b = new byte[1024];
        int len;
        try {
            while ((len = imageStream.read(b, 0, 1024)) != -1) {
                response.getOutputStream().write(b, 0, len);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public List<String> getHighLightedFlows(ProcessDefinitionEntity processDefinitionEntity, List<HistoricActivityInstance> historicActivityInstances) {
        List<String> highFlows = new ArrayList<String>();// 用以保存高亮的线flowId
        for (int i = 0; i < historicActivityInstances.size() - 1; i++) {// 对历史流程节点进行遍历
            ActivityImpl activityImpl = processDefinitionEntity
                    .findActivity(historicActivityInstances.get(i)
                            .getActivityId());// 得到节点定义的详细信息
            List<ActivityImpl> sameStartTimeNodes = new ArrayList<ActivityImpl>();// 用以保存后需开始时间相同的节点
            ActivityImpl sameActivityImpl1 = processDefinitionEntity
                    .findActivity(historicActivityInstances.get(i + 1)
                            .getActivityId());
            // 将后面第一个节点放在时间相同节点的集合里
            sameStartTimeNodes.add(sameActivityImpl1);
            for (int j = i + 1; j < historicActivityInstances.size() - 1; j++) {
                HistoricActivityInstance activityImpl1 = historicActivityInstances
                        .get(j);// 后续第一个节点
                HistoricActivityInstance activityImpl2 = historicActivityInstances
                        .get(j + 1);// 后续第二个节点
                if (activityImpl1.getStartTime().equals(
                        activityImpl2.getStartTime())) {
                    // 如果第一个节点和第二个节点开始时间相同保存
                    ActivityImpl sameActivityImpl2 = processDefinitionEntity
                            .findActivity(activityImpl2.getActivityId());
                    sameStartTimeNodes.add(sameActivityImpl2);
                } else {
                    // 有不相同跳出循环
                    break;
                }
            }
            List<PvmTransition> pvmTransitions = activityImpl
                    .getOutgoingTransitions();// 取出节点的所有出去的线
            for (PvmTransition pvmTransition : pvmTransitions) {
                // 对所有的线进行遍历
                ActivityImpl pvmActivityImpl = (ActivityImpl) pvmTransition
                        .getDestination();
                // 如果取出的线的目标节点存在时间相同的节点里，保存该线的id，进行高亮显示
                if (sameStartTimeNodes.contains(pvmActivityImpl)) {
                    highFlows.add(pvmTransition.getId());
                }
            }
        }
        return highFlows;
    }
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
    public JSONObject saveToFtp(MultipartFile file) {
        String msg = "";
        JSONObject jsonObject = new JSONObject();
        if(file == null || file.isEmpty()){
            msg = "未发现文件";
        }
        //获取文件名
        // 生成一个文件名16位数字+字母
        String fileRandomName = getUUID();
        String realName = file.getOriginalFilename();
        String suffixName = realName.substring(realName.lastIndexOf("."));
        fileRandomName += suffixName;

        FTPClient ftpClient = new FTPClient();

        try {
            ftpClient.connect(ftpConfig.getHost(),ftpConfig.getPort());
            boolean ftplogin = ftpClient.login(ftpConfig.getUsername(),ftpConfig.getPassword());
            if(ftplogin){
                int reply = ftpClient.getReplyCode();
                if(FTPReply.isPositiveCompletion(reply)){
                    ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
                    ftpClient.setRemoteVerificationEnabled(false);
                    ftpClient.enterLocalPassiveMode();
                    //切换的目标目录
                    if(!ftpClient.changeWorkingDirectory(ftpConfig.getSavePath())){
                        String tempPath = ftpConfig.getSavePath();
                        if(!ftpClient.changeWorkingDirectory(tempPath)){
                            if(!ftpClient.makeDirectory(tempPath)){
                                msg = "创建路径！";
                            }else{
                                ftpClient.changeWorkingDirectory(tempPath);
                            }
                        }
                    }
                    //上传文件
                    boolean success = ftpClient.storeFile(fileRandomName,file.getInputStream());
                    msg = fileRandomName;
                    if(!success){
                        msg = "上传到ftp失败！";
                    }
                }else{
                    msg = "无应答！";
                }
            }else{
                msg = "登录失败！";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                ftpClient.logout();
                ftpClient.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        jsonObject.put("msg",msg);
        jsonObject.put("name",realName);
        return jsonObject;
    }

    @Override
    public List<MultipartFile> getMultipartFileList(HttpServletRequest request) {
        List<MultipartFile> files = new ArrayList<MultipartFile>();
        try {
            CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                    request.getSession().getServletContext());
            if (request instanceof MultipartHttpServletRequest) {
                // 将request变成多部分request
                MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
                Iterator<String> iter = multiRequest.getFileNames();
                // 检查form中是否有enctype="multipart/form-data"
                if (multipartResolver.isMultipart(request) && iter.hasNext()) {
                    // 获取multiRequest 中所有的文件名
                    while (iter.hasNext()) {
                        // 适配名字重复的文件
                        List<MultipartFile> fileRows = multiRequest
                                .getFiles(iter.next().toString());
                        if (fileRows != null && fileRows.size() != 0) {
                            for (MultipartFile file : fileRows) {
                                if (file != null && !file.isEmpty()) {
                                    files.add(file);
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return files;
    }

    @Override
    public Object ImageChange(String path,String fileName, int width, int height,HttpServletRequest request,String modify) throws Exception {
        // TODO Auto-generated method stub
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(path+fileName));
        // 字节流转图片对象
        Image bi = ImageIO.read(in);
        // 构建图片流
        BufferedImage tag = new BufferedImage(width , height , BufferedImage.TYPE_INT_RGB);
        // 绘制改变尺寸后的图
        boolean m = tag.getGraphics().drawImage(bi, 0, 0, width , height , null);
        System.out.println(m);
        // 输出流
        String ModifyfileName = fileName.substring(0, 16) + modify;
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        ModifyfileName += suffix;
        File out = new File(path,ModifyfileName);
        out.createNewFile();
        boolean n = ImageIO.write(tag, "jpg", out);
        System.out.println(n);
        in.close();
        return ModifyfileName;
    }
    public String getIdByUUId() {
        int machineId = 1;//最大支持1-9个集群机器部署
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        if(hashCodeV < 0) {//有可能是负数
            hashCodeV = - hashCodeV;
        }
        // 0 代表前面补充0
        // 4 代表长度为4
        // d 代表参数为正数型
        return machineId + String.format("%015d", hashCodeV);
    }
}
