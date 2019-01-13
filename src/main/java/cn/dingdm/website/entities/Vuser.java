package cn.dingdm.website.entities;

/***
 * @author dinggc
 * Description //拜访用户类
 * Date 下午5:28 18-12-31
 * Param
 * return
 **/
public class Vuser extends BaseEntity{
    /**
     * id标识
     **/
    private int id;
    /**
     * 用户名
     **/
    private String username;
    /**
     * 密码
     **/
    private String password;
    /**
     * 邮箱
     **/
    private String email;
    /**
     * 用户id
     **/
    public String uuid;
    /**
     * 邮箱确认标识
     **/
    public int emailconfirm;
    public Role role;

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getEmailconfirm() {
        return emailconfirm;
    }

    public void setEmailconfirm(int emailconfirm) {
        this.emailconfirm = emailconfirm;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}
