package cn.tangtj.proxypool.pool;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import okhttp3.OkHttpClient;

import java.net.Proxy;
import java.util.concurrent.TimeUnit;

/**
 * @author tang
 * @date 2019/9/28
 */
public class HttpClientPool {


    private NetProxyPool proxyPool;

    private Cache<String, OkHttpClient> clientStore;

    private HttpClientPool() {
        clientStore = CacheBuilder
                .newBuilder()
                .expireAfterAccess(20, TimeUnit.MINUTES)
                .build();
        this.proxyPool = NetProxyPool.getInstance();
    }

    private static class Inner {
        private static final HttpClientPool INSTANCE = new HttpClientPool();
    }

    public static HttpClientPool getInstance() {
        return Inner.INSTANCE;
    }

    public OkHttpClient get(String ip, int port) {

        Proxy proxy = proxyPool.get(ip, port);
        if (proxy == null) {
            return null;
        }

        String key = keyName(ip, port);

        OkHttpClient client = clientStore.getIfPresent(key);

        if (client != null) {
            return client;
        }

        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        client = clientBuilder.proxy(proxy).build();

        clientStore.put(key, client);
        return client;
    }

    private String keyName(String ip, int port) {
        return ip + ":" + port;
    }
}
