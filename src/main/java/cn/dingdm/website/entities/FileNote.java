package cn.dingdm.website.entities;

/**
 * @ClassName FileNote
 * @Description 笔记资源类
 * Author dinggc
 * Date 19-1-9 上午8:49
 * @Version 1.0
 **/
public class FileNote extends BaseEntity{
    /**
     * id标识
     **/
    private int id;
    /**
     * 笔记id
     **/
    private int noteid;
    /**
     * name
     **/
    private String name;
    /**
     * 类型
     **/
    private String type;
    /**
     * 描述
     **/
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNoteid() {
        return noteid;
    }

    public void setNoteid(int noteid) {
        this.noteid = noteid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
