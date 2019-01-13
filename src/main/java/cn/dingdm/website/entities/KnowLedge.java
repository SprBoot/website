package cn.dingdm.website.entities;

import java.io.Serializable;
/***
 * @author dinggc
 * Description //个人信息页面的知识实体类
 * Date 下午5:19 18-12-31
 * Param
 * return
 **/
public class KnowLedge extends BaseEntity implements Serializable {
    /**
     * id标识
     **/
    private int id;
    /**
     * 知识id
     **/
    private int zsid;
    /**
     * 知识名称
     **/
    private String zsmc;
    /**
     * 知识的掌握程度，百分比
     **/
    private int percent;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getZsid() {
        return zsid;
    }
    public void setZsid(int zsid) {
        this.zsid = zsid;
    }
    public String getZsmc() {
        return zsmc;
    }
    public void setZsmc(String zsmc) {
        this.zsmc = zsmc;
    }
    public int getPercent() {
        return percent;
    }
    public void setPercent(int percent) {
        this.percent = percent;
    }
}
