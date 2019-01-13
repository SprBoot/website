package cn.dingdm.website.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Configuration
/***
 * @author dinggc
 * Description //阿里云的数据源配置
 * Date 下午4:54 18-12-31
 * Param
 * return
 **/
public class DruidConfig {
    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DruidDataSource druidDataSource(){
        return new DruidDataSource();
    }
    /**
     * @author dinggc
     * Description //配置阿里云的数据监控，配置后台的Servlet路径，任何关于druid的路径访问都会跳转到阿里云
     * 后台数据源管理界面
     * Date 下午4:54 18-12-31
     * Param []
     * return org.springframework.boot.web.servlet.ServletRegistrationBean
     **/
    @Bean
    public ServletRegistrationBean statViewServlet(){
        ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(),"/druid/*");
        Map<String,String> initParams = new HashMap<>();
        initParams.put("loginUsername","admin");
        initParams.put("loginPassword","admin");
        initParams.put("deny","");
        bean.setInitParameters(initParams);
        return bean;
    }

    /***
     * @author dinggc
     * Description //配置web监控的过滤器
     * Date 下午4:55 18-12-31
     * Param []
     * return org.springframework.boot.web.servlet.FilterRegistrationBean
     **/
    @Bean
    public FilterRegistrationBean webStatFilter(){
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new WebStatFilter());
        Map<String,String> initParams = new HashMap<>();

        initParams.put("exclusions","*.js,*.css,/druid/*");

        bean.setInitParameters(initParams);

        bean.setUrlPatterns(Arrays.asList("/*"));

        return bean;
    }
}
