package cn.tangtj.proxypool.pool;

/**
 * @author tang
 * @date 2019/9/29
 */
public class ProxyTestPool {

    private final IpProxyStore store;

    private ProxyTestPool() {
        store = new IpProxyStore();
    }

    public IpProxyStore getPool() {
        return store;
    }

    public static ProxyTestPool getInstance() {
        return ProxyTestPool.Inner.INSTANCE;
    }

    private static class Inner {
        private static final ProxyTestPool INSTANCE = new ProxyTestPool();
    }
}

