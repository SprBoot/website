package cn.dingdm.website.entities;

import java.util.Date;
/***
 * @author dinggc
 * Description //自定义的流程部署类
 * Date 下午5:09 18-12-31
 * Param
 * return
 **/
public class ActDeployment {
    /**
     * id标识
     **/
    private String id;
    /**
     * 部署流程的名称
     **/
    private String name;
    /**
     * 流程部署的时间
     **/
    private Date deploymentTime;

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

    public Date getDeploymentTime() {
        return deploymentTime;
    }

    public void setDeploymentTime(Date deploymentTime) {
        this.deploymentTime = deploymentTime;
    }
}
