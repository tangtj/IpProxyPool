package cn.tangtj.ippool.pool;

import cn.tangtj.ippool.domain.ProxyInfo;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.net.*;
import java.util.concurrent.TimeUnit;

/**
 * @author tang
 * @date 2019/9/28
 */
public class NetProxyPool {

    private Cache<String, Proxy> proxyStore;

    private NetProxyPool() {
        proxyStore = CacheBuilder
                .newBuilder()
                .expireAfterAccess(20, TimeUnit.MINUTES)
                .build();
    }

    public void put(ProxyInfo proxyInfo) {
        String key = keyName(proxyInfo.getIp(), proxyInfo.getPort());
        Proxy proxy = proxyStore.getIfPresent(key);
        if (proxy == null) {
            proxy = newProxy(proxyInfo.getIp(), proxyInfo.getPort());
            if (proxy != null) {
                proxyStore.put(key, proxy);
            }
        }
    }

    public Proxy get(String ip, int port) {

        String key = keyName(ip, port);

        //从缓存中获取Proxy
        Proxy target = proxyStore.getIfPresent(key);
        if (target != null) {
            return target;
        }
        //缓存没有,创建新的网络代理
        target = newProxy(ip, port);

        if (target != null) {
            proxyStore.put(key, target);
        }

        return target;
    }

    private Proxy newProxy(String ip, int port) {
        InetAddress inetAddress;
        try {
            inetAddress = Inet4Address.getByName(ip);
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return null;
        }
        InetSocketAddress socketAddress = new InetSocketAddress(inetAddress, port);
        return new Proxy(Proxy.Type.HTTP, socketAddress);
    }

    private String keyName(String ip, int port) {
        return ip + ":" + port;
    }

    public static NetProxyPool getInstance() {
        return Inner.INSTANCE;
    }

    private static class Inner {
        private static final NetProxyPool INSTANCE = new NetProxyPool();
    }
}
