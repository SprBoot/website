package cn.dingdm.website.entities;

import java.io.Serializable;
/**
 * @author dinggc
 * Description //笔记类型实体类
 * Date 下午5:23 18-12-31
 * Param
 * return
 **/
public class NoteType extends BaseEntity implements Serializable {
    /**
     * id标识
     **/
    private String id;
    /**
     * 笔记类型的名称
     **/
    private String lxmc;
    /**
     * 笔记类型的描述信息
     **/
    private String description;
    /**
     * 类型中笔记的数量
     **/
    private int number;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLxmc() {
        return lxmc;
    }

    public void setLxmc(String lxmc) {
        this.lxmc = lxmc;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
