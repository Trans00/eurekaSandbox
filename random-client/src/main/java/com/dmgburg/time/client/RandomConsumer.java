package com.dmgburg.time.client;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class RandomConsumer {
    @Autowired
    private EurekaClient eurekaClient;
    private volatile String url;
    private HttpClient client = new DefaultHttpClient();
    private final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH-mm-ss");
    private final static Log log = LogFactory.getLog(RandomConsumer.class);


    public void init() {
        InstanceInfo instanceInfo = eurekaClient.getNextServerFromEureka("com.dmgburg.random", false);
        url = instanceInfo.getHomePageUrl();
    }

    public Date getDate() throws IOException {
        HttpGet request = new HttpGet(url + "/random");
        HttpResponse response = client.execute(request);
        BufferedReader rd = new BufferedReader (new InputStreamReader(response.getEntity().getContent()));
        String line = rd.readLine();

        try {
            return dateFormat.parse(line);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String toString() {
        return url;
    }

    @PostConstruct
    public void run() {
        init();
        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1);
        executorService.scheduleAtFixedRate(() -> {
            try {
                log.info(toString() + " : " +getDate());
            } catch (IOException e) {
                log.error("Failed to query date for " + toString(), e);
                init();
            }
        },0,1000, TimeUnit.MILLISECONDS);
    }
}
