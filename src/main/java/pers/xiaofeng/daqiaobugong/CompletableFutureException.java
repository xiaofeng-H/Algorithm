package pers.xiaofeng.daqiaobugong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @className: pers.xiaofeng.daqiaobugong.CompletableFutureException
 * @description:
 * @author: xiaofeng
 * @create: 2021-01-21 15:19
 */
public class CompletableFutureException {
    public static void handleResult(String result) {
        System.out.println("============result: " + result + "=============");
    }

    public static void main(String[] args) {
        Logger log = LoggerFactory.getLogger(CompletableFutureException.class);

        log.info("main: started");

        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            log.info("async: started");
            try {
                Thread.sleep(10 * 1000);
            } catch (InterruptedException e) {
                log.info("async: interrupted");
                return "default interrupted result";
            }

            boolean exceptional = false;
            if (exceptional) {
                log.info("async: run into exception");
                throw new RuntimeException("async exception");
            } else {
                log.info("async: finished");
                return "hello";
            }
        });

        // attention: split the line, because the cancel operation is against the above "future"
        future.handle((result, throwable) -> {
            log.info("async: result: " + result + ", throwable: " + throwable);
            if (throwable != null) {
                log.info("async: got exception from async: " + throwable.getClass() + ", " + throwable.getCause());
                handleResult("default exception result");
                return "default exception result";
            } else {
                log.info("got normal result: " + result);
                handleResult(result);
                return result;
            }
        });

//        try {
//            Thread.sleep(2 * 1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        log.info("default cancel value");
//        future.cancel(true);
//        log.info("main: async cancelled");

        try {
            Thread.sleep(15 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        log.info("main ended");
    }
}
