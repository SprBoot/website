package cn.dingdm.website.entities;

import java.io.Serializable;
/***
 * @author dinggc
 * Description //项目实体类
 * Date 下午5:26 18-12-31
 * Param
 * return
 **/
public class Project extends BaseEntity implements Serializable {
    /**
     * id标识
     **/
    private int id;
    /**
     * 项目名称
     **/
    private String xmmc;
    /**
     * 项目描述
     **/
    private Description description;

    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getXmmc() {
        return xmmc;
    }
    public void setXmmc(String xmmc) {
        this.xmmc = xmmc;
    }
}
