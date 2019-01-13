package cn.dingdm.website.entities;

/***
 * @author dinggc
 * Description //自定义的流程定义实体类
 * Date 下午5:10 18-12-31
 * Param
 * return
 **/
public class ActProcdef {
    /**
     * id标识
     **/
    private String id;
    /**
     * 流程定义名称
     **/
    private String name;
    /**
     * 流程定义的key
     **/
    private String key;
    /**
     * 部署流程的版本
     **/
    private int version;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
