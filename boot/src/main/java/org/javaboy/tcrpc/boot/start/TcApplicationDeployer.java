package org.javaboy.tcrpc.boot.start;

import org.javaboy.tcrpc.annotation.TCService;
import org.javaboy.tcrpc.config.ApplicationConfig;
import org.javaboy.tcrpc.config.ProtocolConfig;
import org.javaboy.tcrpc.config.RegistryConfig;
import org.javaboy.tcrpc.registry.RegistryFactory;
import org.javaboy.tcrpc.registry.RegistryService;
import org.javaboy.tcrpc.registry.ServiceInstance;
import org.javaboy.tcrpc.rpc.service.ServiceManger;
import org.javaboy.tcrpc.rpc.service.ServiceProvider;
import org.javaboy.tcrpc.server.remoting.RpcServer;
import org.javaboy.tcrpc.server.remoting.RpcServerFactory;
import org.javaboy.tcrpc.util.ServiceKeyHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.util.List;

/**
 * @author:majin.wj 应用部署器，在TcApplicationLister Bean创建时创建。
 */
public class TcApplicationDeployer {

    private static final Logger LOG = LoggerFactory.getLogger(TcApplicationDeployer.class);

    private RegistryFactory registryFactory = RegistryFactory.DEFAULT;
    private ServiceManger serviceManger = ServiceManger.DEFAULT;

    /**
     * 全局应用配置
     */
    private ApplicationConfig applicationConfig;
    private ProtocolConfig protocolConfig;
    private RegistryConfig registryConfig;
    private RegistryService registryService;

    private RpcServer server;

    public TcApplicationDeployer() {
        this.init();
    }

    private void init() {
        this.applicationConfig = ApplicationConfig.DEFAULT;
        this.protocolConfig = applicationConfig.getProtocolConfig();
        this.registryConfig = ApplicationConfig.DEFAULT.getRegistryConfig();
        this.registryService = registryFactory.getRegistry(registryConfig);
        this.server = RpcServerFactory.createServer(protocolConfig.getIp(), protocolConfig.getPort());
    }

    public void start(ApplicationContext context) {
        startServer();
        exportService(context);
    }


    public void close() {
        closeServer();
        deRegisterServices();
    }

    void startServer() {
        try {
            server.openServer();
        } catch (Exception e) {
            LOG.error("RpcServer start failed,bind address is {}", server.getAddress(), e);
        }
    }

    void exportService(ApplicationContext context) {
        List<String> serviceBeanNames = serviceManger.getServiceBeanNames();
        // 1.注册服务
        // 2.服务保存至ServiceManager
        serviceBeanNames.forEach(beanName -> {
            Object bean = context.getBean(beanName);
            Class<?> serviceClass = bean.getClass();
            TCService serviceAnnotation = serviceClass.getAnnotation(TCService.class);
            String version = serviceAnnotation.version();
            Class<?> interfaceClass = serviceAnnotation.interfaceClass();
            String serviceKey = ServiceKeyHelper.serviceKey(interfaceClass, version);
            ServiceProvider provider = new ServiceProvider(bean, interfaceClass, serviceKey);
            // 服务provider注册
            ServiceManger.DEFAULT.addServiceProvider(serviceKey, provider);
            ServiceInstance instance = ServiceInstance.builder().serviceKey(serviceKey).port(protocolConfig.getPort()).ip(protocolConfig.getIp()).build();
            // 注册
            registryService.register(instance);
            ServiceManger.DEFAULT.addServiceInstanve(instance);
        });
    }

    private void closeServer() {
        try {
            server.shutDown();
            LOG.info("RpcServer close succeed");
        } catch (Exception e) {
            LOG.error("RpcServer close error", e);
        }
    }

    private void deRegisterServices() {
        List<ServiceInstance> serviceInstances = serviceManger.getServiceInstances();
        LOG.info("begin 销毁服务:"+serviceInstances);
        for (ServiceInstance serviceInstance : serviceInstances) {
            try {
                registryService.unRegister(serviceInstance);
                LOG.info("service:{} deregister success", serviceInstance.getServiceKey());
            } catch (Exception e) {
                LOG.error("service:{} deregister error", serviceInstance.getServiceKey(), e);
            }
        }
    }


}
