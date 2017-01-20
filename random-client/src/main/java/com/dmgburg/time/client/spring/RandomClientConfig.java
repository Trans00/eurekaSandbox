package com.dmgburg.time.client.spring;

import com.dmgburg.time.client.RandomConsumer;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.dmgburg.time.client")
@EnableAutoConfiguration
@EnableEurekaClient
public class RandomClientConfig {

    @Bean
    public RandomConsumer timeConsumer(){
        return new RandomConsumer();
    }
}
