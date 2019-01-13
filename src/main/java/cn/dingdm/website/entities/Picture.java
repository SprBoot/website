package cn.dingdm.website.entities;

/**
 * @ClassName Picture
 * @Description 图片实体类
 * Author dinggc
 * Date 19-1-2 下午7:36
 * @Version 1.0
 **/
public class Picture extends BaseEntity{
    /***
     * id标识
     **/
    private int id;
    /***
     * 图片id
     **/
    private String pid;
    /***
     * 图片名称
     **/
    private String pname;
    /***
     * 图片路径
     **/
    private String address;
    /***
     * 图片类型
     **/
    private String type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
