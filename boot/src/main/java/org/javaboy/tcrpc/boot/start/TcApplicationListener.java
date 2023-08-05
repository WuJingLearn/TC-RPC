package org.javaboy.tcrpc.boot.start;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * @author:majin.wj
 */
public class TcApplicationListener implements ApplicationListener<ApplicationContextEvent> {

    private static final Logger LOG = LoggerFactory.getLogger(TcApplicationListener.class);

    public TcApplicationDeployer deployer = new TcApplicationDeployer();

    @Override
    public void onApplicationEvent(ApplicationContextEvent event) {
        LOG.info("TcApplicationListener receive spring context event.{}", event.getClass());
        if (event instanceof ContextRefreshedEvent) {
            deployer.start(event.getApplicationContext());
        } else if (event instanceof ContextClosedEvent) {
            deployer.close();
        }

    }
}
