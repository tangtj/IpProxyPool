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

    private final int minSize = 200;

    private final List<AbstractPeriodProxyCapture> proxyCaptures;

    private IpProxyStore ipProxyStore;

    public CaptureIpTask(List<AbstractPeriodProxyCapture> proxyCaptures) {
        this.proxyCaptures = proxyCaptures;
        ipProxyStore = ProxyTestPool.getInstance().getPool();
    }

    @Scheduled(cron = "0 0/1 * * * ?")
    public void capture() {

        if (ipProxyStore.size() > minSize) {
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
