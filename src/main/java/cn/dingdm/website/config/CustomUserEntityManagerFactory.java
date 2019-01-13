package cn.dingdm.website.config;

import org.activiti.engine.impl.interceptor.Session;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.activiti.engine.impl.persistence.entity.UserIdentityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
/**
 * @author dinggc
 * Description //Activiti组管理工厂，重载实现Activiti框架对于用户的管理方法
 * Date 下午4:53 18-12-31
 * Param 
 * return 
 **/
public class CustomUserEntityManagerFactory implements SessionFactory {

    @Resource
    private CustomUserEntityManager customUserEntityManager;

    @Override
    public Class<?> getSessionType() {
        return UserIdentityManager.class;
    }

    @Override
    public Session openSession() {
        return customUserEntityManager;
    }

    @Autowired
    public void setCustomUserEntityManager(CustomUserEntityManager customUserEntityManager){
        this.customUserEntityManager = customUserEntityManager;
    }
}
