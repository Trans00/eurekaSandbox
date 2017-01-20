package com.dmgburg.time.client;

import com.dmgburg.time.client.spring.TimeClientConfig;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

/**
 * Created by Denis on 01.01.2017.
 */
public class TimeClient {

    public static void main(String[] args) {
        ApplicationContext context = new SpringApplicationBuilder(TimeClientConfig.class).web(false).run(args);
        context.getBean(TimeConsumer.class);
    }
}
