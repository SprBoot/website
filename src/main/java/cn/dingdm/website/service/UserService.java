package cn.dingdm.website.service;

import cn.dingdm.website.entities.*;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

public interface UserService {
    //获取个人信息
    public User getUserById();
    //获取知识
    public List<KnowLedge> getKnowledge();
    //获取能力
    public List<Abilities> getAbility();
    //shiro认证
    public Vuser getUserToConfirm(String username);
    //注册验证用户名是否重复
    public Vuser getVuserToCheckRepeat(String username);
    //插入拜访用户
    public void insertVuser(Vuser vuser);

    //获取权限信息
    public List<Permission> getPermissionById(int id);
    //根据uuid获取用户对象
    public Vuser getVuserByUUID(String uuid);

    //更新邮件验证
    public void updateVuserByUUID(Vuser vuser);
    //注册验证邮箱是否重复
    public Vuser getVuserToCheckRepeatEmail(String email);
    //rabbitmq接收消息
    public Vuser getVuserRecvById(int id);
}
