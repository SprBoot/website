package cn.dingdm.website.config;

import org.activiti.engine.impl.interceptor.Session;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.activiti.engine.impl.persistence.entity.GroupEntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
/**
 * @author dinggc
 * Description //Activiti组管理工厂，重载实现Activiti框架对于组的管理方法
 * Date 下午4:52 18-12-31
 * Param
 * return
 **/
public class CustomGroupEntityManagerFactory implements SessionFactory {

    @Resource
    private CustomGroupEntityManager customGroupEntityManager;
    @Override
    public Class<?> getSessionType() {
        return GroupEntityManager.class;
    }

    @Override
    public Session openSession() {
        return customGroupEntityManager;
    }

    @Autowired
    public void setCustomGroupEntityManager(CustomGroupEntityManager customGroupEntityManager){
        this.customGroupEntityManager = customGroupEntityManager;
    }
}
