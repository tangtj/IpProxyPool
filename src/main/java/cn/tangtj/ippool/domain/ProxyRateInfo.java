package cn.tangtj.ippool.domain;

import cn.tangtj.ippool.util.TimeUtils;

/**
 * @author tang
 * @date 2019/9/28
 */
public class ProxyRateInfo extends AbstractProxyInfo implements ProxyRate {

    /**
     * 评分
     */
    private ProxyRateLevel rate;

    private long lastTestTime;

    private final Object rateLock = new Object();

    public ProxyRateInfo(String ip, int port) {
        super(ip, port);
    }

    public ProxyRateInfo(ProxyInfo info) {
        super(info.getIp(), info.getPort());
        rate = ProxyRateLevel.C;
        lastTestTime = TimeUtils.currentTimeSecond();
    }

    @Override
    public ProxyRateLevel getRate() {
        return rate;
    }

    @Override
    public ProxyRateLevel downRate() {
        synchronized (rateLock) {
            rate = rate.downRate();
            if (rate == null) {
                rate = ProxyRateLevel.E;
            }
            return rate;
        }
    }

    @Override
    public ProxyRateLevel incrRate() {
        synchronized (rateLock) {
            rate = rate.incrRate();
            if (rate == null) {
                rate = ProxyRateLevel.A;
            }
            return rate;
        }
    }

    @Override
    public String toString() {
        return "ProxyRateInfo{" +
                "port=" + port +
                ", ip='" + ip + '\'' +
                "} " + super.toString();
    }

    public long getLastTestTime() {
        return lastTestTime;
    }

    public void setLastTestTime(long lastTestTime) {
        this.lastTestTime = lastTestTime;
    }

    public void setRate(ProxyRateLevel rate) {
        this.rate = rate;
    }
}
