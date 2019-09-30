package cn.tangtj.proxypool.proxytest;

/**
 * @author tang
 * @date 2019/9/14
 */
public class BaiduProxyConnectTest extends AbstractProxyConnectTest {

    private static final String TEST_URL = "https://www.baidu.com";

    private BaiduProxyConnectTest(String testUrl) {
        super(testUrl);
    }

    public BaiduProxyConnectTest() {
        this(TEST_URL);
    }
}
