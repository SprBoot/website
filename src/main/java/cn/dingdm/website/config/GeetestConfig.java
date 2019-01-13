package cn.dingdm.website.config;

/**
 * GeetestWeb配置文件
 *
 *
 */
public class GeetestConfig {

    /**
     * @author dinggc
     * Description //滑动验证的id
     * Date 下午4:59 18-12-31
     * Param
     * return
     **/
    private static final String geetest_id = "858e2004db0855b437b0b23855829ed5";
    /***
     * @author dinggc
     * Description //滑动验证的key
     * Date 下午4:59 18-12-31
     * Param
     * return
     **/
    private static final String geetest_key = "50b7287b5d7aac3bcd16c63da227a9da";
    private static final boolean newfailback = true;

    public static final String getGeetest_id() {
        return geetest_id;
    }

    public static final String getGeetest_key() {
        return geetest_key;
    }

    public static final boolean isnewfailback() {
        return newfailback;
    }

}
