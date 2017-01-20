package com.dmgburg.eureka;

import org.springframework.boot.builder.SpringApplicationBuilder;

public class EurekaServer {

    public static void main(String[] args) {
        new SpringApplicationBuilder(EurekaConfig.class).web(true).run(args);
    }

}
