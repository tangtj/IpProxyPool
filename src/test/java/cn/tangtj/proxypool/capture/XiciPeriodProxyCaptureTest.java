package cn.tangtj.proxypool.capture;

import cn.tangtj.proxypool.capture.impl.XiciPeriodProxyCapture;
import org.junit.Test;

public class XiciPeriodProxyCaptureTest {

    @Test
    public void getProxy() {

        XiciPeriodProxyCapture xiciPeriodProxyCapture = new XiciPeriodProxyCapture();
        xiciPeriodProxyCapture.getProxy();
    }

    @Test
    public void getId() {
    }
}