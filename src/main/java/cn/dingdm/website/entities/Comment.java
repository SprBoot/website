package cn.dingdm.website.entities;

import java.io.Serializable;
import java.util.List;
/***
 * @author dinggc
 * Description //评论实体类
 * Date 下午5:15 18-12-31
 * Param
 * return
 **/
public class Comment extends BaseEntity implements Serializable {
    /**
     * id标识
     **/
    private int id;
    /**
     * 评论层数
     **/
    private int floor;
    /**
     * 评论的问题id
     **/
    private int questionid;
    /**
     * 问题或者评论信息的作者id
     **/
    private int authorid;
    /**
     * 评论人的id
     **/
    private int commentid;
    /**
     * 在级联回复中，用于标识一个总的评论的id
     **/
    private String uuid;
    /**
     * 评论内容
     **/
    private String context;
    /**
     * 评论的子评论，及父子评论
     **/
    private List<Comment> comments;
    /**
     * 评论人的用户名
     **/
    private String commentName;
    /**
     * 被评论者的用户名
     **/
    private String authorName;

    public String getCommentName() {
        return commentName;
    }

    public void setCommentName(String commentName) {
        this.commentName = commentName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public int getQuestionid() {
        return questionid;
    }

    public void setQuestionid(int questionid) {
        this.questionid = questionid;
    }

    public int getAuthorid() {
        return authorid;
    }

    public void setAuthorid(int authorid) {
        this.authorid = authorid;
    }

    public int getCommentid() {
        return commentid;
    }

    public void setCommentid(int commentid) {
        this.commentid = commentid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }
}
