package cn.tangtj.proxypool;

import cn.tangtj.proxypool.proxytest.CheckIp;
import okhttp3.OkHttpClient;
import org.junit.Test;

public class CheckIpTest {

    @Test
    public void check() {
        OkHttpClient okHttpClient = new OkHttpClient();
        CheckIp checkIp = new CheckIp();

        //checkIp.check(okHttpClient,"123123");

    }
}