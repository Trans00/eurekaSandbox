package com.dmgburg.time.client.spring;

import com.dmgburg.time.client.TimeConsumer;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.dmgburg.time.client")
@EnableAutoConfiguration
@EnableEurekaClient
public class TimeClientConfig {

    @Bean
    public TimeConsumer timeConsumer(){
        return new TimeConsumer();
    }
}
