package cn.tangtj.ippool.domain;

/**
 * @author tang
 * @date 2019/9/28
 */
public abstract class AbstractProxyInfo implements ProxyInfo {

    protected final int port;

    protected final String ip;

    public AbstractProxyInfo(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    @Override
    public int getPort() {
        return port;
    }

    @Override
    public String getIp() {
        return ip;
    }

    @Override
    public String toString() {
        return "AbstractProxyInfo{" +
                "port=" + port +
                ", ip='" + ip + '\'' +
                '}';
    }
}
