package com.dmgburg.time.eureka;

import com.dmgburg.time.EurekaApplicationContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.builder.SpringApplicationBuilder;

public class EmbeddedEureka {
    private final static Log log = LogFactory.getLog(EmbeddedEureka.class);

    public static void main(String[] args) throws Exception {
        new EmbeddedEureka().run();
    }

    private void run() throws Exception {
        new SpringApplicationBuilder(EurekaApplicationContext.class).bannerMode(Banner.Mode.OFF).web(true).run();
    }
}
