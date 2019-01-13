package cn.dingdm.website.entities;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
/***
 * @author dinggc
 * Description //自定义的流程任务实体类
 * Date 下午5:11 18-12-31
 * Param
 * return
 **/
public class ActTask {
    /**
     * id标识
     **/
    private String id;
    /**
     * 流程任务名称
     **/
    private String name;
    /**
     * 流程执行id
     **/
    private String executionId;
    /**
     * 流程任务描述
     **/
    private String description;
    /**
     * 流程任务创建时间
     **/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createtime;
    /**
     * 流程当前的显示信息类
     **/
    private BackAppl backAppl;

    public BackAppl getBackAppl() {
        return backAppl;
    }

    public void setBackAppl(BackAppl backAppl) {
        this.backAppl = backAppl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExecutionId() {
        return executionId;
    }

    public void setExecutionId(String executionId) {
        this.executionId = executionId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}
