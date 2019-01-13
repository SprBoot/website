package cn.dingdm.website.entities;
/***
 * @author dinggc
 * Description //尝试，使用rabbitmq做站内通知的实体类
 * Date 下午5:22 18-12-31
 * Param
 * return
 **/
public class MsgRabbitMq extends BaseEntity{
    /**
     * id标识
     **/
    private String uuid;
    /**
     * 通信的内容
     **/
    private String context;
    /**
     * 是否已读
     **/
    private int have_read;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public int getHave_read() {
        return have_read;
    }

    public void setHave_read(int have_read) {
        this.have_read = have_read;
    }
}
