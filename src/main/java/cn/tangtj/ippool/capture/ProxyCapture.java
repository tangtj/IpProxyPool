package cn.tangtj.ippool.capture;

import cn.tangtj.ippool.domain.ProxyInfo;

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
