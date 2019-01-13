package cn.dingdm.website.entities;

import java.io.Serializable;
/***
 * @author dinggc
 * Description //生活页面的人生感悟实体类
 * Date 下午5:20 18-12-31
 * Param
 * return
 **/
public class Life extends BaseEntity implements Serializable {
    /**
     * id标识
     **/
    private int id;
    /**
     * 文章名称
     **/
    private String mc;
    /**
     * 文章作者
     **/
    private String auth;
    /**
     * 文章内容
     **/
    private String js;
    /**
     * 文章的图片id
     **/
    private String picid;
    public String getJs() {
        return js;
    }
    public void setJs(String js) {
        this.js = js;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getMc() {
        return mc;
    }
    public void setMc(String mc) {
        this.mc = mc;
    }
    public String getAuth() {
        return auth;
    }
    public void setAuth(String auth) {
        this.auth = auth;
    }
    public String getPicid() {
        return picid;
    }
    public void setPicid(String picid) {
        this.picid = picid;
    }
}
