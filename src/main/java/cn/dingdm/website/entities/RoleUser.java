package cn.dingdm.website.entities;

import java.io.Serializable;
/***
 * @author dinggc
 * Description //TODO 
 * Date 下午5:28 18-12-31
 * Param 
 * return 
 **/
public class RoleUser implements Serializable {
    private int roleid;
    private int userid;

    public int getRoleid() {
        return roleid;
    }

    public void setRoleid(int roleid) {
        this.roleid = roleid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }
}
