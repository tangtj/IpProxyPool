package cn.tangtj.proxypool.capture;

import cn.tangtj.proxypool.domain.ProxyInfo;

import java.util.List;

public interface ProxyCapture {

    /**
     * 获取ip代理
     *
     * @return
     */
    List<ProxyInfo> getProxy();

    /**
     * 获取当前id
     *
     * @return
     */
    String getId();
}
