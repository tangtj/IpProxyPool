package cn.tangtj.ippool;

import cn.tangtj.ippool.capture.impl.XiciPeriodProxyCapture;
import cn.tangtj.ippool.domain.ProxyInfo;
import cn.tangtj.ippool.pool.HttpClientPool;
import cn.tangtj.ippool.proxytest.ProxyConnectTest;
import okhttp3.OkHttpClient;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author tang
 * @date 2019/9/28
 */
public class Task {

    private HttpClientPool httpClientPool = HttpClientPool.getInstance();

    private XiciPeriodProxyCapture xiciPeriodProxyCapture = new XiciPeriodProxyCapture();

    private ProxyConnectTest connectTest = new CheckIp();

    @PostConstruct
    @Scheduled(fixedDelay = 50 * 1000)
    public void test() {
        List<ProxyInfo> proxyInfos = xiciPeriodProxyCapture.getProxy();

        proxyInfos.forEach(i -> {
            OkHttpClient client = httpClientPool.get(i.getIp(), i.getPort());

            long start = System.currentTimeMillis();
            try {
                connectTest.test(client, i.getIp());
            } catch (Exception e) {
                //System.out.println("对 "+i+" 进行有效性验证,"+"验证 不通过,耗时:"+(System.currentTimeMillis() - start)/1000);
                return;
            }
            System.out.println("对 " + i + " 进行有效性验证," + "验证 通过,耗时:" + (System.currentTimeMillis() - start) / 1000);
        });
    }
}
