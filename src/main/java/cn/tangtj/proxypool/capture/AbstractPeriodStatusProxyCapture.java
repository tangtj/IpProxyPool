package cn.tangtj.proxypool.capture;

/**
 * @author tang
 * @date 2019/9/28
 */
public abstract class AbstractPeriodStatusProxyCapture extends AbstractPeriodProxyCapture implements ProxyCaptureStatusAble {

    public AbstractPeriodStatusProxyCapture(long capturePeriod) {
        super(capturePeriod);
    }
}
