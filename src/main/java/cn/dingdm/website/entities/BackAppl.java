package cn.dingdm.website.entities;

import java.io.Serializable;
/***
 * @author dinggc
 * Description //流程显示信息实体类
 * Date 下午5:13 18-12-31
 * Param
 * return
 **/
public class BackAppl extends BaseEntity implements Serializable {
    /**
     * 序列化id
     **/
    private static final long serialVersionUID = 1L;
    /**
     * id标识
     **/
    private int id;
    /**
     * 发起流程的标题
     **/
    private String title;
    /**
     * 发起流程的描述
     **/
    private String describe;
    /**
     * 发起流程的状态
     **/
    private int state;
    /**
     * 发起人的用户id
     **/
    private int userid;
    /**
     * 流程部署id
     **/
    private String deploymentid;
    /**
     * 流程的注释
     **/
    private String zhushi;
    /**
     * 流程开始时间
     **/
    private String createtime;

    public String getZhushi() {
        return zhushi;
    }

    public void setZhushi(String zhushi) {
        this.zhushi = zhushi;
    }

    public String getDeploymentid() {
        return deploymentid;
    }

    public void setDeploymentid(String deploymentid) {
        this.deploymentid = deploymentid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }
}
