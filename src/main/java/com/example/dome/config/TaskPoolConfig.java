package com.example.dome.config;

import org.apache.tomcat.util.threads.ThreadPoolExecutor;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * \* @author wcy
 * \* @date: 2020-06-05 14:42
 * \* Description:  类
 * \
 */

@Configuration
@EnableAsync
public class TaskPoolConfig implements AsyncConfigurer {
    /**
     * 设置线程池核心容量
     * 核心线程会一直存活,即使没有任务需要处理.当线程数小于核心线程数时,
     * 即使现有的线程空闲,线程池也会优先创建新线程来处理任务,而不是直接交
     * 给现有的线程处理;核心线程在allowCoreThreadTimeout被设置为true时
     * 会超时退出,默认情况下不会退出..
     */
    @Value("${task.execution.pool.core-size}")
    private int corePoolSize;

    /**
     *  当线程数大于或等于核心线程,且任务队列已满时,线程池会创建新的线程,
     *  直到线程数量达到maxPoolSize.如果线程数已等于maxPoolSize,且任务
     *  队列已满,则已超出线程池的处理能力,线程池会拒绝处理任务而抛出异常;
     */
    @Value("${task.execution.pool.max-size}")
    private int maxPoolSize;

    /**
     * 任务队列容量.从maxPoolSize的描述上可以看出,任务队列的容量会影响到线
     * 程的变化,因此任务队列的长度也需要恰当的设置.我们中给了10000,相当于就
     * 是没有上限了....
     */
    @Value("${task.execution.pool.queue-capacity}")
    private int queueCapacity;

    /**
     * 当线程空闲时间达到keepAliveTime,该线程会退出,直到线程数量等于corePoolSize,
     * 如果allowCoreThreadTimeout设置为true,则所有线程均会退出直到线程数量为0.
     */
    @Value("${task.execution.pool.keep-alive}")
    private int keepAliveSeconds;

    /**
     * 是否允许核心线程空闲退出,默认值为false.一般就使用false的
     */
    @Value("${task.execution.pool.allow-core-thread-timeout}")
    private boolean allowCoreThreadTimeout;

    /**
     * 设置线程池 前缀
     */
    @Value("${task.execution.pool.thread-name-prefix}")
    private String threadNamePrefix;
    /**
     * 在配置了spring线程池的情况下，如果某时刻要停止应用，如果没有优雅停机，\
     * 存在于线程池中的任务将会被强制停止，导致部分任务失败
     * WaitForTasksToCompleteOnShutdown=true（默认为false），表明等待所有线程执行完
     */
    @Value("${task.execution.pool.wait-for}")
    private boolean waitForTasks;

    /**在配置了spring线程池的情况下，如果某时刻要停止应用，如果没有优雅停机，
     * 存在于线程池中的任务将会被强制停止，导致部分任务失败
     * AwaitTerminationSeconds=xx（默认为0，此时立即停止），并没等待xx秒后强制停止
     */
    @Value("${task.execution.pool.await-termination-seconds}")
    private int awaitTerminationSeconds;


    @Override
    @Bean("taskExecutor")
    public Executor getAsyncExecutor() {

        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        // 设置线程池核心容量
        taskExecutor.setCorePoolSize(corePoolSize);
        // 设置线程池最大容量
        taskExecutor.setMaxPoolSize(maxPoolSize);
        // 设置任务队列长度
        taskExecutor.setQueueCapacity(queueCapacity);
        // 设置线程超时时间
        taskExecutor.setKeepAliveSeconds(keepAliveSeconds);
        // 设置线程名称前缀
        taskExecutor.setThreadNamePrefix(threadNamePrefix);
        // 是否允许核心线程空闲退出,默认值为false.一般就使用false的
        taskExecutor.setAllowCoreThreadTimeOut(allowCoreThreadTimeout);
        // 调度器shutdown被调用时等待当前被调度的任务完成
        taskExecutor.setWaitForTasksToCompleteOnShutdown(waitForTasks);
        // （默认为0，此时立即停止），并没等待xx秒后强制停止
        taskExecutor.setAwaitTerminationSeconds(awaitTerminationSeconds);
        // 线程池对拒绝任务（无线程可用）的处理策略，目前只支持AbortPolicy、CallerRunsPolicy；默认为后者
        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return taskExecutor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new SimpleAsyncUncaughtExceptionHandler();
    }
}