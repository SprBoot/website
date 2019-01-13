package cn.dingdm.website.entities;

import java.io.Serializable;
/***
 * @author dinggc
 * Description //TODO 
 * Date 下午5:27 18-12-31
 * Param 
 * return 
 **/
public class RolePermission implements Serializable {
    private int id;
    private int roleid;
    private int perid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoleid() {
        return roleid;
    }

    public void setRoleid(int roleid) {
        this.roleid = roleid;
    }

    public int getPerid() {
        return perid;
    }

    public void setPerid(int perid) {
        this.perid = perid;
    }
}
