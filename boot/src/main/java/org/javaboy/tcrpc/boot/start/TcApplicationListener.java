package org.javaboy.tcrpc.boot.start;

import org.javaboy.tcrpc.annotation.TCService;
import org.javaboy.tcrpc.config.ApplicationConfig;
import org.javaboy.tcrpc.config.ProtocolConfig;
import org.javaboy.tcrpc.config.RegistryConfig;
import org.javaboy.tcrpc.registry.DefaultRegistryFactory;
import org.javaboy.tcrpc.registry.RegistryFactory;
import org.javaboy.tcrpc.registry.RegistryService;
import org.javaboy.tcrpc.registry.ServiceInstance;
import org.javaboy.tcrpc.rpc.service.ServiceDescriptor;
import org.javaboy.tcrpc.rpc.service.ServiceManger;
import org.javaboy.tcrpc.rpc.service.ServiceProvider;
import org.javaboy.tcrpc.server.remoting.RpcServer;
import org.javaboy.tcrpc.server.remoting.RpcServerFactory;
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
        // 启动服务器
        startServer();
        // 注册服务
        exportService(context);
    }

    void startServer() {
        ProtocolConfig config = ApplicationConfig.DEFAULT.getProtocolConfig();
        RpcServer server = RpcServerFactory.createServer(config.getIp(), config.getPort());
        server.openServer();
    }

    void exportService(ApplicationContext context) {
        RegistryConfig registryConfig = ApplicationConfig.DEFAULT.getRegistryConfig();
        RegistryService registry = registryFactory.getRegistry(registryConfig);
        List<String> serviceBeanNames = ServiceManger.DEFAULT.getServiceBeanNames();
        serviceBeanNames
                .forEach(beanName -> {
                    Object bean = context.getBean(beanName);
                    Class<?> serviceClass = bean.getClass();
                    TCService serviceAnnotation = serviceClass.getAnnotation(TCService.class);
                    String version = serviceAnnotation.version();
                    Class<?> interfaceClass = serviceAnnotation.interfaceClass();
                    String serviceKey = ServiceKeyHelper.serviceKey(interfaceClass, version);
                    ServiceDescriptor descriptor = new ServiceDescriptor(interfaceClass);
                    ServiceProvider provider = new ServiceProvider(bean);
                    // 服务provider注册
                    ServiceManger.DEFAULT.addServiceProvider(serviceKey, provider);
                    ServiceManger.DEFAULT.addServiceDescriptor(serviceKey, descriptor);

                    ServiceInstance instance = ServiceInstance.builder()
                            .serviceKey(serviceKey)
                            .port(registryConfig.getPort()).ip(NetUtil.getIp())
                            .build();
                    // 注册
                    registry.register(instance);
                });
    }

}
