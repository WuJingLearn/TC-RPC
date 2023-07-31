package org.javaboy.tcrpc.boot.start;

import org.javaboy.tcrpc.annotation.TCService;
import org.javaboy.tcrpc.config.ApplicationConfig;
import org.javaboy.tcrpc.config.RegistryConfig;
import org.javaboy.tcrpc.registry.DefaultRegistryFactory;
import org.javaboy.tcrpc.registry.RegistryFactory;
import org.javaboy.tcrpc.registry.RegistryService;
import org.javaboy.tcrpc.registry.ServiceInstance;
import org.javaboy.tcrpc.rpc.config.ServiceManger;
import org.javaboy.tcrpc.util.NetUtil;
import org.javaboy.tcrpc.util.ServiceKeyHelper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ApplicationContextEvent;

import java.util.List;

/**
 * @author:majin.wj
 */
public class TcApplicationListener implements ApplicationListener<ApplicationContextEvent> {

    private RegistryFactory registryFactory = new DefaultRegistryFactory();

    @Override
    public void onApplicationEvent(ApplicationContextEvent event) {
        ApplicationContext context = event.getApplicationContext();

    }

    void startServer() {

    }

    void exportService(ApplicationContext context) {
        RegistryConfig registryConfig = ApplicationConfig.DEFAULT.getRegistryConfig();
        RegistryService registry = registryFactory.getRegistry(registryConfig);
        List<String> serviceBeanNames = ServiceManger.DEFAULT.getServiceBeanNames();
        for (String beanName : serviceBeanNames) {
            Object bean = context.getBean(beanName);
            Class<?> serviceClass = bean.getClass();
            TCService serviceAnnotation = serviceClass.getAnnotation(TCService.class);
            String version = serviceAnnotation.version();
            Class<?> interfaceClass = serviceAnnotation.interfaceClass();
            ServiceInstance instance = ServiceInstance.builder()
                    .serviceKey(ServiceKeyHelper.serviceKey(interfaceClass, version))
                    .port(registryConfig.getPort()).ip(NetUtil.getIp())
                    .build();
            // 注册
            registry.register(instance);
        }
    }

}
