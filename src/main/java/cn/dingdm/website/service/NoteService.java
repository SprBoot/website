package cn.dingdm.website.service;

import cn.dingdm.website.entities.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NoteService {
    public int getNoteCount();
    public List<Note> getNoteByCount(Page page);
    public List<NoteType> getNoteType();
    public int getNoteTypeCount(String lxid);
    public List<Note> getNoteTypeByCount(String lxid,Page page);
    public Note getNoteById(int id);
    public int getMinId();
    public int getMaxId();
    public void insertComment(Comment comment);
    public Object getCommentByQuestionidAndFloor(int questionid,int floor);
    public List<Note> getNoteLikeName(String search,Page page);
    public int getNoteLikeBjmcCount(String search);
    public List<Comment> getSonCommentList(@Param("questionid") int questionid,@Param("uuid") String uuid);
    public Vuser getVuserByCommentId(@Param("id") int id);
    //获取所有的笔记
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
    public List<FileNote> getFileNoteByCount(@Param("noteid") int noteid,Page page);
}
