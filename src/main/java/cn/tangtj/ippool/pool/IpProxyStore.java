package cn.tangtj.ippool.pool;

import cn.tangtj.ippool.domain.ProxyRateInfo;

import java.util.*;

/**
 * @author tang
 * @date 2019/9/28
 */
public class IpProxyStore {

    private final Set<Integer> ipSet = new HashSet<>();
    private LinkedList<ProxyRateInfo> store = new LinkedList<>();


    private final Object lockObj = new Object();

    IpProxyStore() {

    }

    public int size() {
        synchronized (lockObj) {
            return store.size();
        }
    }

    public ProxyRateInfo poll() {
        synchronized (lockObj) {
            ProxyRateInfo info = store.poll();
            if (info != null) {
                ipSet.remove(info.hashCode());
            }
            return info;
        }
    }

    public boolean push(ProxyRateInfo info) {
        if (info == null) {
            return false;
        }
        synchronized (lockObj) {
            int hashcode = info.hashCode();
            if (ipSet.contains(hashcode)) {
                return true;
            }
            ipSet.add(hashcode);
            return store.add(info);
        }
    }

    public List<ProxyRateInfo> gets(int size) {
        synchronized (lockObj) {
            int s = Math.min(size, store.size());
            ArrayList<ProxyRateInfo> list = new ArrayList<>(s + 1);

            for (int i = 0; i < s; i++) {
                list.add(poll());
            }
            return list;
        }
    }

    public List<ProxyRateInfo> getAll() {
        return gets(store.size());
    }

    public boolean contains(ProxyRateInfo info) {
        if (info == null) {
            return false;
        }
        synchronized (lockObj) {
            return ipSet.contains(info.hashCode());
        }
    }
}
