package cn.dingdm.website.entities;

import java.util.Date;
/***
 * @author dinggc
 * Description //工作流自定义类
 * Date 下午5:29 18-12-31
 * Param
 * return
 **/

public class WorkFlow {
    /**
     * id标识
     **/
    private String id;
    /**
     * 流程名称
     **/
    private String name;
    /**
     * 时间
     **/
    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

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

}
