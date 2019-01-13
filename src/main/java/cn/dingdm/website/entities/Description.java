package cn.dingdm.website.entities;

import java.io.Serializable;
import java.util.List;
/***
 * @author dinggc
 * Description //个人信息中的项目实体类
 * Date 下午5:17 18-12-31
 * Param
 * return
 **/
public class Description extends BaseEntity implements Serializable {
    /**
     * id标识
     **/
    private int id;
    /**
     * 项目id
     **/
    private int xmid;
    /**
     * 项目图片id
     **/
    private String picid;
    /**
     * 项目内容
     **/
    private String xmnr;
    /**
     * 项目类型
     **/
    private String xmlx;
    /**
     * 项目成员
     **/
    private List<Members> membersList;

    public List<Members> getMembersList() {
        return membersList;
    }

    public void setMembersList(List<Members> membersList) {
        this.membersList = membersList;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getXmid() {
        return xmid;
    }
    public void setXmid(int xmid) {
        this.xmid = xmid;
    }
    public String getPicid() {
        return picid;
    }
    public void setPicid(String picid) {
        this.picid = picid;
    }
    public String getXmnr() {
        return xmnr;
    }
    public void setXmnr(String xmnr) {
        this.xmnr = xmnr;
    }
    public String getXmlx() {
        return xmlx;
    }
    public void setXmlx(String xmlx) {
        this.xmlx = xmlx;
    }
}
