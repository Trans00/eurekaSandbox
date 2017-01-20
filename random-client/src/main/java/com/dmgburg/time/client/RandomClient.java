package com.dmgburg.time.client;

import com.dmgburg.time.client.spring.RandomClientConfig;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * Created by Denis on 01.01.2017.
 */
public class RandomClient {

    public static void main(String[] args) {
        new SpringApplicationBuilder(RandomClientConfig.class).web(true).run(args);
    }
}
