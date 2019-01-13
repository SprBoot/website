package cn.dingdm.website.entities;
/***
 * @author dinggc
 * Description //shiro中的权限类
 * Date 下午5:25 18-12-31
 * Param
 * return
 **/
public class Permission extends BaseEntity{
    /**
     * id标识
     **/
    private int id;
    /**
     * 权限名称
     **/
    private String permission;
    /**
     * 权限描述
     **/
    private String describe;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getPermission() {
        return permission;
    }
    public void setPermission(String permission) {
        this.permission = permission;
    }
    public String getDescribe() {
        return describe;
    }
    public void setDescribe(String describe) {
        this.describe = describe;
    }
}
