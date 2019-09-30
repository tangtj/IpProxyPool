package cn.tangtj.proxypool.task;

import cn.tangtj.proxypool.capture.AbstractPeriodProxyCapture;
import cn.tangtj.proxypool.domain.ProxyInfo;
import cn.tangtj.proxypool.domain.ProxyRateInfo;
import cn.tangtj.proxypool.pool.IpProxyStore;
import cn.tangtj.proxypool.pool.ProxyTestPool;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 定期执行,发现当前可用IP低于一定数量时获取新的IP
 *
 * @author tang
 * @date 2019/9/29
 */
@Component
public class CaptureIpTask {

    private static final int TEST_MIN_SIZE = 200;

    private static final int KEEP_MIN_SIZE = 100;

    private final List<AbstractPeriodProxyCapture> proxyCaptures;

    private IpProxyStore ipProxyStore;

    private IpProxyStore keepPool;

    public CaptureIpTask(List<AbstractPeriodProxyCapture> proxyCaptures) {
        this.proxyCaptures = proxyCaptures;
        ipProxyStore = ProxyTestPool.getInstance().getPool();
    }

    //每30分钟执行一次
    @Scheduled(cron = "0 0/30 * * * ?")
    public void capture() {

        //检查当前,ip是否已满足需求.
        if (ipProxyStore.size() > TEST_MIN_SIZE || keepPool.size() > KEEP_MIN_SIZE) {
            return;
        }

        //获取ip并放入的池子中
        for (AbstractPeriodProxyCapture capture : proxyCaptures) {
            //判断是否已过cd
            if (capture.capturePeriodAble()) {
                List<ProxyInfo> proxyInfos = capture.getProxy();
                if (proxyInfos != null && proxyInfos.size() >= 1) {
                    for (ProxyInfo info : proxyInfos) {
                        ProxyRateInfo rateInfo = new ProxyRateInfo(info);
                        ipProxyStore.push(rateInfo);
                    }
                }
            }
        }
    }
}
