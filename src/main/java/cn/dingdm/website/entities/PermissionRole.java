package cn.dingdm.website.entities;
/***
 * @author dinggc
 * Description //
 * Date 下午5:25 18-12-31
 * Param
 * return
 **/
public class PermissionRole extends Permission{
    private int id;
    private String permission;
    private String describe;
    private Role role;

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public int getId() {
        return id;
    }
    @Override
    public void setId(int id) {
        this.id = id;
    }
    @Override
    public String getPermission() {
        return permission;
    }
    @Override
    public void setPermission(String permission) {
        this.permission = permission;
    }
    @Override
    public String getDescribe() {
        return describe;
    }
    @Override
    public void setDescribe(String describe) {
        this.describe = describe;
    }
}
