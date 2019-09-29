package cn.tangtj.proxypool.capture;

import java.time.Instant;

/**
 * @author tang
 */
public interface ProxyCapturePeriod {

    /**
     * 获取最近一次抓取时间
     *
     * @return 时间戳
     */
    Instant lastCapture();

    /**
     * 是否到达可抓取时间
     *
     * @return
     */
    boolean capturePeriodAble();

    /**
     * 获得抓取间隔
     *
     * @return 单位秒
     */
    long getCapturePeriod();
}
