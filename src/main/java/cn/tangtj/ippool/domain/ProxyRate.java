package cn.tangtj.ippool.domain;

/**
 * @author tang
 * @date 2019/9/28
 */
public interface ProxyRate {

    /**
     * 获取当前评分
     *
     * @return
     */
    ProxyRateLevel getRate();

    /**
     * 降低评分
     *
     * @return 降低后的评分
     */
    ProxyRateLevel downRate();

    /**
     * 提高评分
     *
     * @return 提高后的评分
     */
    ProxyRateLevel incrRate();
}
