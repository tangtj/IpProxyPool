package cn.tangtj.ippool.util;

/**
 * @author tang
 * @date 2019/9/28
 */
public class TimeUtils {

    /**
     * 获取当前时间戳,毫秒
     *
     * @return 毫秒
     */
    public static long currentTimeMillis() {
        return System.currentTimeMillis();
    }

    /**
     * 获取当前时间戳,秒
     *
     * @return 秒
     */
    public static long currentTimeSecond() {
        return currentTimeMillis() / 1000L;
    }
}
