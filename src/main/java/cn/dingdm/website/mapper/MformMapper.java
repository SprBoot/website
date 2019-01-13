package cn.dingdm.website.mapper;

import cn.dingdm.website.entities.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MformMapper {
    /***
     * @author dinggc
     * Description //获取个人信息
     * Date 上午8:19 19-1-2
     * Param []
     * return cn.dingdm.website.entities.User
     **/
    public User getUserById();

    /***
     * @author dinggc
     * Description //更新个人信息
     * Date 上午8:19 19-1-2
     * Param [user]
     * return void
     **/
    public void updateInfo(User user);

    /***
     * @author dinggc
     * Description //得到后台笔记类型
     * Date 上午8:19 19-1-2
     * Param []
     * return java.util.List<cn.dingdm.website.entities.NoteType>
     **/
    public List<NoteType> getNoteType();

    /***
     * @author dinggc
     * Description //得到类型id
     * Date 上午8:19 19-1-2
     * Param [lxmc]
     * return java.lang.String
     **/
    public String getNoteTypeIdByLxmc(String lxmc);

    /***
     * @author dinggc
     * Description //插入笔记
     * Date 上午8:20 19-1-2
     * Param [note]
     * return void
     **/
    public Integer insertNote(@Param("lxid") String lxid,@Param("bjmc") String bjmc,@Param("bjzz") String bjzz,
                              @Param("bjnr") String bjnr,@Param("description") String description,@Param("date") String date,
                              @Param("del_flag") int del_flag);
    /***
     * @author 获取笔记类型数量
     * Description //TODO
     * Date 上午8:20 19-1-2
     * Param [lxid]
     * return int
     **/
    public int getNoteTypeCount(@Param("lxid") String lxid);
    /***
     * @author dinggc
     * Description //插入笔记类型
     * Date 上午8:23 19-1-2
     * Param [noteType]
     * return void
     **/
    public void insertNoteType(NoteType noteType);
    /***
     * @author dinggc
     * Description //获取笔记数量
     * Date 上午8:23 19-1-2
     * Param []
     * return int
     **/
    public int getNoteCount();
    /***
     * @author dinggc
     * Description //根据获取的笔记数量进行分页
     * Date 上午8:23 19-1-2
     * Param [start, end]
     * return java.util.List<cn.dingdm.website.entities.Note>
     **/
    public List<Note> getNoteByCount(@Param("start") int start,@Param("end") int end);

    /***
     * @author dinggc
     * Description //根据id得到笔记
     * Date 上午8:24 19-1-2
     * Param [id]
     * return cn.dingdm.website.entities.Note
     **/
    public Note getNoteById(int id);

    /***
     * @author dinggc
     * Description //根据id删除笔记
     * Date 上午8:24 19-1-2
     * Param [note]
     * return void
     **/
    public void deleteNote(Note note);

    /***
     * @author dinggc
     * Description //根据笔记类型id得到笔记类型
     * Date 上午8:24 19-1-2
     * Param [lxid]
     * return cn.dingdm.website.entities.NoteType
     **/
    public NoteType getNoteTypeById(String lxid);

    /***
     * @author dinggc
     * Description //更新笔记
     * Date 上午8:25 19-1-2
     * Param [note]
     * return void
     **/
    public void updateNote(Note note);

    /***
     * @author dinggc
     * Description //得到知识
     * Date 上午8:25 19-1-2
     * Param []
     * return java.util.List<cn.dingdm.website.entities.KnowLedge>
     **/
    public List<KnowLedge> getKnowLedge();

    /***
     * @author dinggc
     * Description //保存未读信息
     * Date 上午8:25 19-1-2
     * Param [msgrabbitmq]
     * return void
     **/
    public void insertMsgRabbitMq(MsgRabbitMq msgrabbitmq);

    /***
     * @author dinggc
     * Description //得到用户的数量
     * Date 上午8:26 19-1-2
     * Param []
     * return int
     **/
    public int getVuserCount();
    /***
     * @author dinggc
     * Description //得到所有的用户
     * Date 上午8:26 19-1-2
     * Param [id]
     * return java.util.List<cn.dingdm.website.entities.Vuser>
     **/
    public List<Vuser> getAllVuserByRoleId(int id);
    /***
     * @author dinggc
     * Description //得到所有的角色
     * Date 上午8:26 19-1-2
     * Param []
     * return java.util.List<cn.dingdm.website.entities.Role>
     **/
    public List<Role> getAllRole();
    /***
     * @author dinggc
     * Description //得到其他角色
     * Date 上午8:27 19-1-2
     * Param []
     * return java.util.List<cn.dingdm.website.entities.Vuser>
     **/
    public List<Vuser> getAllOtherVuserWithoutRole();
    /***
     * @author dinggc
     * Description //根据用户id和角色id删除对应用户的角色
     * Date 上午8:27 19-1-2
     * Param [userid, roleid]
     * return void
     **/
    public void deleteRoleByUserid(@Param("userid") int userid,@Param("roleid") int roleid);
    /***
     * @author dinggc
     * Description //删除用户
     * Date 上午8:27 19-1-2
     * Param [id]
     * return void
     **/
    public void deleteUser(int id);
    /***
     * @author dinggc
     * Description //删除用户对应的角色
     * Date 上午8:27 19-1-2
     * Param [id]
     * return void
     **/
    public void deleteRoleByUserId(int id);
    /***
     * @author dinggc
     * Description //赋予角色用户对应的角色
     * Date 上午8:28 19-1-2
     * Param [roleUser]
     * return void
     **/
    public void insertRoleByUserId(RoleUser roleUser);
    /***
     * @author dinggc
     * Description //得到所有的权限
     * Date 上午8:28 19-1-2
     * Param []
     * return java.util.List<cn.dingdm.website.entities.Permission>
     **/
    public List<Permission> getAllPermission();
    /***
     * @author dinggc
     * Description //删除对应角色
     * Date 上午8:28 19-1-2
     * Param [id]
     * return void
     **/
    public void deleteRoleUserByRoleId(int id);
    /***
     * @author dinggc
     * Description //插入角色
     * Date 上午8:29 19-1-2
     * Param [role]
     * return void
     **/
    public void insertRole(Role role);
    /***
     * @author dinggc
     * Description //删除对应的权限
     * Date 上午8:29 19-1-2
     * Param [id]
     * return void
     **/
    public void deleteRolePermissionByPermissionId(int id);
    /***
     * @author dinggc
     * Description //插入权限
     * Date 上午8:30 19-1-2
     * Param [permission]
     * return void
     **/
    public void insertPermission(Permission permission);
    /***
     * @author dinggc
     * Description //得到所有的用户
     * Date 上午8:30 19-1-2
     * Param [id]
     * return java.util.List<cn.dingdm.website.entities.Permission>
     **/
    public List<Permission> getAllPermissionByRoleId(int id);
    /***
     * @author dinggc
     * Description //删除角色的权限
     * Date 上午8:30 19-1-2
     * Param [permissionid, roleid]
     * return void
     **/
    public void deletePermissionByPermissionid(@Param("permissionid") int permissionid,@Param("roleid") int roleid);
    /***
     * @author dinggc
     * Description //添加角色对应权限
     * Date 上午8:30 19-1-2
     * Param [rolePermission]
     * return void
     **/
    public void insertRoleByPermissionId(RolePermission rolePermission);
    /***
     * @author dinggc
     * Description //得到所有用户申请单的数量
     * Date 上午8:30 19-1-2
     * Param [userid]
     * return int
     **/
    public int getProcCountByUserid(int userid);
    /***
     * @author dinggc
     * Description //得到所有的申请单
     * Date 上午8:31 19-1-2
     * Param [userid]
     * return java.util.List<cn.dingdm.website.entities.BackAppl>
     **/
    public List<BackAppl> getProcByUserId(@Param("userid") int userid);
    /***
     * @author dinggc
     * Description //插入申请
     * Date 上午8:31 19-1-2
     * Param [backAppl]
     * return void
     **/
    public void insertBackAppl(BackAppl backAppl);
    /***
     * @author dinggc
     * Description //删除请求
     * Date 上午8:31 19-1-2
     * Param [backAppl]
     * return void
     **/
    public void updateBackAppl(BackAppl backAppl);
    /***
     * @author dinggc
     * Description //获取管理员用户
     * Date 上午8:31 19-1-2
     * Param [id]
     * return java.util.List<cn.dingdm.website.entities.Vuser>
     **/
    public List<Vuser> getAdminByAdminId(int id);
    /***
     * @author dinggc
     * Description //获取申请的内容，设置流程变量
     * Date 上午8:32 19-1-2
     * Param [id]
     * return cn.dingdm.website.entities.BackAppl
     **/
    public BackAppl getBackAppl(int id);
    /***
     * @author dinggc
     * Description //更新申请单的状态
     * Date 上午8:32 19-1-2
     * Param [backAppl]
     * return void
     **/
    public void updateBackApplState(BackAppl backAppl);
    /***
     * @author dinggc
     * Description //请求申请完成
     * Date 上午8:32 19-1-2
     * Param [backAppl]
     * return void
     **/
    public void updateBackApplStateToSuccess(BackAppl backAppl);
    /***
     * @author dinggc
     * Description //请求被驳回，请求失败
     * Date 上午8:32 19-1-2
     * Param [backAppl]
     * return void
     **/
    public void updateBackApplStateToFail(BackAppl backAppl);
    /***
     * @author dinggc
     * Description //请求被驳回，删除当前的流程
     * Date 上午8:33 19-1-2
     * Param [processDefinitionId]
     * return void
     **/
    public void deleteAllProcess(String processDefinitionId);
    /***
     * @author dinggc
     * Description //得到所有的笔记
     * Date 上午8:33 19-1-2
     * Param []
     * return java.util.List<cn.dingdm.website.entities.Note>
     **/
    public List<Note> getAllNote();
    /***
     * @author dinggc
     * Description //得到所有的用户
     * Date 上午8:33 19-1-2
     * Param []
     * return java.util.List<cn.dingdm.website.entities.Vuser>
     **/
    public List<Vuser> getAllUser();
    /***
     * @author dinggc
     * Description //根据用户的id得到他的角色
     * Date 上午8:33 19-1-2
     * Param [userid]
     * return cn.dingdm.website.entities.Role
     **/
    public Role getUserRoleByUserId(int userid);
    /***
     * @author dinggc
     * Description //根据id获取角色
     * Date 上午8:34 19-1-2
     * Param [id]
     * return cn.dingdm.website.entities.Role
     **/
    public Role getRoleById(int id);
    /***
     * @author dinggc
     * Description //得到带有role的permission
     * Date 上午8:34 19-1-2
     * Param [id]
     * return java.util.List<cn.dingdm.website.entities.PermissionRole>
     **/
    public List<PermissionRole> getAllPermissionRoleByRoleId(int id);
    /***
     * @author dinggc
     * Description //更新申请单的注释信息
     * Date 上午8:34 19-1-2
     * Param [backAppl]
     * return void
     **/
    public void updateBackZhushi(BackAppl backAppl);
    /***
     * @author dinggc
     * Description //插入知识的类型
     * Date 上午8:34 19-1-2
     * Param [knowLedge]
     * return void
     **/
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
     * Param [id]
     * return cn.dingdm.website.entities.Game
     **/
    public Game getGameById(int id);
    /***
     * @author dinggc
     * Description //根据id删除游戏文章
     * Date 下午7:24 19-1-4
     * Param [id]
     * return void
     **/
    public void deleteGame(Game game);
    /***
     * @author dinggc
     * Description //更新一篇游戏文章
     * Date 上午8:43 19-1-8
     * Param [game]
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
