package cn.tangtj.ippool.capture;

import cn.tangtj.ippool.util.TimeUtils;

import java.time.Instant;

/**
 * @author tang
 * @date 2019/9/28
 */
public abstract class AbstractPeriodProxyCapture implements ProxyCapture, ProxyCapturePeriod {

    protected final long capturePeriod;

    protected Instant lastCapture;

    public AbstractPeriodProxyCapture(long capturePeriod) {
        this.capturePeriod = capturePeriod;
    }


    @Override
    public boolean capturePeriodAble() {
        if (lastCapture == null) {
            return true;
        }
        return lastCapture.plusSeconds(capturePeriod).getEpochSecond() >= TimeUtils.currentTimeSecond();
    }

    @Override
    public Instant lastCapture() {
        return lastCapture;
    }

    @Override
    public long getCapturePeriod() {
        return capturePeriod;
    }
}
