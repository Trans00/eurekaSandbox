package com.dmgburg.random.eureka;

import com.dmgburg.random.RandomContext;
import org.springframework.boot.builder.SpringApplicationBuilder;

public class RandomServer {
    public static void main(String[] args) throws Exception {
        new SpringApplicationBuilder(RandomContext.class).web(true).run(args);
    }

}
