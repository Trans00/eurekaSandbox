package com.dmgburg.time.eureka;

import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.thoughtworks.xstream.InitializationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Singleton;

@Singleton
public class EurekaServiceBase {
    private final ApplicationInfoManager applicationInfoManager;
    private final EurekaClient eurekaClient;
    private final static Log log = LogFactory.getLog(TimeServer.class);

    public EurekaServiceBase(ApplicationInfoManager applicationInfoManager,
                             EurekaClient eurekaClient) {
        this.applicationInfoManager = applicationInfoManager;
        this.eurekaClient = eurekaClient;
    }

    @PostConstruct
    public void start() {
        // A good practice is to register as STARTING and only change status to UP
        // after the service is ready to receive traffic
        log.info("Registering service to eureka with STARTING status");
        applicationInfoManager.setInstanceStatus(InstanceInfo.InstanceStatus.STARTING);

        log.info("Simulating service initialization by sleeping for 2 seconds...");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            // Nothing
        }

        // Now we change our status to UP
        log.info("Done sleeping, now changing status to UP");
        applicationInfoManager.setInstanceStatus(InstanceInfo.InstanceStatus.UP);
        waitForRegistrationWithEureka(eurekaClient);
        log.info("Service started and ready to process requests..");
    }

    @PreDestroy
    public void stop() {
        if (eurekaClient != null) {
            log.info("Shutting down server. Demo over.");
            eurekaClient.shutdown();
        }
    }

    private void waitForRegistrationWithEureka(EurekaClient eurekaClient) {
        // my vip address to listen on
        String vipAddress = applicationInfoManager.getInfo().getInstanceId();
        InstanceInfo nextServerInfo = null;
        int tryIndex = 0;
        while (nextServerInfo == null) {
            try {
                nextServerInfo = (InstanceInfo) eurekaClient.getInstancesById(vipAddress).get(0);
                log.info("Registered instance with address: " + nextServerInfo.getHomePageUrl());
            } catch (Throwable e) {
                if (tryIndex++ < 3) {
                    log.info("Waiting ... verifying service registration with eureka ...");

                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                } else {
                    throw new InitializationException("Failed to register in Eureca:", e);
                }
            }
        }
    }

}
