package cn.dingdm.website.entities;

import java.io.Serializable;

/**
 * @author dinggc
 * Description //个人信息中的能力实体类
 * Date 下午5:08 18-12-31
 * Param
 * return
 **/
public class Abilities extends BaseEntity implements Serializable {
    /**
     * id标识
     **/
    private int id;
    /**
     * 能力id
     **/
    private int nlid;
    /**
     * 能力名称
     **/
    private String nlmc;
    /**
     * 能力掌握
     **/
    private String nlzw;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getNlid() {
        return nlid;
    }
    public void setNlid(int nlid) {
        this.nlid = nlid;
    }
    public String getNlmc() {
        return nlmc;
    }
    public void setNlmc(String nlmc) {
        this.nlmc = nlmc;
    }
    public String getNlzw() {
        return nlzw;
    }
    public void setNlzw(String nlzw) {
        this.nlzw = nlzw;
    }
}
