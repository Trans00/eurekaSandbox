package com.dmgburg.time.eureka;

import com.dmgburg.time.ApplicationContext;
import com.dmgburg.time.WebConfig;
import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.MyDataCenterInstanceConfig;
import com.netflix.config.ConfigurationManager;
import com.netflix.discovery.DefaultEurekaClientConfig;
import com.netflix.discovery.EurekaClient;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static com.dmgburg.time.eureka.Singletons.*;

public class TimeServer {
    private final static Log log = LogFactory.getLog(TimeServer.class);

    public static void main(String[] args) throws Exception {
        new TimeServer().run();
    }

    private void run() throws Exception {
        Properties properties = new Properties();
        InputStream stream = TimeServer.class.getResourceAsStream("/WEB-INF/application.properties");
        properties.load(stream);
        Server server = initJettyServer(properties);
        EurekaServiceBase eurekaServiceBase = null;


        try {
            server.start();
            properties.put("server.port", ((ServerConnector)server.getConnectors()[0]).getLocalPort());
            ConfigurationManager.loadProperties(properties);
            ApplicationInfoManager applicationInfoManager = getApplicationInfoManager(new MyDataCenterInstanceConfig());
            EurekaClient eurekaClient = getEurekaClient(applicationInfoManager, new DefaultEurekaClientConfig());
            eurekaServiceBase = getEurekaServiceBase(applicationInfoManager,eurekaClient);
            int port = ((ServerConnector) server.getConnectors()[0]).getLocalPort();
            log.info("Time server started on port: " + port);
            eurekaServiceBase.start();
            server.join();
        } finally {
            server.stop();
            eurekaServiceBase.stop();
        }
    }

    private static Server initJettyServer(Properties properties) throws IOException {

        AnnotationConfigWebApplicationContext webContext = new AnnotationConfigWebApplicationContext();
        webContext.register(WebConfig.class);
        webContext.getEnvironment().getPropertySources().addLast(new PropertiesPropertySource("applicationEnvironment", properties));

        ServletHolder servletHolder = new ServletHolder("time-dispatcher", new DispatcherServlet(webContext));
        servletHolder.setAsyncSupported(true);
        servletHolder.setInitOrder(1);

        WebAppContext webAppContext = new WebAppContext();
        webAppContext.setInitParameter("contextConfigLocation", ApplicationContext.class.getName());
        webAppContext.setResourceBase("resource");
        webAppContext.addEventListener(new ContextLoaderListener(webContext));
        webAppContext.setContextPath("/");
        webAppContext.addServlet(servletHolder, "/");

        Server server = new Server(0);
        server.setHandler(webAppContext);
        return server;
    }
}
