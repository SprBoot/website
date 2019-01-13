package cn.dingdm.website.entities;

import java.io.Serializable;
/***
 * @author dinggc
 * Description //笔记实体类
 * Date 下午5:23 18-12-31
 * Param
 * return
 **/
public class Note extends BaseEntity implements Serializable {
    /**
     * id标识
     **/
    private int id;
    /**
     * 笔记类型id
     **/
    private String lxid;
    /**
     * 笔记名称
     **/
    private String bjmc;
    /**
     * 笔记作者
     **/
    private String bjzz;
    /**
     * 笔记内容
     **/
    private String bjnr;
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 资源文件描述
     **/
    private String description;


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getLxid() {
        return lxid;
    }
    public void setLxid(String lxid) {
        this.lxid = lxid;
    }
    public String getBjmc() {
        return bjmc;
    }
    public void setBjmc(String bjmc) {
        this.bjmc = bjmc;
    }
    public String getBjzz() {
        return bjzz;
    }
    public void setBjzz(String bjzz) {
        this.bjzz = bjzz;
    }
    public String getBjnr() {
        return bjnr;
    }
    public void setBjnr(String bjnr) {
        this.bjnr = bjnr;
    }
}
