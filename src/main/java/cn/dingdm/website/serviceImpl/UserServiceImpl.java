package cn.dingdm.website.serviceImpl;

import cn.dingdm.website.entities.*;
import cn.dingdm.website.mapper.UserMapper;
import cn.dingdm.website.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;
    @Autowired
    RedisCacheManager redisCacheManager;

    @Override
    @Cacheable(cacheNames = "getUserById")
    public User getUserById() {
        return userMapper.getUserById();
    }

    @Override
    @Cacheable(cacheNames = "getKnowledge")
    public List<KnowLedge> getKnowledge() {
        return userMapper.getKnowledge();
    }

    @Override
    @Cacheable(cacheNames = "getAbility")
    public List<Abilities> getAbility() {
        return userMapper.getAbility();
    }

    @Override
    public Vuser getUserToConfirm(String username) {
        return userMapper.getUserToConfirm(username);
    }

    @Override
    public Vuser getVuserToCheckRepeat(String username){
        return userMapper.getVuserToCheckRepeat(username);
    }

    @Override
    public void insertVuser(Vuser vuser) {
        userMapper.insertVuser(vuser);
    }

    @Override
    public List<Permission> getPermissionById(int id) {
        return userMapper.getPermissionById(id);
    }

    @Override
    public Vuser getVuserByUUID(String uuid) {
        return userMapper.getVuserByUUID(uuid);
    }

    @Override
    public void updateVuserByUUID(Vuser vuser) {
        userMapper.updateVuserByUUID(vuser);
    }

    @Override
    public Vuser getVuserToCheckRepeatEmail(String email) {
        return userMapper.getVuserToCheckRepeatEmail(email);
    }

    @Override
    public Vuser getVuserRecvById(int id) {
        return userMapper.getVuserRecvById(id);
    }


}
