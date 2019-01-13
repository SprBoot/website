package cn.dingdm.website.entities;

import java.util.List;
/***
 * @author dinggc
 * Description //shiro认证角色类
 * Date 下午5:27 18-12-31
 * Param
 * return
 **/
public class Role extends BaseEntity{
    /**
     * id标识
     **/
    private int id;
    /**
     * 角色名称
     **/
    private String role;
    /**
     * 角色描述
     **/
    private String describe;
    /**
     * 拜访用户
     **/
    private List<Vuser> vuserList;
    /**
     * 角色对应的权限
     **/
    private List<Permission> permissionList;

    public List<Permission> getPermissionList() {
        return permissionList;
    }

    public void setPermissionList(List<Permission> permissionList) {
        this.permissionList = permissionList;
    }

    public List<Vuser> getVuserList() {
        return vuserList;
    }

    public void setVuserList(List<Vuser> vuserList) {
        this.vuserList = vuserList;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public String getDescribe() {
        return describe;
    }
    public void setDescribe(String describe) {
        this.describe = describe;
    }
}
