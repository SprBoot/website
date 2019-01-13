package cn.dingdm.website.mapper;

import cn.dingdm.website.entities.*;

import java.util.List;

public interface UserMapper {
    /***
     * @author dinggc
     * Description //获取个人信息
     * Date 上午8:41 19-1-2
     * Param []
     * return cn.dingdm.website.entities.User
     **/
    public User getUserById();
    /***
     * @author dinggc
     * Description //获取知识
     * Date 上午8:41 19-1-2
     * Param []
     * return java.util.List<cn.dingdm.website.entities.KnowLedge>
     **/
    public List<KnowLedge> getKnowledge();
    /***
     * @author dinggc
     * Description //获取能力
     * Date 上午8:41 19-1-2
     * Param []
     * return java.util.List<cn.dingdm.website.entities.Abilities>
     **/
    public List<Abilities> getAbility();
    /***
     * @author dinggc
     * Description //shiro认证
     * Date 上午8:41 19-1-2
     * Param [username]
     * return cn.dingdm.website.entities.Vuser
     **/
    public Vuser getUserToConfirm(String username);

    /***
     * @author dinggc
     * Description //注册验证用户名是否重复
     * Date 上午8:41 19-1-2
     * Param [username]
     * return cn.dingdm.website.entities.Vuser
     **/
    public Vuser getVuserToCheckRepeat(String username);

    /***
     * @author dinggc
     * Description //插入拜访用户
     * Date 上午8:42 19-1-2
     * Param [vuser]
     * return void
     **/
    public void insertVuser(Vuser vuser);
    /***
     * @author dinggc
     * Description //获取权限信息
     * Date 上午8:42 19-1-2
     * Param [id]
     * return java.util.List<cn.dingdm.website.entities.Permission>
     **/
    public List<Permission> getPermissionById(int id);

    /***
     * @author dinggc
     * Description //根据uuid获取用户对象
     * Date 上午8:42 19-1-2
     * Param [uuid]
     * return cn.dingdm.website.entities.Vuser
     **/
    public Vuser getVuserByUUID(String uuid);
    /***
     * @author dinggc
     * Description //更新邮件验证
     * Date 上午8:42 19-1-2
     * Param [vuser]
     * return void
     **/
    public void updateVuserByUUID(Vuser vuser);
    /***
     * @author dinggc
     * Description //注册验证邮箱是否重复
     * Date 上午8:42 19-1-2
     * Param [email]
     * return cn.dingdm.website.entities.Vuser
     **/
    public Vuser getVuserToCheckRepeatEmail(String email);
    /***
     * @author dinggc
     * Description //rabbitmq接收消息
     * Date 上午8:43 19-1-2
     * Param [id]
     * return cn.dingdm.website.entities.Vuser
     **/
    public Vuser getVuserRecvById(int id);
}