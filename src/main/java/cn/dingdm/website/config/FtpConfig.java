package cn.dingdm.website.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
/**
 * @author dinggc
 * Description //Ftp的配置文件，用于获取properties配置文件中
 * 关于ftp相关的配置，用作文件上传到ftp服务器
 * Date 下午4:56 18-12-31
 * Param
 * return
 **/
@Component
public class FtpConfig {
    /**
     * IP地址
     **/
    @Value("${ftp.host}")
    private String host;
    /**
     * 端口
     **/
    @Value("${ftp.port}")
    private int port;
    /**
     * 用户名
     **/
    @Value("${ftp.username}")
    private String username;
    /**
     * 密码
     **/
    @Value("${ftp.password}")
    private String password;
    /**
     * 文件在ftp保存的路径
     **/
    @Value("${ftp.savePath}")
    private String savePath;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }
}
