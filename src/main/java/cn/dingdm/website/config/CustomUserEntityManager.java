package cn.dingdm.website.config;

import org.activiti.engine.identity.User;
import org.activiti.engine.impl.persistence.entity.UserEntityManager;
import org.springframework.stereotype.Component;

@Component
/***
 * @author dinggc
 * Description //Activiti中自定义的用户管理类
 * Date 下午4:53 18-12-31
 * Param
 * return
 **/
public class CustomUserEntityManager extends UserEntityManager {

    @Override
    public User findUserById(String userId) {
        return super.findUserById(userId);
    }
}
