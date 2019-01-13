package cn.dingdm.website.mapper;

import cn.dingdm.website.entities.*;
import org.apache.ibatis.annotations.Param;


import java.util.List;

public interface NoteMapper {
    /***
     * @author dinggc
     * Description //获取
     * Date 上午8:35 19-1-2
     * Param []
     * return int
     **/
    public int getNoteCount();
    /***
     * @author dinggc
     * Description //获取笔记数量用于分页
     * Date 上午8:35 19-1-2
     * Param [start, end]
     * return java.util.List<cn.dingdm.website.entities.Note>
     **/
    public List<Note> getNoteByCount(@Param("start") int start,@Param("end") int end);
    /***
     * @author dinggc
     * Description //获取笔记类型
     * Date 上午8:36 19-1-2
     * Param []
     * return java.util.List<cn.dingdm.website.entities.NoteType>
     **/
    public List<NoteType> getNoteType();
    /***
     * @author dinggc
     * Description //获取笔记类型数量
     * Date 上午8:36 19-1-2
     * Param [id]
     * return int
     **/
    public int getNoteTypeCount(@Param("lxid") String id);
    /***
     * @author dinggc
     * Description //获取笔记类型数量用于分页
     * Date 上午8:37 19-1-2
     * Param [lxid, start, end]
     * return java.util.List<cn.dingdm.website.entities.Note>
     **/
    public List<Note> getNoteTypeByCount(@Param("lxid") String lxid,@Param("start") int start,@Param("end") int end);
    /***
     * @author dinggc
     * Description //根据id获取笔记
     * Date 上午8:37 19-1-2
     * Param [id]
     * return cn.dingdm.website.entities.Note
     **/
    public Note getNoteById(int id);
    /***
     * @author dinggc
     * Description //获取笔记最小id
     * Date 上午8:37 19-1-2
     * Param []
     * return int
     **/
    public int getMinId();
    /***
     * @author dinggc
     * Description //获取笔记最大id
     * Date 上午8:38 19-1-2
     * Param []
     * return int
     **/
    public int getMaxId();
    /***
     * @author dinggc
     * Description //插入评论
     * Date 上午8:38 19-1-2
     * Param [comment]
     * return void
     **/
    public void insertComment(Comment comment);
    /***
     * @author dinggc
     * Description //根据问题和层级获取评论内容
     * Date 上午8:38 19-1-2
     * Param [questionid, floor]
     * return java.lang.Object
     **/
    public Object getCommentByQuestionidAndFloor(@Param("questionid") int questionid,@Param("floor") int floor);
    /***
     * @author dinggc
     * Description //搜索相似笔记内容
     * Date 上午8:38 19-1-2
     * Param [bjmc, start, end]
     * return java.util.List<cn.dingdm.website.entities.Note>
     **/
    public List<Note> getNoteLikeName(@Param("bjmc") String bjmc,@Param("start") int start,@Param("end") int end);
    /***
     * @author dinggc
     * Description //获取搜索内容
     * Date 上午8:39 19-1-2
     * Param [search]
     * return int
     **/
    public int getNoteLikeBjmcCount(String search);
    /***
     * @author dinggc
     * Description //获取子级评论列表
     * Date 上午8:39 19-1-2
     * Param [questionid, uuid]
     * return java.util.List<cn.dingdm.website.entities.Comment>
     **/
    public List<Comment> getSonCommentList(@Param("questionid") int questionid,@Param("uuid") String uuid);
    /***
     * @author dinggc
     * Description //根据用户的id获取用户
     * Date 上午8:40 19-1-2
     * Param [id]
     * return cn.dingdm.website.entities.Vuser
     **/
    public Vuser getVuserByCommentId(@Param("id") int id);
    /***
     * @author dinggc
     * Description //获取所有的笔记
     * Date 上午8:41 19-1-2
     * Param []
     * return java.util.List<cn.dingdm.website.entities.Note>
     **/
    public List<Note> getAllNoteToBootstraoTable();
    /**
     * @author dinggc
     * Description //根据笔记id获取笔记资源数量
     * Date 下午5:46 19-1-10
     * Param [noteId]
     * return int
     **/
    public int getFileSourceCount(int noteId);
    /***
     * @author dinggc
     * Description //分页获取笔记的文件资源
     * Date 下午5:57 19-1-10
     * Param [noteid, start, end]
     * return java.util.List<cn.dingdm.website.entities.FileNote>
     **/
    public List<FileNote> getFileNoteByCount(@Param("noteid") int noteid,@Param("start") int start,
                                             @Param("end") int end);
}
