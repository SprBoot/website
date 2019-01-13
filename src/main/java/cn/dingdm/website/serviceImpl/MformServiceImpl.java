package cn.dingdm.website.serviceImpl;

import cn.dingdm.website.entities.*;
import cn.dingdm.website.mapper.MformMapper;
import cn.dingdm.website.service.BaseService;
import cn.dingdm.website.service.MformService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MformServiceImpl implements MformService {

    @Autowired
    MformMapper mformMapper;
    @Autowired
    BaseService baseService;
    @Autowired
    TaskService taskService;
    @Autowired
    RuntimeService runtimeService;

    @Cacheable(cacheNames = "getUserById")
    @Override
    public User getUserById() {
        return mformMapper.getUserById();
    }

    @CacheEvict(cacheNames = "getUserById",allEntries=true)
    @Override
    public void updateInfo(User user) {
        mformMapper.updateInfo(user);
    }

    @Override
    @Cacheable(cacheNames = "getNoteType")
    public List<NoteType> getNoteType() {
        return mformMapper.getNoteType();
    }

    @Override
    public String getNoteTypeIdByLxmc(String lxmc) {
        return mformMapper.getNoteTypeIdByLxmc(lxmc);
    }

    @Override
    @CacheEvict(value = {"getNoteByCount","getBackNoteByCount","getNoteTypeByCount"},allEntries = true)
    public Integer insertNote(String lxid, String bjmc, String bjzz, String bjnr, String description, String date, int del_flag) {
        return mformMapper.insertNote(lxid,bjmc,bjzz,bjnr,description,date,del_flag);
    }

    @Override
    public int getNoteTypeCount(String lxid) {
        return mformMapper.getNoteTypeCount(lxid);
    }

    @Override
    @CacheEvict(cacheNames = {"getNoteByCount","getBackNoteByCount","getNoteType"},allEntries = true)
    public void insertNoteType(NoteType noteType) {
        mformMapper.insertNoteType(noteType);
    }

    @Override
    public int getNoteCount() {
        return mformMapper.getNoteCount();
    }

    @Override
    @Cacheable(cacheNames = "getBackNoteByCount")
    public List<Note> getNoteByCount(Page page) {
        int start = page.getCurrentResult();
        int end = start+page.getLimit();
        return mformMapper.getNoteByCount(start,end-start);
    }

    @Override
    public Note getNoteById(int id) {
        return mformMapper.getNoteById(id);
    }

    @Override
    public void deleteNote(Note note) {
        mformMapper.deleteNote(note);
    }

    @Override
    public NoteType getNoteTypeById(String lxid) {
        return mformMapper.getNoteTypeById(lxid);
    }
    @Override
    @CacheEvict(cacheNames = {"getNoteByCount","getBackNoteByCount"},allEntries = true)
    public void updateNote(Note note) {
        mformMapper.updateNote(note);
    }

    @Override
    @Cacheable(cacheNames = "getKnowledge")
    public List<KnowLedge> getKnowLedge() {
        return mformMapper.getKnowLedge();
    }

    @Override
    public void insertMsgRabbitMq(MsgRabbitMq msgrabbitmq) {
        mformMapper.insertMsgRabbitMq(msgrabbitmq);
    }

    @Override
    public int getVuserCount() {
        return mformMapper.getVuserCount();
    }

    @Override
    public List<Vuser> getAllVuserByRoleId(int id) {
        return mformMapper.getAllVuserByRoleId(id);
    }

    @Override
    public List<Role> getAllRole() {
        return mformMapper.getAllRole();
    }

    @Override
    public List<Vuser> getAllOtherVuserWithoutRole() {
        return mformMapper.getAllOtherVuserWithoutRole();
    }

    @Override
    public void deleteRoleByUserid(int userid, int roleid) {
        mformMapper.deleteRoleByUserid(userid,roleid);
    }

    @Override
    public void deleteUser(int id) {
        mformMapper.deleteUser(id);
    }

    @Override
    public void deleteRoleByUserId(int id) {
        mformMapper.deleteRoleByUserId(id);
    }

    @Override
    public void insertRoleByUserId(RoleUser roleUser) {
        mformMapper.insertRoleByUserId(roleUser);
    }

    @Override
    public List<Permission> getAllPermission() {
        return mformMapper.getAllPermission();
    }

    @Override
    public void deleteRoleUserByRoleId(int id) {
        mformMapper.deleteRoleUserByRoleId(id);
    }

    @Override
    public void insertRole(Role role) {
        mformMapper.insertRole(role);
    }

    @Override
    public void deleteRolePermissionByPermissionId(int id) {
        mformMapper.deleteRolePermissionByPermissionId(id);
    }

    @Override
    public void insertPermission(Permission permission) {
        mformMapper.insertPermission(permission);
    }

    @Override
    public List<Permission> getAllPermissionByRoleId(int id) {
        return mformMapper.getAllPermissionByRoleId(id);
    }

    @Override
    public void deletePermissionByPermissionid(int permissionid, int roleid) {
        mformMapper.deletePermissionByPermissionid(permissionid,roleid);
    }

    @Override
    public void insertRoleByPermissionId(RolePermission rolePermission) {
        mformMapper.insertRoleByPermissionId(rolePermission);
    }

    @Override
    public int getProcCountByUserid(int userid) {
        return mformMapper.getProcCountByUserid(userid);
    }

    @Override
    public List<BackAppl> getProcByUserId(int userid) {
        return mformMapper.getProcByUserId(userid);
    }

    @Override
    public void insertBackAppl(BackAppl backAppl) {
        mformMapper.insertBackAppl(backAppl);
    }

    @Override
    public void updateBackAppl(BackAppl backAppl) {
        mformMapper.updateBackAppl(backAppl);
    }

    @Override
    public List<Vuser> getAdminByAdminId(int id) {
        return mformMapper.getAdminByAdminId(id);
    }

    @Override
    public int findTaskCount(String username) {
        Vuser currentVuser = baseService.getCurrentVuser();
        username = currentVuser.getUsername();
        long count = taskService.createTaskQuery()
                .taskAssignee(username)//根据任务办理人查询
                .count();
        return (int)count;
    }

    @Override
    public ActTask findCurrentUserTaskToSubmit(String username) {
        //任务办理人
        Vuser currentVuser = baseService.getCurrentVuser();
        username = currentVuser.getUsername();
        //得到任务的Service
        //创建一个查询
        //Task task = taskService.createTaskQuery().taskId("").singleResult();
        Task list = taskService.createTaskQuery()
                .taskAssignee(username)//根据任务办理人查询
                .singleResult();
        ActTask actTask = new ActTask();
        BeanUtils.copyProperties(list,actTask);
        return actTask;
    }

    @Override
    public BackAppl getBackAppl(int id) {
        return mformMapper.getBackAppl(id);
    }

    @Override
    public void updateBackApplState(BackAppl backAppl) {
        mformMapper.updateBackApplState(backAppl);
    }

    @Override
    public void updateBackApplStateToSuccess(BackAppl backAppl) {
        mformMapper.updateBackApplStateToSuccess(backAppl);
    }

    @Override
    public void updateBackApplStateToFail(BackAppl backAppl) {
        mformMapper.updateBackApplStateToFail(backAppl);
    }

    @Override
    public void deleteAllProcess(String processDefinitionId) {
        mformMapper.deleteAllProcess(processDefinitionId);
    }

    @Override
    public List<Note> getAllNote() {
        return mformMapper.getAllNote();
    }

    @Override
    public List<Vuser> getAllUser() {
        return mformMapper.getAllUser();
    }

    @Override
    public Role getUserRoleByUserId(int userid) {
        return mformMapper.getUserRoleByUserId(userid);
    }

    @Override
    public Role getRoleById(int id) {
        return mformMapper.getRoleById(id);
    }

    @Override
    public List<PermissionRole> getAllPermissionRoleByRoleId(int id) {
        return mformMapper.getAllPermissionRoleByRoleId(id);
    }

    @Override
    public void updateBackZhushi(BackAppl backAppl) {
        mformMapper.updateBackZhushi(backAppl);
    }

    @Override
    @CacheEvict(cacheNames = "getKnowledge",allEntries = true)
    public void insertKnowLedge(KnowLedge knowLedge) {
        mformMapper.insertKnowLedge(knowLedge);
    }

    @Override
    public void insertPicture(Picture picture) {
        mformMapper.insertPicture(picture);
    }

    @Override
    @CacheEvict(cacheNames = {"lifeCount","lifeList"},allEntries = true)
    public void insertLife(Life life) {
        mformMapper.insertLife(life);
    }

    @Override
    public List<Life> getAllLife() {
        return mformMapper.getAllLife();
    }

    @Override
    public Life getLifeById(int id) {
        return mformMapper.getLifeById(id);
    }

    @Override
    public void updateLife(Life life) {
        mformMapper.updateLife(life);
    }

    @Override
    public void updatePicture(Picture picture) {
        mformMapper.updatePicture(picture);
    }

    @Override
    public void deleteLife(Life life) {
        mformMapper.deleteLife(life);
    }

    @Override
    public void insertGame(Game game) {
        mformMapper.insertGame(game);
    }

    @Override
    public List<Game> getAllGame() {
        return mformMapper.getAllGame();
    }

    @Override
    public Game getGameById(int id) {
        return mformMapper.getGameById(id);
    }

    @Override
    public void deleteGame(Game game) {
        mformMapper.deleteGame(game);
    }

    @Override
    public void updateGame(Game game) {
        mformMapper.updateGame(game);
    }

    @Override
    public void insertFileNote(FileNote fileNote) {
        mformMapper.insertFileNote(fileNote);
    }

    @Override
    public List<FileNote> getAllNoteSourceByNoteId(int noteId) {
        return mformMapper.getAllNoteSourceByNoteId(noteId);
    }

    @Override
    public void deleteSourceById(FileNote fileNote) {
        mformMapper.deleteSourceById(fileNote);
    }

    @Override
    public List<ActTask> findCurrentUserTask(String username) {
        //任务办理人
        Vuser currentVuser = baseService.getCurrentVuser();
        username = currentVuser.getUsername();
        //得到任务的Service
        //创建一个查询
        //Task task = taskService.createTaskQuery().taskId("").singleResult();
        List<Task> list = taskService.createTaskQuery()
                .taskCandidateUser(username)//根据任务办理人查询
                .list();

        List<ActTask> actTasks = new ArrayList<ActTask>();
        for(Task task : list){
            ActTask actTask = new ActTask();
            BeanUtils.copyProperties(task,actTask);
            String executionId = task.getExecutionId();
            BackAppl backAppl = (BackAppl) runtimeService.getVariable(executionId,"info");
            actTask.setBackAppl(backAppl);
            actTasks.add(actTask);
        }
        return actTasks;
    }

}
