package cn.dingdm.website.controller;

import cn.dingdm.website.entities.Note;
import cn.dingdm.website.service.NoteService;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/***
 * @author dinggc
 * Description //Bootstrap-table控制层
 * Date 下午5:07 18-12-31
 * Param
 * return
 **/
@Controller
public class BootstrapController {

    @Autowired
    NoteService noteService;

    /***
     * @author dinggc
     * Description //跳转到Bootstrap-table的页面
     * Date 下午5:07 18-12-31
     * Param []
     * return java.lang.String
     **/
    @RequestMapping(value = "/testEntrance")
    public String testEntrance(){
        return "BootstrapTable";
    }

    /***
     * @author dinggc
     * Description //获取所有笔记用于bootstrap-table的表格显示
     * Date 下午5:07 18-12-31
     * Param []
     * return java.lang.Object
     **/
    @RequestMapping(value = "/getAllNoteToBootstrap")
    @ResponseBody
    public Object getAllNoteToBootstrap(){
        List<Note> allNoteToBootstraoTable = noteService.getAllNoteToBootstraoTable();
        JSONArray jsonArray = JSONArray.fromObject(allNoteToBootstraoTable);
        return jsonArray;
    }
}
