package cn.dingdm.website.controller;

import cn.dingdm.website.entities.*;
import cn.dingdm.website.service.BaseService;
import cn.dingdm.website.service.NoteService;
import cn.dingdm.website.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
/***
 * @author dinggc
 * Description //笔记Controller
 * Date 下午5:48 18-12-31
 * Param
 * return
 **/
@Controller
public class NoteController {

    @Autowired
    NoteService noteService;
    @Autowired
    BaseService baseService;
    @Autowired
    UserService userService;
    /***
     * @author dinggc
     * Description //封装笔记类型信息跳转到笔记页面
     * Date 下午5:49 18-12-31
     * Param [model]
     * return java.lang.String
     **/
    @RequestMapping(value = "/note")
    public String note(Model model){
        try{
            List<NoteType> noteTypes = noteService.getNoteType();
            int notecount = noteService.getNoteCount();
            for(NoteType noteType : noteTypes){
                String lxid = noteType.getId();
                int count = noteService.getNoteTypeCount(lxid);
                noteType.setNumber(count);
            }
            model.addAttribute("NoteType",noteTypes);
            model.addAttribute("count",notecount);
        }catch (Exception e){

        }
        return "note";
    }
    /***
     * @author dinggc
     * Description //获取所有的笔记信息并返回
     * Date 下午5:49 18-12-31
     * Param [start, limit, nowPage, Number]
     * return java.lang.Object
     **/
    @ResponseBody
    @RequestMapping("/getnoteIformation")
    public Object getnoteIformation(Integer start, Integer limit,Integer nowPage, Integer Number){
        //代理对象
        Page page = new Page(start,limit);
        page.setCurrentPage(nowPage);
        try{
            page.setTotal(noteService.getNoteCount());
            List<Note> noteList = noteService.getNoteByCount(page);
            for(Note note:noteList){
                String str = note.getBjnr();
                if(str.length()>15){
                    str = str.substring(0,15);
                    note.setBjnr(str+"......");
                }
            }
            page.setRoot(noteList);
        }catch (Exception e){

        }
        return page;
    }
    /***
     * @author dinggc
     * Description //根据点击的笔记类型获取相应的信息并返回
     * Date 下午5:50 18-12-31
     * Param [start, limit, nowPage, Number, request]
     * return java.lang.Object
     **/
    @ResponseBody
    @RequestMapping(value = "/getTypeInfomation")
    public Object getTypeInfomation(Integer start, Integer limit, Integer nowPage, Integer Number, HttpServletRequest request){
        Page page = new Page(start,limit);
        page.setCurrentPage(nowPage);
        String id = request.getParameter("id");
        try{
            page.setTotal(noteService.getNoteTypeCount(id));
            List<Note> noteList = noteService.getNoteTypeByCount(id,page);
            for(Note note:noteList){
                String str = note.getBjnr();
                if(str.length()>15){
                    str = str.substring(0,15);
                    note.setBjnr(str+"......");
                }
            }
            page.setRoot(noteList);
        }catch (Exception e){

        }
        return page;
    }
    /***
     * @author dinggc
     * Description //根据输入框的输入内容搜索相应的笔记并返回
     * Date 下午5:50 18-12-31
     * Param [start, limit, nowPage, Number, request]
     * return java.lang.Object
     **/
    @ResponseBody
    @RequestMapping(value = "/getSearch")
    public Object getSearch(Integer start, Integer limit, Integer nowPage, Integer Number, HttpServletRequest request){
        String search = request.getParameter("search");
        Page page = new Page(start,limit);
        page.setCurrentPage(nowPage);
        page.setTotal(noteService.getNoteLikeBjmcCount(search));
        List<Note> noteList = noteService.getNoteLikeName(search,page);
        for(Note note:noteList){
            String str = note.getBjnr();
            if(str.length()>15){
                str = str.substring(0,15);
                note.setBjnr(str+"......");
            }
        }
        page.setRoot(noteList);
        return page;
    }
    /***
     * @author dinggc
     * Description //根据笔记id获取笔记和该笔记的评论内容并跳转到具体的显示页面
     * Date 下午5:51 18-12-31
     * Param [model, request]
     * return java.lang.String
     **/
    @RequestMapping(value = "/noteEntrance")
    public String noteEntrance(Model model, HttpServletRequest request){
        String id = request.getParameter("id");
        int noteid = Integer.parseInt(id);
        int minid = noteService.getMinId();
        int maxid = noteService.getMaxId();
        List<Comment> comments = new ArrayList<Comment>();
        try{
            comments = (List<Comment>) noteService.getCommentByQuestionidAndFloor(noteid,1);
            for(Comment comment:comments){
                String uuid = comment.getUuid();
                List<Comment> sonCommentList = noteService.getSonCommentList(noteid,uuid);
                for(Comment commentName : sonCommentList){
                    int commentNameId = commentName.getCommentid();
                    int authorNameId = commentName.getAuthorid();
                    Vuser commentvuser = noteService.getVuserByCommentId(commentNameId);
                    Vuser authorvuser = noteService.getVuserByCommentId(authorNameId);
                    commentName.setCommentName(commentvuser.getUsername());
                    commentName.setAuthorName(authorvuser.getUsername());
                }
                int superCommentNameId = comment.getCommentid();
                int superAuthorNameId = comment.getAuthorid();
                Vuser superCommentVuser = noteService.getVuserByCommentId(superCommentNameId);
                Vuser superAuthorVuser = noteService.getVuserByCommentId(superAuthorNameId);
                comment.setCommentName(superCommentVuser.getUsername());
                comment.setAuthorName(superAuthorVuser.getUsername());
                comment.setComments(sonCommentList);
            }
        }catch (Exception e){
        }
        model.addAttribute("minid",minid);
        model.addAttribute("maxid",maxid);
        Note note = noteService.getNoteById(noteid);
        model.addAttribute("Note",note);
        model.addAttribute("commentList",comments);
        return "noteview";
    }
    /***
     * @author dinggc
     * Description //笔记页面的上一篇与下一篇功能
     * Date 下午5:51 18-12-31
     * Param [request, model]
     * return java.lang.String
     **/
    @RequestMapping(value = "/notepageEntrance")
    public String notepageEntrance(HttpServletRequest request,Model model){
        String beforeid = request.getParameter("beforeid");
        String afterid = request.getParameter("afterid");
        int minid = noteService.getMinId();
        int maxid = noteService.getMaxId();
        Note note = null;
        int id = 0;
        if(beforeid!=null&&afterid==null){
            int currentid = Integer.parseInt(beforeid);
            for(int i=currentid;i>=minid;i--){
                note = noteService.getNoteById(i);
                if(note!=null){
                    id = i;
                    break;
                }
            }
        }else if(beforeid==null&&afterid!=null){
            int currentid = Integer.parseInt(afterid);
            for(int i=currentid;i<=maxid;i++){
                note = noteService.getNoteById(i);
                if(note!=null){
                    id = i;
                    break;
                }
            }
        }

        return "redirect:/noteEntrance?id="+id;
    }
    /***
     * @author dinggc
     * Description //提交评论
     * Date 下午5:51 18-12-31
     * Param [request, comment]
     * return java.lang.String
     **/
    @RequestMapping(value = "/submitcomment")
    @ResponseBody
    public String submitcomment(HttpServletRequest request, Comment comment) {
        try {
            String questionid = request.getParameter("questionid");
            String author = request.getParameter("authorid");
            String comments = request.getParameter("commentid");
            String commentcontext = request.getParameter("context");
            String uuid = request.getParameter("uuid");
            String floors = request.getParameter("floor");
            int question = Integer.parseInt(questionid);
            if (uuid == "") {
                uuid = baseService.getUUID();
            }
            int authorid = Integer.parseInt(author);
            int commenid = Integer.parseInt(comments);
            int floor = Integer.parseInt(floors);
            String date = baseService.getCurrentTime();
            comment.setQuestionid(question);
            comment.setAuthorid(authorid);
            comment.setCommentid(commenid);
            comment.setUuid(uuid);
            comment.setFloor(floor);
            comment.setContext(commentcontext);
            comment.setDate(date);
            comment.setDel_flag(0);
            noteService.insertComment(comment);
            Vuser vuser = userService.getVuserRecvById(authorid);
            Vuser commitvuser = userService.getVuserRecvById(commenid);
            Note note = noteService.getNoteById(question);
            String rabbitContext = commitvuser.getUsername() +"在" +note.getBjmc()+ "中回复了你！";
            baseService.createExchange("commit",vuser.getUsername());
            baseService.send("commit",vuser.getUsername(),rabbitContext);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
    }
    /***
     * @author dinggc
     * Description //根据笔记id跳转到该篇笔记的文件资源页面
     * Date 下午5:53 19-1-10
     * Param [request, model]
     * return java.lang.String
     **/
    @RequestMapping(value = "fileSourcesEntrance")
    public String fileSourcesEntrance(HttpServletRequest request,Model model){
        String id = request.getParameter("id");
        int noteId = Integer.parseInt(id);
        model.addAttribute("noteId",noteId);
        return "noteFile";
    }
    /***
     * @author dinggc
     * Description //获取所有的笔记资源
     * Date 下午5:41 19-1-10
     * Param [start, limit, nowPage, Number]
     * return java.lang.Object
     **/
    @ResponseBody
    @RequestMapping("/getFileSources")
    public Object getFileSources(Integer start, Integer limit,Integer nowPage, Integer Number,HttpServletRequest request){
        //代理对象
        String id = request.getParameter("id");
        int noteId = Integer.parseInt(id);
        Page page = new Page(start,limit);
        page.setCurrentPage(nowPage);
        try{
            page.setTotal(noteService.getFileSourceCount(noteId));
            List<FileNote> fileNotes = noteService.getFileNoteByCount(noteId, page);
            page.setRoot(fileNotes);
        }catch (Exception e){
            e.printStackTrace();
        }
        return page;
    }
}
