package cn.tangtj.ippool.capture.impl;

import cn.tangtj.ippool.capture.AbstractPeriodProxyCapture;
import cn.tangtj.ippool.domain.ProxyInfo;
import cn.tangtj.ippool.domain.ProxyRateInfo;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author tang
 * @date 2019/9/28
 */
@Component
public class XiciPeriodProxyCapture extends AbstractPeriodProxyCapture {

    /**
     * 时间间隔,100秒
     */
    private static final long TIME_PERIOD = 200;

    private static final int IP_INDEX = 1;

    private static final int PORT_INDEX = 2;

    private static final int HTTP_TYPE_INDEX = 5;

    public XiciPeriodProxyCapture() {
        this(TIME_PERIOD);
    }

    private XiciPeriodProxyCapture(long capturePeriod) {
        super(capturePeriod);
    }

    @Override
    public List<ProxyInfo> getProxy() {

        Document document;

        lastCapture = Instant.now();
        try {
            document = Jsoup.connect("https://www.xicidaili.com/nt/").get();
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.EMPTY_LIST;
        }
        if (document == null) {
            return Collections.EMPTY_LIST;
        }

        Element element = document.selectFirst("#ip_list");
        Elements elements = element.select("tr[class],tr[class=odd]");
        List<ProxyInfo> proxyInfos = new LinkedList<>();
        elements.forEach(e -> {
            Elements es = e.select("td");
            String ip = es.get(IP_INDEX).text();
            int port = Integer.parseInt(es.get(PORT_INDEX).text());
            ProxyInfo info = new ProxyRateInfo(ip, port);
            proxyInfos.add(info);
        });
        return proxyInfos;
    }

    @Override
    public String getId() {
        return null;
    }
}
