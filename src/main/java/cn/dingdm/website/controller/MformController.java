package cn.dingdm.website.controller;


import cn.dingdm.website.Util.JsonDateValueProcessor;
import cn.dingdm.website.config.FtpConfig;
import cn.dingdm.website.entities.*;
import cn.dingdm.website.service.ActivitiService;
import cn.dingdm.website.service.BaseService;
import cn.dingdm.website.service.MformService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
/***
 * @author dinggc
 * Description //后台管理系统Controller
 * Date 下午5:37 18-12-31
 * Param
 * return
 **/
@Controller
@RequestMapping(value = "/mform")
public class MformController {

    @Autowired
    MformService mformService;
    @Autowired
    BaseService baseService;
    @Autowired
    ActivitiService activitiService;
    @Autowired
    TaskService taskService;
    @Autowired
    RepositoryService repositoryService;
    @Autowired
    FtpConfig ftpConfig;
    /***
     * @author dinggc
     * Description //获取rabbitmq中存放的未读取的信息
     * Date 下午5:37 18-12-31
     * Param [msgrabbitmq]
     * return java.lang.Object
     **/
    @RequestMapping(value = "/getRabbitMessage")
    @ResponseBody
    public Object getRabbitMessage(MsgRabbitMq msgrabbitmq){
        Vuser currentVuser = baseService.getCurrentVuser();
        try{
            msgrabbitmq = baseService.recv(currentVuser.getUsername(),baseService.getUUID());
            return msgrabbitmq;
        }catch (NullPointerException e){
            e.printStackTrace();
            msgrabbitmq.setContext("none");
            return msgrabbitmq;
        }
    }
    /***
     * @author dinggc
     * Description //后台管理系统的主页面，即跳转到后台个人信息显示页面
     * Date 下午5:37 18-12-31
     * Param [model]
     * return java.lang.String
     **/
    @RequestMapping(value = "/mformentrance")
    @RequiresPermissions("back:view")
    public String mformentrance(Model model){
        try{
            User user = mformService.getUserById();
            List<KnowLedge> knowLedges = mformService.getKnowLedge();
            model.addAttribute("knowLedge",knowLedges);
            model.addAttribute("user",user);
        }catch (Exception e){

        }
        return "personinfo";
    }
    /***
     * @author dinggc
     * Description //获取所有的笔记类型信息并返回
     * Date 下午5:38 18-12-31
     * Param []
     * return java.lang.Object
     **/
    @RequestMapping(value = "/getAllNoteType")
    @ResponseBody
    public Object getAllNoteType(){
        JSONArray jsonArray = new JSONArray();
        try{
            List<NoteType> noteTypes = mformService.getNoteType();
            for(NoteType noteType : noteTypes){
                String lxid = noteType.getId();
                int count = mformService.getNoteTypeCount(lxid);
                noteType.setNumber(count);
            }
            jsonArray = JSONArray.fromObject(noteTypes);
        }catch (Exception e){

        }
        return jsonArray;
    }
    /***
     * @author dinggc
     * Description //跳转到笔记概览页面
     * Date 下午5:38 18-12-31
     * Param []
     * return java.lang.String
     **/
    @RequestMapping(value = "/noteOverview")
    public String noteOverview(){
        return "noteoverview";
    }
    /***
     * @author dinggc
     * Description //更新个人信息
     * Date 下午5:38 18-12-31
     * Param [user]
     * return java.lang.String
     **/
    @RequestMapping(value = "/updateInfo")
    public String updateInfo(User user){
        String date = baseService.getCurrentTime();
        user.setDate(date);
        mformService.updateInfo(user);
        return "redirect:/mform/mformentrance";
    }
    /***
     * @author dinggc
     * Description //更新一篇笔记
     * Date 下午5:38 18-12-31
     * Param [model]
     * return java.lang.String
     **/
    @RequestMapping(value = "/uploadnote")
    @RequiresPermissions("back:view")
    public String uploadnote(Model model){
        List<NoteType> noteTypes = mformService.getNoteType();
        for(NoteType noteType : noteTypes){
            String lxid = noteType.getId();
            int count = mformService.getNoteTypeCount(lxid);
            noteType.setNumber(count);
        }
        model.addAttribute("note",noteTypes);
        return "uploadnote";
    }
    /***
     * @author dinggc
     * Description //新添一篇笔记
     * Date 下午5:39 18-12-31
     * Param [session, request, note, noteType]
     * return java.lang.String
     **/
    @RequestMapping("/addnote")
    @ResponseBody
    public String insertBj(@Param("name") String []name,@Param("sourceArray") String []sourceArray,HttpSession session, HttpServletRequest request, Note note,NoteType noteType,
                           FileNote fileNote)throws Exception{
        try {
            String date = baseService.getCurrentTime();
            String lxid = noteType.getLxmc();
            String [] lx = lxid.split(",");
            String totallx = "";
            for(int i=0;i<lx.length;i++){
                String id = mformService.getNoteTypeIdByLxmc(lx[i]);
                if(i == lx.length-1){
                    totallx += id;
                }else {
                    totallx += id + ",";
                }
            }
            Integer noteid = mformService.insertNote(totallx, request.getParameter("bjmc"), request.getParameter("bjzz"), request.getParameter("bjnr"),
                    request.getParameter("description"), date, 0);
            if(sourceArray.length > 0){
                for(int i=0;i<sourceArray.length;i++){
                    String sourceName = sourceArray[i];
                    String[] split = sourceName.split("\\.");
                    fileNote.setNoteid(noteid);
                    fileNote.setName(split[0]);
                    fileNote.setType(split[1]);
                    fileNote.setDescription(name[i]);
                    fileNote.setDate(date);
                    fileNote.setDel_flag(0);
                    mformService.insertFileNote(fileNote);
                }
            }
            return "SUCCESS";
        } catch (NullPointerException e ) {
            e.printStackTrace();
            return "error";

        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

   /***
    * @author dinggc
    * Description //markdown编辑器的图片上传
    * Date 下午5:39 18-12-31
    * Param [file, request, response, session]
    * return void
    **/
    @RequestMapping("/markpic")
    public void markpic(@RequestParam(value = "editormd-image-file", required = true) MultipartFile file,
                        HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        JSONObject json = new JSONObject();
        JSONObject markjson = new JSONObject();
        PrintWriter wirte = null;
        String orPath = request.getSession().getServletContext().getRealPath("/opt/uploadImages/");
        //对文件进行保存并且生成名称
        try{
            //成功
            wirte = response.getWriter();
            JSONObject jsonObject = baseService.saveToFtp(file);

            String fileRandomName = jsonObject.getString("msg");

            String path = "http://"+"www.xuxiaonan.cn/uploadImages/"
                    + fileRandomName;
            markjson.put("success", 1);
            markjson.put("message", "hello");
            markjson.put("url", path);
        }catch (Exception e) {
            //失败
            json.put("code", 1);
            e.printStackTrace();
        }finally {
            wirte.print(markjson);
            wirte.flush();
            wirte.close();
        }
    }
    /***
     * @author dinggc
     * Description //添加笔记类型
     * Date 下午5:39 18-12-31
     * Param [request, noteType]
     * return java.lang.Object
     **/
    @ResponseBody
    @RequestMapping(value = "/addnoteType")
    public Object addnoteType(HttpServletRequest request,NoteType noteType){
        String name = request.getParameter("noteTypeName");
        String desc = request.getParameter("noteTypeDesc");
        String id = baseService.getUUID();
        String date = baseService.getCurrentTime();
        noteType.setId(id);
        noteType.setLxmc(name);
        noteType.setDescription(desc);
        noteType.setDate(date);
        noteType.setDel_flag(0);
        mformService.insertNoteType(noteType);
        return "success";
    }
    /***
     * @author dinggc
     * Description //获取所有笔记信息并返回
     * Date 下午5:40 18-12-31
     * Param [start, limit, nowPage, Number]
     * return java.lang.Object
     **/
    @ResponseBody
    @RequestMapping("/getnoteIformation")
    public Object getnoteIformation(Integer start, Integer limit,Integer nowPage, Integer Number){
        Page page = new Page(start,limit);
        page.setCurrentPage(nowPage);
        try{
            page.setTotal(mformService.getNoteCount());
            List<Note> noteList = mformService.getNoteByCount(page);
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
     * Description //根据具体的id删除某一篇笔记
     * Date 下午5:40 18-12-31
     * Param [request, NoteIdArray, note]
     * return java.lang.String
     **/
    @RequestMapping("/deleteNote")
    @ResponseBody
    public String deleteBj(HttpServletRequest request, @Param(value = "NoteIdArray") Integer [] NoteIdArray,Note note)throws Exception{
        for(int i=0;i<NoteIdArray.length;i++){
            int id = NoteIdArray[i];
            note.setId(id);
            mformService.deleteNote(note);
        }
        return "删除成功！";
    }

    /***
     * @author dinggc
     * Description //根据笔记id查看笔记的具体信息
     * Date 下午5:40 18-12-31
     * Param [model, request]
     * return java.lang.String
     **/
    @RequestMapping("/editNoteById")
    public String ckbjentrance(Model model,HttpServletRequest request)throws Exception{
        List<NoteType> noteTypes = mformService.getNoteType();
        String id = request.getParameter("id");
        int noteid = Integer.parseInt(id);
        Note note = mformService.getNoteById(noteid);
        List<FileNote> noteSource = mformService.getAllNoteSourceByNoteId(noteid);
        String lxid = note.getLxid();
        String []lx = lxid.split(",");
        String lxname = "";
        for(int i=0;i<lx.length;i++){
            NoteType noteType = mformService.getNoteTypeById(lx[i]);
            if(i == lx.length-1){
                lxname += noteType.getLxmc();
            }else {
                lxname += noteType.getLxmc() + ",";
            }
        }
        model.addAttribute("note", note);
        model.addAttribute("noteType", lxname);
        model.addAttribute("notes", noteTypes);
        model.addAttribute("noteSource",noteSource);
        return "editview";
    }
    /***
     * @author dinggc
     * Description //更新笔记
     * Date 下午5:41 18-12-31
     * Param [session, request, note, noteType]
     * return java.lang.String
     **/
    @RequestMapping("/updateNote")
    @ResponseBody
    public String updateBj(@Param("name") String []name,@Param("sourceArray") String []sourceArray,HttpServletRequest request,Note note,NoteType noteType,
                           FileNote fileNote)throws Exception{
        try {
            String udate = baseService.getCurrentTime();
            String noteid = request.getParameter("id");
            int changeId = Integer.parseInt(noteid);
            if(sourceArray.length > 0){
                for(int i=0;i<sourceArray.length;i++){
                    String sourceName = sourceArray[i];
                    String[] split = sourceName.split("\\.");
                    fileNote.setNoteid(changeId);
                    fileNote.setName(split[0]);
                    fileNote.setType(split[1]);
                    fileNote.setDescription(name[i]);
                    fileNote.setDate(udate);
                    fileNote.setDel_flag(0);
                    mformService.insertFileNote(fileNote);
                }
            }
            String lxid = noteType.getLxmc();
            String [] lx = lxid.split(",");
            String totallx = "";
            for(int i=0;i<lx.length;i++){
                String id = mformService.getNoteTypeIdByLxmc(lx[i]);
                if(i == lx.length-1){
                    totallx += id;
                }else {
                    totallx += id + ",";
                }
            }
            String noteTypeId = request.getParameter("lxid");
            int id = Integer.parseInt(noteid);
            note.setDate(udate);
            note.setLxid(totallx);
            mformService.updateNote(note);
            return "success";
        } catch (NullPointerException e ) {
            e.printStackTrace();
            return "/error";

        } catch (Exception e) {
            e.printStackTrace();
            return "/error";
        }
    }
    /***
     * @author dinggc
     * Description //个人信息页面新增知识
     * Date 下午5:41 18-12-31
     * Param [request, knowLedge]
     * return java.lang.Object
     **/
    @ResponseBody
    @RequestMapping(value = "/addKnowLedge")
    public Object addKnowLedge(HttpServletRequest request,KnowLedge knowLedge){
        String name = request.getParameter("knowName");
        String per = request.getParameter("knowPer");
        int percent = Integer.parseInt(per);
        String date = baseService.getCurrentTime();
        knowLedge.setZsid(1);
        knowLedge.setPercent(percent);
        knowLedge.setZsmc(name);
        knowLedge.setDate(date);
        knowLedge.setDel_flag(0);
        mformService.insertKnowLedge(knowLedge);
        return "success";
    }
    /***
     * @author dinggc
     * Description //跳转到添加生活文章页面
     * Date 下午5:41 18-12-31
     * Param []
     * return java.lang.String
     **/
    @RequestMapping(value = "/addlifeEntrance")
    public String lifeEntrance(){
        return "addLife";
    }
    /***
     * @author dinggc
     * Description //保存图片
     * Date 下午5:41 18-12-31
     * Param [file, request, session]
     * return java.lang.Object
     **/
    @RequestMapping(value = "/saveFile")
    @ResponseBody
    public Object saveFile(MultipartFile file,HttpServletRequest request,HttpSession session){
        String path = "/opt/file/";
        JSONObject jsonObject = new JSONObject();
        boolean returnBoolean = false;
        try{
            jsonObject = baseService.uploadFile(file, path, session, request);
            returnBoolean = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return jsonObject;
    }
    /***
     * @author dinggc
     * Description //封装所有的角色并跳转到管理用户界面
     * Date 下午5:42 18-12-31
     * Param [model]
     * return java.lang.String
     **/
    @RequestMapping(value = "/managerUser")
    public String managerUser(Model model){
        List<Role> roleList = mformService.getAllRole();
        model.addAttribute("role",roleList);
        return "manageruser";
    }
    /***
     * @author dinggc
     * Description //删除用户指定的角色
     * Date 下午5:42 18-12-31
     * Param [request, userid, roleid]
     * return java.lang.String
     **/
    @RequestMapping(value = "/removeUserRole")
    @ResponseBody
    public String removeUserRole(HttpServletRequest request,@Param("userid") Integer []userid,@Param("roleid") Integer []roleid){
        for(int i=0;i<userid.length;i++){
            int uid = userid[i];
            int rid = roleid[i];
            mformService.deleteRoleByUserid(uid,rid);
        }
        return "角色删除成功！";
    }
    /***
     * @author dinggc
     * Description //删除用户
     * Date 下午5:42 18-12-31
     * Param [request, userid]
     * return java.lang.String
     **/
    @RequestMapping(value = "/deleteUser")
    @ResponseBody
    public String deleteUser(HttpServletRequest request,@Param("userid") Integer []userid){
        for(int i=0;i<userid.length;i++){
            int id = userid[i];
            mformService.deleteUser(id);
            mformService.deleteRoleByUserId(id);
        }
        return "删除成功！";
    }
    /***
     * @author dinggc
     * Description //为用户添加角色
     * Date 下午5:43 18-12-31
     * Param [userArray, request, currentRoleId]
     * return java.lang.String
     **/
    @RequestMapping(value = "/addUserRoles")
    @ResponseBody
    public String addUserRoles(@RequestParam(value = "userArray") Integer[] userArray,HttpServletRequest request,
                               @Param("currentRoleId") Integer []currentRoleId){
        int roleid = currentRoleId[0];
        for(int i=0;i<userArray.length;i++){
            RoleUser roleUser = new RoleUser();
            roleUser.setRoleid(roleid);
            roleUser.setUserid(userArray[i]);
            mformService.insertRoleByUserId(roleUser);
        }
        return "用户角色添加成功！";
    }
    /***
     * @author dinggc
     * Description //跳转到角色和权限的管理界面
     * Date 下午5:43 18-12-31
     * Param []
     * return java.lang.String
     **/
    @RequestMapping(value = "/addRoleAndPermission")
    public String addRoleAndPermission(){
        return "addRoleAndPermission";
    }
    /***
     * @author dinggc
     * Description //获取所有的权限信息并返回
     * Date 下午5:43 18-12-31
     * Param []
     * return java.lang.Object
     **/
    @RequestMapping(value = "/getAllPermissions")
    @ResponseBody
    public Object getAllPermissions(){
        List<Permission> permissionList = mformService.getAllPermission();
        JSONArray jsonArray = JSONArray.fromObject(permissionList);
        return jsonArray;
    }
    /***
     * @author dinggc
     * Description //获取所有的角色信息并返回
     * Date 下午5:43 18-12-31
     * Param []
     * return java.lang.Object
     **/
    @RequestMapping(value = "/getAllRoles")
    @ResponseBody
    public Object getAllRoles(){
        List<Role> roleList = mformService.getAllRole();
        JSONArray jsonArray = JSONArray.fromObject(roleList);
        return jsonArray;
    }
    /***
     * @author dinggc
     * Description //删除角色
     * Date 下午5:44 18-12-31
     * Param [request, roleid]
     * return java.lang.String
     **/
    @RequestMapping(value = "/deleteRole")
    @ResponseBody
    public String deleteRole(HttpServletRequest request,@Param("roleid") Integer []roleid){
        for(int i=0;i<roleid.length;i++){
            int id = roleid[i];
            mformService.deleteRoleUserByRoleId(id);
        }
        return "删除角色成功！";
    }
    /***
     * @author dinggc
     * Description //添加角色
     * Date 下午5:44 18-12-31
     * Param [role]
     * return java.lang.String
     **/
    @RequestMapping(value = "/addRole")
    public String addRole(Role role){
        String currentTime = baseService.getCurrentTime();
        role.setDel_flag(0);
        role.setDate(currentTime);
        mformService.insertRole(role);
        return "redirect:/mform/addRoleAndPermission";
    }
    /***
     * @author dinggc
     * Description //删除权限
     * Date 下午5:44 18-12-31
     * Param [request, permissionid]
     * return java.lang.String
     **/
    @RequestMapping(value = "/deletePermission")
    @ResponseBody
    public String deletePermission(HttpServletRequest request,@Param("permissionid") Integer []permissionid){
        for(int i=0;i<permissionid.length;i++){
           int id = permissionid[i];
           mformService.deleteRolePermissionByPermissionId(id);
        }
        return "删除权限成功！";
    }
    /***
     * @author dinggc
     * Description //添加权限
     * Date 下午5:44 18-12-31
     * Param [permission]
     * return java.lang.String
     **/
    @RequestMapping(value = "/addPermission")
    public String addPermission(Permission permission){
        String currentTime = baseService.getCurrentTime();
        permission.setDel_flag(0);
        permission.setDate(currentTime);
        mformService.insertPermission(permission);
        return "redirect:/mform/addRoleAndPermission";
    }
    /***
     * @author dinggc
     * Description //跳转到管理权限页面
     * Date 下午5:44 18-12-31
     * Param [model]
     * return java.lang.String
     **/
    @RequestMapping(value = "/managerPermission")
    public String managerPermission(Model model){
        List<Permission> permissionList = mformService.getAllPermission();
        model.addAttribute("permission",permissionList);
        return "managerPermission";
    }
    /***
     * @author dinggc
     * Description //根据角色id获取角色拥有的权限
     * Date 下午5:44 18-12-31
     * Param [request]
     * return java.lang.Object
     **/
    @RequestMapping(value = "/getAllPermissionsByRoleId")
    @ResponseBody
    public Object getAllPermissionsByRoleId(HttpServletRequest request){
        String id = request.getParameter("id");
        int roleid = Integer.parseInt(id);
        Role role = mformService.getRoleById(roleid);
        List<PermissionRole> permissionList = mformService.getAllPermissionRoleByRoleId(roleid);
        for(PermissionRole permission : permissionList){
            permission.setRole(role);
        }
        JSONArray jsonArray = JSONArray.fromObject(permissionList);
        return jsonArray;
    }
    /***
     * @author dinggc
     * Description //获取所有的角色和权限信息
     * Date 下午5:45 18-12-31
     * Param []
     * return java.lang.Object
     **/
    @RequestMapping(value = "/getAllRolesAndPermissions")
    @ResponseBody
    public Object getAllRolesAndPermissions(){
        List<Role> roleList = mformService.getAllRole();
        JSONArray jsonArray = JSONArray.fromObject(roleList);
        return jsonArray;
    }
    /***
     * @author dinggc
     * Description //删除角色具有的权限
     * Date 下午5:45 18-12-31
     * Param [request, permissionid]
     * return java.lang.String
     **/
    @RequestMapping(value = "/removeRolePermission")
    @ResponseBody
    public String removeRolePermission(HttpServletRequest request,@Param("permissionid") Integer []permissionid){
        String role = request.getParameter("roleid");
        int roleid = Integer.parseInt(role);
        for(Integer in : permissionid){
            int id = in;
            mformService.deletePermissionByPermissionid(id,roleid);
        }
        return "权限删除成功！";
    }
    /***
     * @author dinggc
     * Description //跳转到流程查看页面
     * Date 下午5:45 18-12-31
     * Param [model, workFlow]
     * return java.lang.String
     **/
    @RequestMapping(value = "/permissionApplication")
    public String permissionApplication(Model model,WorkFlow workFlow){
        List<ActDeployment> deployments = baseService.getAllDeployMent(workFlow);
        model.addAttribute("proc",deployments);
        return "permissionApplication";
    }
    /***
     * @author dinggc
     * Description //为角色添加权限
     * Date 下午5:46 18-12-31
     * Param [permissionArray, request]
     * return java.lang.String
     **/
    @RequestMapping(value = "/addRolePermissions")
    @ResponseBody
    public String addRolePermissions(@RequestParam(value = "permissionArray") Integer[] permissionArray,HttpServletRequest request){
        String id = request.getParameter("roleid");
        int roleid = Integer.parseInt(id);
        for(int i=0;i<permissionArray.length;i++){
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleid(roleid);
            rolePermission.setPerid(permissionArray[i]);
            mformService.insertRoleByPermissionId(rolePermission);
        }
        return "角色权限添加成功！";
    }
    /***
     * @author dinggc
     * Description //得到所有的流程定义信息并返回
     * Date 下午5:46 18-12-31
     * Param [request]
     * return java.lang.Object
     **/
    @ResponseBody
    @RequestMapping(value = "/getAllProceDef")
    public Object getAllProceDef(HttpServletRequest request){
        int userid = baseService.getCurrentVuser().getId();
        List<BackAppl> list = mformService.getProcByUserId(userid);
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
        JSONArray jsonArray = JSONArray.fromObject(list,jsonConfig);
        return jsonArray;
    }
    /***
     * @author dinggc
     * Description //部署流程
     * Date 下午5:46 18-12-31
     * Param [backAppl, request]
     * return java.lang.String
     **/
    @RequestMapping(value = "/addProc")
    public String addProc(BackAppl backAppl,HttpServletRequest request){
        String currentTime = baseService.getCurrentTime();
        backAppl.setCreatetime(currentTime);
        backAppl.setState(0);
        backAppl.setDate(currentTime);
        backAppl.setDel_flag(0);
        mformService.insertBackAppl(backAppl);
        return "redirect:/mform/permissionApplication";
    }
    /***
     * @author dinggc
     * Description //删除流程
     * Date 下午5:46 18-12-31
     * Param [request, backAppl, id]
     * return java.lang.String
     **/
    @RequestMapping(value = "/deleteProc")
    @ResponseBody
    public String deleteProc(HttpServletRequest request,BackAppl backAppl,@Param("id") Integer []id){
        for(int i=0;i<id.length;i++){
            int procid = id[i];
            String currentTime = baseService.getCurrentTime();
            backAppl.setDate(currentTime);
            backAppl.setId(procid);
            mformService.updateBackAppl(backAppl);
        }
        return "删除成功！";
    }
    /***
     * @author dinggc
     * Description //任务办理人获取自己的代办信息
     * Date 下午5:47 18-12-31
     * Param [request]
     * return java.lang.Object
     **/
    @ResponseBody
    @RequestMapping(value = "/getAllRequest")
    public Object getAllRequest(HttpServletRequest request){
        Vuser currentVuser = baseService.getCurrentVuser();
        String username = currentVuser.getUsername();
        List<ActTask> list = mformService.findCurrentUserTask(username);
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
        JSONArray jsonArray = JSONArray.fromObject(list,jsonConfig);
        return jsonArray;
    }
    /***
     * @author dinggc
     * Description //跳转到处理流程请求页面
     * Date 下午5:47 18-12-31
     * Param []
     * return java.lang.String
     **/
    @RequestMapping(value = "/approvalRequest")
    public String approvalRequest(){
        return "approvalRequest";
    }
    /***
     * @author dinggc
     * Description //申请流程
     * Date 下午5:47 18-12-31
     * Param [request, deploymentId, processId]
     * return java.lang.String
     **/
    @RequestMapping(value = "/submitRequest")
    @ResponseBody
    public String submitRequest(HttpServletRequest request,@Param("deploymentId") String []deploymentId,
                                @Param("processId") Integer []processId){
        String outcome = "默认提交";
        Vuser currentVuser = baseService.getCurrentVuser();
        String username = currentVuser.getUsername();
        for(int i=0;i<deploymentId.length;i++){
            String deployment = deploymentId[i];
            int processid = processId[i];
            activitiService.startProcess(deployment, outcome, username, processid);
            ActTask currentUserTaskToSubmit = mformService.findCurrentUserTaskToSubmit(username);
            activitiService.complete(currentUserTaskToSubmit.getId());
            BackAppl backAppl = new BackAppl();
            backAppl.setId(processid);
            backAppl.setState(1);
            mformService.updateBackApplState(backAppl);
        }
        return "申请成功!";
    }
    /***
     * @author dinggc
     * Description //同意申请的流程
     * Date 下午5:47 18-12-31
     * Param [request, taskId, backApplId]
     * return java.lang.String
     **/
    @RequestMapping(value = "/allowRequest")
    @ResponseBody
    public String allowRequest(HttpServletRequest request,@Param("taskId") String []taskId,
                               @Param("backApplId") Integer []backApplId){
        String backinfo = "";
        String outcome = request.getParameter("outcome");
        for(int i=0;i<taskId.length;i++){
            String taskid = taskId[i];
            String processInstanceId = activitiService.dowmRequestProcessInstanceId(taskid);
            activitiService.completeTaskWithVariables(taskid,outcome);
            boolean processComplete = activitiService.isProcessComplete(processInstanceId);
            if(processComplete == true){
                BackAppl backAppl = new BackAppl();
                backAppl.setState(2);
                backAppl.setId(backApplId[i]);
                mformService.updateBackApplStateToSuccess(backAppl);
            }else{
            }
            if("同意".equals(outcome)){
                backinfo = "已同意！";
            }else if ("驳回".equals(outcome)){
                BackAppl backAppl = new BackAppl();
                backAppl.setId(backApplId[i]);
                backAppl.setState(3);
                mformService.updateBackApplStateToFail(backAppl);
                mformService.deleteAllProcess(processInstanceId);
                backinfo = "已驳回！";
            }
        }
        return backinfo;
    }
    /***
     * @author dinggc
     * Description //获取所有的笔记
     * Date 下午5:48 18-12-31
     * Param []
     * return java.lang.Object
     **/
    @RequestMapping(value = "/getAllNote")
    @ResponseBody
    public Object getAllNote(){
        List<Note> allNote = mformService.getAllNote();
        JSONArray jsonArray = JSONArray.fromObject(allNote);
        return jsonArray;
    }
    /***
     * @author dinggc
     * Description //获取所有的用户并返回
     * Date 下午5:48 18-12-31
     * Param []
     * return java.lang.Object
     **/
    @RequestMapping(value = "/getAllUserToManagerRole")
    @ResponseBody
    public Object getAllUserToManagerRole(){
        List<Vuser> allUser = mformService.getAllUser();
        for(Vuser vuser : allUser){
            int id = vuser.getId();
            Role userRoleByUserId = mformService.getUserRoleByUserId(id);
            if(userRoleByUserId == null){
             Role role = new Role();
                role.setDescribe("暂无权限");
             vuser.setRole(role);
            }else{
                vuser.setRole(userRoleByUserId);
            }
        }
        JSONArray jsonArray = JSONArray.fromObject(allUser);
        return jsonArray;
    }
    /***
     * @author dinggc
     * Description //更新流程进行中的注释
     * Date 下午5:48 18-12-31
     * Param [backApplId, request]
     * return java.lang.Object
     **/
    @RequestMapping(value = "/updateZhushi")
    @ResponseBody
    public Object updateZhushi(@Param("backApplId") Integer []backApplId,HttpServletRequest request){
        String zhushi = request.getParameter("agree");
        for(int i=0;i<backApplId.length;i++){
            BackAppl backAppl = new BackAppl();
            backAppl.setId(backApplId[i]);
            backAppl.setZhushi(zhushi);
            mformService.updateBackZhushi(backAppl);
        }
        return "信息更新成功！";
    }
    /***
     * @author dinggc
     * Description //跳转到生活增加页面
     * Date 上午9:03 19-1-2
     * Param []
     * return java.lang.String
     **/
    @RequestMapping(value = "jumpToLife")
    public String jumpToLife(){

        return "addLife";
    }
    /***
     * @author dinggc
     * Description //跳转到游戏增加页面
     * Date 上午9:03 19-1-2
     * Param []
     * return java.lang.String
     **/
    @RequestMapping(value = "jumpToGame")
    public String jumpToGame(){

        return "addGame";
    }
    /***
     * @author dinggc
     * Description //富文本编辑器上传图片
     * Date 上午10:18 19-1-2
     * Param [file, request, response, session]
     * return void
     **/
    @RequestMapping("/uploadImage")
    @ResponseBody
    public JSONObject uploadImage(HttpServletRequest request, HttpServletResponse response) {
        JSONObject markjson = new JSONObject();
        //对文件进行保存并且生成名称
        try{
            //成功
            List<MultipartFile> multipartFileList = baseService.getMultipartFileList(request);
            //这里应该封装多个json返回，因为可能上传多张图片，我先在前端写图片限制为1张
            for (MultipartFile multipartFile : multipartFileList){
                JSONObject jsonObject = baseService.saveToFtp(multipartFile);
                String fileRandomName = jsonObject.getString("msg");
                String path = "http://"+"www.xuxiaonan.cn/uploadImages/"
                        + fileRandomName;
                markjson.put("success", 1);
                markjson.put("message", "hello");
                markjson.put("url", path);
            }
        }catch (Exception e) {
            //失败
            markjson.put("code", 1);
            e.printStackTrace();
        }
        return markjson;
    }
    /***
     * @author dinggc
     * Description //bootstrap的图片上传
     * Date 上午11:12 19-1-2
     * Param [file, request, session]
     * return java.lang.Object
     **/
    @RequestMapping(value = "savePic")
    @ResponseBody
    public Object savePic(MultipartFile file, HttpServletRequest request, HttpSession session){
        String msg = "";
        JSONObject jsonObject = new JSONObject();
        try{
            JSONObject object = baseService.saveToFtp(file);
            jsonObject.put("url",object.getString("msg"));
            jsonObject.put("name",object.getString("name"));
        }catch (Exception e){
            e.printStackTrace();
        }
        return jsonObject;
    }
    /***
     * @author dinggc
     * Description //增加一篇生活的文章
     * Date 下午8:32 19-1-2
     * Param [request]
     * return java.lang.Object
     **/
    @RequestMapping(value = "addNewLife")
    @ResponseBody
    public Object addNewLife(HttpServletRequest request){
        String picid = request.getParameter("picid");
        String context = request.getParameter("context");
        String title = request.getParameter("title");
        String currentTime = baseService.getCurrentTime();
        Vuser currentVuser = baseService.getCurrentVuser();
        Life life = new Life();
        life.setMc(title);
        life.setAuth(currentVuser.getUsername());
        life.setJs(context);
        life.setDate(currentTime);
        life.setDel_flag(0);
        if(picid != null){
            String[] split = picid.split("\\.");
            Picture picture = new Picture();
            //这里name和address都写死，但是应该进行前台的获取，由于存在多个文件图片的上传，因此待重构后修正
            picture.setPname("lifePicture");
            picture.setAddress("/home/dinggc/uploadImages/");
            picture.setPid(split[0]);
            picture.setType(split[1]);
            picture.setDate(currentTime);
            picture.setDel_flag(0);
            life.setPicid(picid);
            mformService.insertPicture(picture);
        }
        mformService.insertLife(life);
        return "success";
    }
    /***
     * @author dinggc
     * Description //增加一篇游戏的文章
     * Date 下午8:32 19-1-2
     * Param [request]
     * return java.lang.Object
     **/
    @RequestMapping(value = "addNewGame")
    @ResponseBody
    public Object addNewGame(HttpServletRequest request){
        String picid = request.getParameter("picid");
        String context = request.getParameter("context");
        String title = request.getParameter("title");
        String yxkfs = request.getParameter("yxkfs");
        String currentTime = baseService.getCurrentTime();
        Game game = new Game();
        game.setYxmc(title);
        game.setYxkfs(yxkfs);
        game.setGrgs(context);
        game.setDate(currentTime);
        game.setDel_flag(0);
        if(picid != null){
            String[] split = picid.split("\\.");
            Picture picture = new Picture();
            //这里name和address都写死，但是应该进行前台的获取，由于存在多个文件图片的上传，因此待重构后修正
            picture.setPname("gamePicture");
            picture.setAddress("/home/dinggc/uploadImages/");
            picture.setPid(split[0]);
            picture.setType(split[1]);
            picture.setDate(currentTime);
            picture.setDel_flag(0);
            game.setPicid(picid);
            mformService.insertPicture(picture);
        }
        mformService.insertGame(game);
        return "success";
    }
    /***
     * @author dinggc
     * Description //跳转到文章管理页面
     * Date 上午9:08 19-1-3
     * Param []
     * return java.lang.String
     **/
    @RequestMapping(value = "jumpToManager")
    public String jumpToManager(){
        return "LifeManager";
    }
    /***
     * @author dinggc
     * Description //获取所有的生活文章信息并返回
     * Date 下午6:50 19-1-3
     * Param []
     * return net.sf.json.JSONArray
     **/
    @RequestMapping(value = "getAllLife")
    @ResponseBody
    public JSONArray getAllLife(){
        JSONArray jsonArray = new JSONArray();
        List<Life> allLife = mformService.getAllLife();
        jsonArray = JSONArray.fromObject(allLife);
        return jsonArray;
    }
    /***
     * @author dinggc
     * Description //获取所有的游戏文章信息并返回
     * Date 下午6:50 19-1-3
     * Param []
     * return net.sf.json.JSONArray
     **/
    @RequestMapping(value = "getAllGame")
    @ResponseBody
    public JSONArray getAllGame(){
        JSONArray jsonArray = new JSONArray();
        List<Game> allGame = mformService.getAllGame();
        jsonArray = JSONArray.fromObject(allGame);
        return jsonArray;
    }
    /***
     * @author dinggc
     * Description //根据id获取到生活文章信息并返回到编辑页面
     * Date 下午6:58 19-1-3
     * Param [request, model]
     * return java.lang.String
     **/
    @RequestMapping(value = "editLife")
    public String editLife(HttpServletRequest request,Model model){
        String id = request.getParameter("id");
        Life life = mformService.getLifeById(Integer.parseInt(id));
        model.addAttribute("life",life);
        return "editLife";
    }
    /***
     * @author dinggc
     * Description //根据id获取到游戏文章信息并返回到编辑页面
     * Date 下午6:58 19-1-3
     * Param [request, model]
     * return java.lang.String
     **/
    @RequestMapping(value = "editGame")
    public String editGame(HttpServletRequest request,Model model){
        String id = request.getParameter("id");
        Game game = mformService.getGameById(Integer.parseInt(id));
        model.addAttribute("game",game);
        return "editGame";
    }
    /***
     * @author dinggc
     * Description //更新生活信息的文章和封面
     * Date 下午7:39 19-1-3
     * Param [request, life, picture]
     * return java.lang.Object
     **/
    @RequestMapping(value = "updateLife")
    @ResponseBody
    public Object updateLife(HttpServletRequest request,Life life,Picture picture){
        String id = request.getParameter("id");
        String update = request.getParameter("update");
        String title = request.getParameter("title");
        String context = request.getParameter("context");
        String picid = request.getParameter("picid");
        String currentTime = baseService.getCurrentTime();
        Vuser currentVuser = baseService.getCurrentVuser();
        int picUpdate = Integer.parseInt(update);
        life.setId(Integer.parseInt(id));
        life.setMc(title);
        life.setAuth(currentVuser.getUsername());
        life.setJs(context);
        life.setPicid(picid);
        life.setDate(currentTime);
        if (picUpdate == 1){
            String[] split = picid.split("\\.");
            picture.setPid(split[0]);
            picture.setPname(split[0]);
            picture.setType(split[1]);
            picture.setDate(currentTime);
            mformService.updatePicture(picture);
        }
        mformService.updateLife(life);
        return "SUCCESS";
    }
    /***
     * @author dinggc
     * Description //更新游戏信息的文章和封面
     * Date 下午7:39 19-1-3
     * Param [request, game, picture]
     * return java.lang.Object
     **/
    @RequestMapping(value = "updateGame")
    @ResponseBody
    public Object updateGame(HttpServletRequest request,Game game,Picture picture){
        String id = request.getParameter("id");
        String update = request.getParameter("update");
        String yxmc = request.getParameter("title");
        String yxkfs = request.getParameter("yxkfs");
        String context = request.getParameter("context");
        String picid = request.getParameter("picid");
        String currentTime = baseService.getCurrentTime();
        int picUpdate = Integer.parseInt(update);
        game.setId(Integer.parseInt(id));
        game.setYxmc(yxmc);
        game.setYxkfs(yxkfs);
        game.setGrgs(context);
        game.setPicid(picid);
        game.setDate(currentTime);
        if (picUpdate == 1){
            String[] split = picid.split("\\.");
            picture.setPid(split[0]);
            picture.setPname(split[0]);
            picture.setType(split[1]);
            picture.setDate(currentTime);
            mformService.updatePicture(picture);
        }
        mformService.updateGame(game);
        return "SUCCESS";
    }
    /***
     * @author dinggc
     * Description //根据前台传来的id数组删除文章
     * Date 下午7:59 19-1-3
     * Param [lifeid, life]
     * return java.lang.Object
     **/
    @RequestMapping(value = "deleteLife")
    @ResponseBody
    public Object deleteLife(@Param("lifeid") Integer []lifeid,Life life){
        for (int i=0;i<lifeid.length;i++){
            life.setId(lifeid[i]);
            life.setDel_flag(1);
            mformService.deleteLife(life);
        }
        return "SUCCESS";
    }
    /***
     * @author dinggc
     * Description //根据前台传来的id数组删除文章
     * Date 下午7:59 19-1-3
     * Param [gameId, game]
     * return java.lang.Object
     **/
    @RequestMapping(value = "deleteGame")
    @ResponseBody
    public Object deleteGame(@Param("gameId") Integer []gameId,Game game){
        for (int i=0;i<gameId.length;i++){
            game.setId(gameId[i]);
            game.setDel_flag(1);
            mformService.deleteGame(game);
        }
        return "SUCCESS";
    }
    /***
     * @author dinggc
     * Description //根据id删除资源
     * Date 下午7:11 19-1-10
     * Param [request, fileNote]
     * return java.lang.String
     **/
    @RequestMapping(value = "deleteSource")
    public String deleteSource(HttpServletRequest request,FileNote fileNote){
        String sourceId = request.getParameter("id");
        int sId = Integer.parseInt(sourceId);
        String noteId = request.getParameter("noteId");
        int id = Integer.parseInt(noteId);
        fileNote.setId(sId);
        mformService.deleteSourceById(fileNote);
        return "redirect:/mform/editNoteById?id=" + id;
    }
}

