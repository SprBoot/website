package cn.dingdm.website.entities;

import java.io.Serializable;
/***
 * @author dinggc
 * Description //个人项目中的人员实体类
 * Date 下午5:21 18-12-31
 * Param
 * return
 **/
public class Members extends BaseEntity implements Serializable {
    /**
     * id标识
     **/
    private int id;
    /**
     * 成员id
     **/
    private int cyid;
    /**
     * 成员在项目中的职位
     **/
    private String cyzw;
    /**
     * 成员姓名
     **/
    private String cymc;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getCyid() {
        return cyid;
    }
    public void setCyid(int cyid) {
        this.cyid = cyid;
    }
    public String getCyzw() {
        return cyzw;
    }
    public void setCyzw(String cyzw) {
        this.cyzw = cyzw;
    }
    public String getCymc() {
        return cymc;
    }
    public void setCymc(String cymc) {
        this.cymc = cymc;
    }
}
