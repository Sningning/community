package sningning.community;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import sningning.community.service.AlphaService;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author: Song Ningning
 * @date: 2020-08-24 22:46
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class ThreadPoolTests {

    public static final Logger LOGGER = LoggerFactory.getLogger(ThreadPoolTests.class);

    @Autowired
    private AlphaService alphaService;

    // JDK 普通线程池
    private ExecutorService executorService = Executors.newFixedThreadPool(5);

    // JDK 可执行定时任务的线程池
    private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);

    // Spring 普通线程池
    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    // Spring 可执行定时任务的线程池
    @Autowired
    private ThreadPoolTaskScheduler taskScheduler;

    public void sleep(long m) {
        try {
            Thread.sleep(m);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // 1.测试JDK测试普通线程池
    @Test
    public void testExecutorService() {
        Runnable task = new Runnable() {
            @Override
            public void run() {
                LOGGER.debug("Hello ExecutorService");
            }
        };
        for (int i = 0; i < 10; i++) {
            executorService.submit(task);
        }

        sleep(10000);
    }

    // 2.测试JDK可执行定时任务的线程池
    @Test
    public void testScheduledExecutorService() {
        Runnable task = new Runnable() {
            @Override
            public void run() {
                LOGGER.debug("Hello ExecutorService");
            }
        };
        scheduledExecutorService.scheduleAtFixedRate(task, 5, 2, TimeUnit.SECONDS);

        sleep(30000);
    }

    // 3.测试Spring普通线程池
    @Test
    public void testThreadPoolTaskExecutor() {
        Runnable task = () -> LOGGER.debug("Hello ThreadPoolTaskExecutor");
        for (int i = 0; i < 10; i++) {
            taskExecutor.submit(task);
        }
        sleep(10000);
    }

    // 4.测试Spring可执行定时任务的线程池
    @Test
    public void testThreadPoolTaskScheduler() {
        Runnable task = () -> LOGGER.debug("Hello ThreadPoolTaskScheduler");
        Date startTime = new Date(System.currentTimeMillis() + 10000);
        taskScheduler.scheduleAtFixedRate(task, startTime, 1000);

        sleep(30000);
    }

    // 5.Spring普通线程池简化写法
    @Test
    public void testThreadPoolTaskExecutorSimple() {
        for (int i = 0; i < 10; i++) {
            alphaService.execute1();
        }
        sleep(10000);
    }

    // 5.Spring定时任务线程池简化写法
    @Test
    public void testThreadPoolTaskSchedulerSimple() {
        sleep(10000);
    }
}
