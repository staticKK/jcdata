package com.jointcorp.jcdata.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;


@Configuration
@ConfigurationProperties(prefix = "appConf")
@EnableAsync
public class AppConfig {

    private String queryUserInfoUrl;
    private String dataFilePath;
    private String deviceUrl;

    public String getQueryUserInfoUrl() {
        return queryUserInfoUrl;
    }

    public void setQueryUserInfoUrl(String queryUserInfoUrl) {
        this.queryUserInfoUrl = queryUserInfoUrl;
    }

    public String getDataFilePath() {
        return dataFilePath;
    }

    public void setDataFilePath(String dataFilePath) {

        this.dataFilePath = dataFilePath;
    }

    public String getDeviceUrl() {
        return deviceUrl;
    }

    public void setDeviceUrl(String deviceUrl) {
        this.deviceUrl = deviceUrl;
    }

    @Bean
    public Executor iokExecutor() {

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(30);
        executor.setMaxPoolSize(200);
        executor.setQueueCapacity(30);
        executor.setKeepAliveSeconds(20);
        executor.setThreadNamePrefix("ioExecutor-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }
}
