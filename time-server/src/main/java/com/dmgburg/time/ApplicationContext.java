package com.dmgburg.time;

import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.config.DynamicPropertyFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@ComponentScan("com.dmgburg.time")
public class ApplicationContext {

    @Bean
    public static PropertySourcesPlaceholderConfigurer appProperty() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
