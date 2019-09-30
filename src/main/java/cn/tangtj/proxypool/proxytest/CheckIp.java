package cn.tangtj.proxypool.proxytest;

import cn.tangtj.proxypool.exception.RequestException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

/**
 * @author tang
 * @date 2019/9/14
 */
public class CheckIp implements ProxyConnectTest {

    private String checkUrl = "https://www.baidu.com";

    private Request request = new Request.Builder().url(checkUrl).get().build();

    @Override
    public boolean test(OkHttpClient client, String ip) throws RequestException {
        Response response;

        ResponseBody body = null;

        try {
            response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                throw new RequestException();
            }
            body = response.body();
            if (body == null) {
                throw new RequestException();
            }
            String ipStr = body.string();
            ipStr = StringUtils.substringBeforeLast(ipStr, StringUtils.LF);
            return StringUtils.equals(ip, ipStr);
        } catch (IOException e) {
            throw new RequestException();
        } finally {
            if (body != null) {
                body.close();
            }
        }
    }
}
