package cn.tangtj.ippool.proxytest;

import cn.tangtj.ippool.exception.RequestException;
import okhttp3.OkHttpClient;

/**
 * @author tang
 */
public interface ProxyConnectTest {

    /**
     * 代理连通性测试
     *
     * @param client http客户端
     * @param ip     代理ip
     * @return 返回`true`
     * @throws RequestException
     */
    boolean test(OkHttpClient client, String ip) throws RequestException;

}
