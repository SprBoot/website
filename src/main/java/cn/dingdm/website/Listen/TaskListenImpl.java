package cn.dingdm.website.Listen;

import cn.dingdm.website.entities.Vuser;
import cn.dingdm.website.service.MformService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
/***
 * @author dinggc
 * Description //忘了
 * Date 上午8:13 19-1-2
 * Param
 * return
 **/
public class TaskListenImpl implements TaskListener {

    @Override
    public void notify(DelegateTask delegateTask) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
        MformService mformService = webApplicationContext.getBean(MformService.class);
        List<String> strings = new ArrayList<String>();
        List<Vuser> list = mformService.getAdminByAdminId(1);
        for (Vuser vuser : list) {
            strings.add(vuser.getUsername());
        }
        delegateTask.addCandidateUsers(strings);
    }
}
