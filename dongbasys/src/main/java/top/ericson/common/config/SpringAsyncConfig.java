package top.ericson.common.config;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;

import lombok.Setter;

// @Slf4j
@Setter
@Configuration
@ConfigurationProperties("async-thread-pool")
public class SpringAsyncConfig implements AsyncConfigurer {
    /**核心线程数*/
    private int corePoolSize = 3;
    /**最大线程数*/
    private int maximumPoolSize = 5;
    /**线程空闲时间*/
    private int keepAliveTime = 30;
    /**阻塞队列容量*/
    // private int queueCapacity = 200;
    /**构建线程工厂*/
    private ThreadFactory threadFactory = new ThreadFactory() {
        // CAS算法
        private AtomicInteger at = new AtomicInteger(1000);

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "db-async-thread-" + at.getAndIncrement());
        }
    };

    
    @Bean("asyncPoolExecutor")
    public ThreadPoolExecutor newThreadPoolExecutor() {
        /**创建队列*/
        BlockingQueue<Runnable> workQueue=new ArrayBlockingQueue<>(10);
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
            corePoolSize, 
            maximumPoolSize, 
            keepAliveTime, 
            TimeUnit.SECONDS, 
            workQueue, 
            threadFactory);
        return threadPoolExecutor;
    }
    
    /*@Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maximumPoolSize);
        executor.setKeepAliveSeconds(keepAliveTime);
        executor.setQueueCapacity(queueCapacity);
        executor.setRejectedExecutionHandler((Runnable r,
            ThreadPoolExecutor exe) -> {
            log.warn("当前任务线程池队列已满.");
        });
        executor.initialize();
        return executor;
    }
    
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new AsyncUncaughtExceptionHandler() {
            @Override
            public void handleUncaughtException(Throwable ex,
                Method method,
                Object... params) {
                log.error("线程池执行任务发生未知异常.", ex);
            }
        };
    }*/
}
