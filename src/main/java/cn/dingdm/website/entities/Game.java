package cn.dingdm.website.entities;

import java.io.Serializable;
/***
 * @author dinggc
 * Description //生活页面的游戏展示的实体类
 * Date 下午5:18 18-12-31
 * Param
 * return
 **/
public class Game extends BaseEntity implements Serializable {
    /**
     * id标识
     **/
    private int id;
    /**
     * 游戏名称
     **/
    private String yxmc;
    /**
     * 游戏开发商
     **/
    private String yxkfs;
    /**
     * 游戏个人感受
     **/
    private String grgs;
    /**
     * 游戏图片id
     **/
    private String picid;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getYxmc() {
        return yxmc;
    }
    public void setYxmc(String yxmc) {
        this.yxmc = yxmc;
    }
    public String getYxkfs() {
        return yxkfs;
    }
    public void setYxkfs(String yxkfs) {
        this.yxkfs = yxkfs;
    }
    public String getGrgs() {
        return grgs;
    }
    public void setGrgs(String grgs) {
        this.grgs = grgs;
    }
    public String getPicid() {
        return picid;
    }
    public void setPicid(String picid) {
        this.picid = picid;
    }
}
