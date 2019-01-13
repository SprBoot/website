package cn.dingdm.website.entities;
/***
 * @author dinggc
 * Description //放置存在标记和时间的基本类
 * Date 下午5:14 18-12-31
 * Param
 * return
 **/
public class BaseEntity {
    /**
     * 日期
     **/
    private String date;
    /**
     * 存在标记
     **/
    private int del_flag;

    public BaseEntity() {
        super();
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getDel_flag() {
        return del_flag;
    }

    public void setDel_flag(int del_flag) {
        this.del_flag = del_flag;
    }
}
