package cn.dingdm.website.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/***
 * @author dinggc
 * Description //Mvc配置文件
 * Date 下午5:06 18-12-31
 * Param
 * return
 **/
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    /***
     * @author dinggc
     * Description //配置静态资源访问的路径映射，由于做了负载均衡，因此配置被我去掉了
     * Date 下午5:06 18-12-31
     * Param [registry]
     * return void
     **/
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

//文件磁盘图片url 映射
//配置server虚拟路径，handler为前台访问的目录，locations为files相对应的本地路径
        //registry.addResourceHandler("/uploadImages/**").addResourceLocations("file:/opt/uploadImages/");
    }
}
