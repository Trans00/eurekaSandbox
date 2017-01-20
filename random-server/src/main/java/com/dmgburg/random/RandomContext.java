package com.dmgburg.random;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
@Configuration
@ComponentScan(basePackages = "com.dmgburg.random")
@EnableAutoConfiguration
@EnableEurekaClient
public class RandomContext {
}
