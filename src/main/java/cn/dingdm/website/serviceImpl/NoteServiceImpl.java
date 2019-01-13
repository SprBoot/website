package cn.dingdm.website.serviceImpl;

import cn.dingdm.website.entities.*;
import cn.dingdm.website.mapper.NoteMapper;
import cn.dingdm.website.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteServiceImpl implements NoteService {

    @Autowired
    NoteMapper noteMapper;
    @Autowired
    RedisCacheManager redisCacheManager;

    @Override
    public int getNoteCount() {
        return noteMapper.getNoteCount();
    }

    @Override
    @Cacheable(cacheNames = "getNoteByCount")
    public List<Note> getNoteByCount(Page page) {
        int start = page.getCurrentResult();
        int end = start+page.getLimit();
        return noteMapper.getNoteByCount(start,end-start);
    }

    @Override
    @Cacheable(cacheNames = "getNoteType")
    public List<NoteType> getNoteType() {
        return noteMapper.getNoteType();
    }

    @Override
    @Cacheable(cacheNames = "getNoteTypeByCount")
    public List<Note> getNoteTypeByCount(String lxid, Page page) {
        int start = page.getCurrentResult();
        int end = start+page.getLimit();
        return noteMapper.getNoteTypeByCount(lxid,start,end-start);
    }

    @Override
    public int getNoteTypeCount(String lxid) {
        return noteMapper.getNoteTypeCount(lxid);
    }

    @Override
    @CachePut(cacheNames = "note")
    public Note getNoteById(int id) {
        return noteMapper.getNoteById(id);
    }

    @Override
    @CachePut(cacheNames = "minId")
    public int getMinId() {
        return noteMapper.getMinId();
    }

    @Override
    @CachePut(cacheNames = "maxId")
    public int getMaxId() {
        return noteMapper.getMaxId();
    }

    @Override
    public void insertComment(Comment comment) {
        noteMapper.insertComment(comment);
    }

    @Override
    @Cacheable(cacheNames = "getCommentByQuestionidAndFloor")
    public Object getCommentByQuestionidAndFloor(int questionid,int floor) {
        return noteMapper.getCommentByQuestionidAndFloor(questionid,floor);
    }

    @Override
    public List<Note> getNoteLikeName(String search, Page page) {
        int start = page.getCurrentResult();
        int end = start+page.getLimit();
        return noteMapper.getNoteLikeName(search,start,end-start);
    }

    @Override
    public int getNoteLikeBjmcCount(String search) {
        return noteMapper.getNoteLikeBjmcCount(search);
    }

    @Override
    public List<Comment> getSonCommentList(int questionid, String uuid) {
        return noteMapper.getSonCommentList(questionid,uuid);
    }

    @Override
    public Vuser getVuserByCommentId(int id) {
        return noteMapper.getVuserByCommentId(id);
    }

    @Override
    public List<Note> getAllNoteToBootstraoTable() {
        return noteMapper.getAllNoteToBootstraoTable();
    }

    @Override
    public int getFileSourceCount(int noteId) {
        return noteMapper.getFileSourceCount(noteId);
    }

    @Override
    public List<FileNote> getFileNoteByCount(int noteid,Page page) {
        int start = page.getCurrentResult();
        int end = start+page.getLimit();
        return noteMapper.getFileNoteByCount(noteid,start,end-start);
    }
}
