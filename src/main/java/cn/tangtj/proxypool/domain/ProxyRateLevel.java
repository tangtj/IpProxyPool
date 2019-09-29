package cn.tangtj.proxypool.domain;

public enum ProxyRateLevel {

    A(5),
    B(4),
    C(3),
    D(2),
    E(1);

    private final int rate;

    ProxyRateLevel(int rate) {
        this.rate = rate;
    }

    public static ProxyRateLevel of(int rate) {
        if (rate <= 1) {
            return E;
        } else if (rate >= 5) {
            return A;
        }
        for (ProxyRateLevel level : ProxyRateLevel.values()) {
            if (rate == level.rate) {
                return level;
            }
        }
        return null;
    }

    public ProxyRateLevel incrRate() {
        return ProxyRateLevel.of(this.rate + 1);
    }

    public ProxyRateLevel downRate() {
        return ProxyRateLevel.of(this.rate - 1);
    }
}
