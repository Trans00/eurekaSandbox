package com.dmgburg.time.eureka;

import com.dmgburg.time.eureka.EurekaServiceBase;
import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.EurekaInstanceConfig;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.appinfo.providers.EurekaConfigBasedInstanceInfoProvider;
import com.netflix.config.DynamicPropertyFactory;
import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.EurekaClientConfig;

public class Singletons {

    private static ApplicationInfoManager applicationInfoManager;
    private static EurekaClient eurekaClient;
    private static EurekaServiceBase eurekaServiceBase;

    public static synchronized ApplicationInfoManager getApplicationInfoManager(EurekaInstanceConfig instanceConfig) {
        if (applicationInfoManager == null) {
            InstanceInfo instanceInfo = new EurekaConfigBasedInstanceInfoProvider(instanceConfig).get();
            applicationInfoManager = new ApplicationInfoManager(instanceConfig, instanceInfo);
        }

        return applicationInfoManager;
    }

    public static synchronized EurekaClient getEurekaClient(ApplicationInfoManager applicationInfoManager, EurekaClientConfig clientConfig) {
        if (eurekaClient == null) {
            eurekaClient = new DiscoveryClient(applicationInfoManager, clientConfig);
        }

        return eurekaClient;
    }

    public static synchronized EurekaServiceBase getEurekaServiceBase(ApplicationInfoManager applicationInfoManager,
                                                                   EurekaClient eurekaClient){
        if (eurekaServiceBase == null) {
            eurekaServiceBase = new EurekaServiceBase(applicationInfoManager,eurekaClient);
        }

        return eurekaServiceBase;

    }

}
