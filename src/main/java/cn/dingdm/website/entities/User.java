package cn.dingdm.website.entities;

import java.io.Serializable;
/***
 * @author dinggc
 * Description //用户信息类
 * Date 下午5:28 18-12-31
 * Param
 * return
 **/
public class User extends BaseEntity implements Serializable {
    /**
     * id标识
     **/
    private int id;
    /**
     * 用户名
     **/
    private String name;
    /**
     * 工作
     **/
    private String gzly;
    /**
     * 个人介绍
     **/
    private String grjs;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getGzly() {
        return gzly;
    }
    public void setGzly(String gzly) {
        this.gzly = gzly;
    }
    public String getGrjs() {
        return grjs;
    }
    public void setGrjs(String grjs) {
        this.grjs = grjs;
    }
}
