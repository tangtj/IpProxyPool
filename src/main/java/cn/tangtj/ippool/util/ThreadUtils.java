package cn.tangtj.ippool.util;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author tang
 * @date 2019/9/5
 */
public class ThreadUtils {

    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 50, 10 * 1000, TimeUnit.MILLISECONDS, new SynchronousQueue<>(), new ThreadFactoryBuilder().setNameFormat("线程池-%d ").build());

    public static void sleep(int millis, boolean withException) throws InterruptedException {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            if (withException) {
                throw e;
            }
        }
    }

    public static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignored) {
        }
    }

    public static void execute(Runnable runnable) {
        executor.execute(runnable);
    }
}