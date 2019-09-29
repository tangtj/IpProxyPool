package cn.tangtj.ippool.pool;

/**
 * @author tang
 * @date 2019/9/29
 */
public class ProxyKeepPool {

    private final IpProxyStore store;

    private ProxyKeepPool() {
        store = new IpProxyStore();
    }

    public IpProxyStore getPool() {
        return store;
    }

    public static ProxyKeepPool getInstance() {
        return ProxyKeepPool.Inner.INSTANCE;
    }

    private static class Inner {
        private static final ProxyKeepPool INSTANCE = new ProxyKeepPool();
    }
}
