package cn.dingdm.website.service;

import cn.dingdm.website.entities.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MformService {
    //获取个人信息
    public User getUserById();
    //更新个人信息
    public void updateInfo(User user);
    //得到后台笔记类型
    public List<NoteType> getNoteType();
    //得到类型id
    public String getNoteTypeIdByLxmc(String lxmc);
    public Integer insertNote(@Param("lxid") String lxid,@Param("bjmc") String bjmc,@Param("bjzz") String bjzz,
                              @Param("bjnr") String bjnr,@Param("description") String description,@Param("date") String date,
                              @Param("del_flag") int del_flag);

    public int getNoteTypeCount(@Param("lxid") String lxid);
    //插入笔记类型
    public void insertNoteType(NoteType noteType);

    public int getNoteCount();
    public List<Note> getNoteByCount(Page page);
    //根据id得到笔记
    public Note getNoteById(int id);
    //根据id删除笔记
    public void deleteNote(Note note);
    //根据笔记类型id得到笔记类型
    public NoteType getNoteTypeById(String lxid);
    //更新笔记
    public void updateNote(Note note);
    //得到知识
    public List<KnowLedge> getKnowLedge();
    //保存未读信息
    public void insertMsgRabbitMq(MsgRabbitMq msgrabbitmq);
    //得到用户的数量
    public int getVuserCount();
    //得到所有的用户
    public List<Vuser> getAllVuserByRoleId(int id);
    //得到所有的角色
    public List<Role> getAllRole();
    //得到其他角色
    public List<Vuser> getAllOtherVuserWithoutRole();
    //根据用户id和角色id删除对应用户的角色
    public void deleteRoleByUserid(@Param("userid") int userid,@Param("roleid") int roleid);
    //删除用户
    public void deleteUser(int id);
    //删除用户对应的角色
    public void deleteRoleByUserId(int id);
    //赋予角色用户对应的角色
    public void insertRoleByUserId(RoleUser roleUser);
    //得到所有的权限
    public List<Permission> getAllPermission();
    //删除对应角色
    public void deleteRoleUserByRoleId(int id);
    //插入角色
    public void insertRole(Role role);
    //删除对应的权限
    public void deleteRolePermissionByPermissionId(int id);
    //插入权限
    public void insertPermission(Permission permission);
    //得到所有的用户
    public List<Permission> getAllPermissionByRoleId(int id);
    //删除角色的权限
    public void deletePermissionByPermissionid(@Param("permissionid") int permissionid,@Param("roleid") int roleid);
    //添加角色对应权限
    public void insertRoleByPermissionId(RolePermission rolePermission);
    //得到所有用户申请单的数量
    public int getProcCountByUserid(int userid);
    //得到所有的申请单
    public List<BackAppl> getProcByUserId(@Param("userid") int userid);
    //插入申请
    public void insertBackAppl(BackAppl backAppl);
    //删除请求
    public void updateBackAppl(BackAppl backAppl);
    //获取管理员用户
    public List<Vuser> getAdminByAdminId(int id);
    //得到所有当前的请求
    public List<ActTask> findCurrentUserTask(String username);
    //得到所有请求的数目
    public int findTaskCount(String username);
    //得到现在申请人的请求
    public ActTask findCurrentUserTaskToSubmit(String username);
    //获取申请的内容，设置流程变量
    public BackAppl getBackAppl(int id);
    //更新申请单的状态
    public void updateBackApplState(BackAppl backAppl);
    //请求申请完成
    public void updateBackApplStateToSuccess(BackAppl backAppl);
    //请求被驳回，请求失败
    public void updateBackApplStateToFail(BackAppl backAppl);
    //请求被驳回，删除当前的流程
    public void deleteAllProcess(String processDefinitionId);
    //得到所有的笔记
    public List<Note> getAllNote();
    //得到所有的用户
    public List<Vuser> getAllUser();
    //根据用户的id得到他的角色
    public Role getUserRoleByUserId(int userid);
    //根据id获取角色
    public Role getRoleById(int id);
    //得到带有role的permission
    public List<PermissionRole> getAllPermissionRoleByRoleId(int id);
    //更新申请单的注释信息
    public void updateBackZhushi(BackAppl backAppl);
    //插入知识的类型
    public void insertKnowLedge(KnowLedge knowLedge);
    /***
     * @author dinggc
     * Description //生活封面图片的插入
     * Date 下午7:42 19-1-2
     * Param [picture]
     * return void
     **/
    public void insertPicture(Picture picture);
    /***
     * @author dinggc
     * Description //插入生活文章
     * Date 下午7:44 19-1-2
     * Param [life]
     * return void
     **/
    public void insertLife(Life life);
    /***
     * @author dinggc
     * Description //获取所有的生活文章
     * Date 上午9:17 19-1-3
     * Param []
     * return java.util.List<cn.dingdm.website.entities.Life>
     **/
    public List<Life> getAllLife();
    /***
     * @author dinggc
     * Description //根据id获取生活的文章
     * Date 下午6:56 19-1-3
     * Param [id]
     * return cn.dingdm.website.entities.Life
     **/
    public Life getLifeById(int id);
    /***
     * @author dinggc
     * Description //更新一篇生活文章
     * Date 下午7:28 19-1-3
     * Param [life]
     * return void
     **/
    public void updateLife(Life life);
    /***
     * @author dinggc
     * Description //更新文章的封面
     * Date 下午7:36 19-1-3
     * Param [picture]
     * return void
     **/
    public void updatePicture(Picture picture);
    /***
     * @author dinggc
     * Description //根据id删除文章
     * Date 下午7:56 19-1-3
     * Param [life]
     * return void
     **/
    public void deleteLife(Life life);
    /***
     * @author dinggc
     * Description //插入一篇新的游戏文章
     * Date 下午4:13 19-1-4
     * Param [game]
     * return void
     **/
    public void insertGame(Game game);
    /***
     * @author dinggc
     * Description //获取所有的游戏文章并返回
     * Date 下午4:57 19-1-4
     * Param []
     * return java.util.List<cn.dingdm.website.entities.Game>
     **/
    public List<Game> getAllGame();
    /***
     * @author dinggc
     * Description //根据id获取游戏文章
     * Date 下午7:13 19-1-4
     * @Param [id]
     * @return cn.dingdm.website.entities.Game
     **/
    public Game getGameById(int id);
    /***
     * @author dinggc
     * description //根据id删除游戏文章
     * Date 下午7:26 19-1-4
     * @param [game]
     * return void
     **/
    public void deleteGame(Game game);
    /***
     * @author dinggc
     * Description //更新一篇游戏文章
     * Date 上午8:43 19-1-8
     * @param [game]
     * return void
     **/
    public void updateGame(Game game);
    /***
     * @author dinggc
     * Description //插入笔记资源
     * Date 上午9:50 19-1-9
     * Param [fileNote]
     * return void
     **/
    public void insertFileNote(FileNote fileNote);
    /***
     * @author dinggc
     * Description //根据笔记id获取笔记资源
     * Date 上午10:07 19-1-9
     * Param [noteId]
     * return java.util.List<cn.dingdm.website.entities.FileNote>
     **/
    public List<FileNote> getAllNoteSourceByNoteId(int noteId);
    /***
     * @author dinggc
     * Description //删除相关资源
     * Date 下午5:22 19-1-10
     * Param [fileNote]
     * return void
     **/
    public void deleteSourceById(FileNote fileNote);
}
