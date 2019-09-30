package cn.tangtj.proxypool.task;

import cn.tangtj.proxypool.domain.ProxyRateInfo;
import cn.tangtj.proxypool.domain.ProxyRateLevel;
import cn.tangtj.proxypool.exception.RequestException;
import cn.tangtj.proxypool.pool.HttpClientPool;
import cn.tangtj.proxypool.pool.IpProxyStore;
import cn.tangtj.proxypool.pool.ProxyKeepPool;
import cn.tangtj.proxypool.pool.ProxyTestPool;
import cn.tangtj.proxypool.proxytest.BaiduProxyConnectTest;
import cn.tangtj.proxypool.proxytest.ProxyConnectTest;
import cn.tangtj.proxypool.util.TimeUtils;
import okhttp3.OkHttpClient;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author tang
 * @date 2019/9/29
 */
@Component
public class ProxyKeepTask {

    private final IpProxyStore testPool;

    private final IpProxyStore keepPool;

    private ProxyConnectTest connectTest = new BaiduProxyConnectTest();

    private HttpClientPool clientPool;

    public ProxyKeepTask() {
        testPool = ProxyTestPool.getInstance().getPool();
        keepPool = ProxyKeepPool.getInstance().getPool();
        clientPool = HttpClientPool.getInstance();
    }

    @Scheduled(cron = "0 0/5 * * * ?")
    public void test() {
        if (keepPool.size() == 0) {
            return;
        }
        List<ProxyRateInfo> rateInfos = keepPool.getAll();
        for (ProxyRateInfo rateInfo : rateInfos) {
            OkHttpClient client = clientPool.get(rateInfo.getIp(), rateInfo.getPort());
            boolean connectAble = false;
            try {
                System.out.printf(Thread.currentThread().getName() + " 正在检测ip:%s\n", rateInfo.getIp());
                connectTest.test(client, rateInfo.getIp());
                connectAble = true;
            } catch (RequestException e) {
                //请求失败,不通过
            }
            rateInfo.setLastTestTime(TimeUtils.currentTimeSecond());
            if (connectAble) {
                //加分,通过保留
                keepPool.push(rateInfo);
            } else {
                //减分
                rateInfo.setRate(ProxyRateLevel.C);
                testPool.push(rateInfo);
                System.out.printf(Thread.currentThread().getName() + " 检测ip:%s,不通过,降低评分,%s\n", rateInfo.getIp(), rateInfo.getRate());
            }
        }

    }
}
