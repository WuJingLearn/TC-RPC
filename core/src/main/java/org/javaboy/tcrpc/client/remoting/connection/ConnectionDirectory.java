package org.javaboy.tcrpc.client.remoting.connection;

import org.javaboy.tcrpc.config.ApplicationConfig;
import org.javaboy.tcrpc.config.RegistryConfig;
import org.javaboy.tcrpc.loadbalance.LoadBalance;
import org.javaboy.tcrpc.loadbalance.impl.LoadBalanceEnum;
import org.javaboy.tcrpc.loadbalance.impl.RandomLoadbalance;
import org.javaboy.tcrpc.registry.NotifyListener;
import org.javaboy.tcrpc.registry.RegistryFactory;
import org.javaboy.tcrpc.registry.RegistryService;
import org.javaboy.tcrpc.registry.ServiceInstance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author:majin.wj 用于管理一个服务所有连接
 */
public class ConnectionDirectory {

    private List<Connection> avaiableConnections = new ArrayList<>();
    /**
     * Key ip:port
     */
    private Map<String,Connection> connectionMap = new HashMap<>();
    private String serviceKey;
    private Map<String, LoadBalance> loadBalancers;

    private RegistryConfig registryConfig = ApplicationConfig.DEFAULT.getRegistryConfig();
    private RegistryService registry = RegistryFactory.DEFAULT.getRegistry(registryConfig);

    public ConnectionDirectory(String serviceKey) {
        this.serviceKey = serviceKey;
        init();
    }

    void init() {
        List<ServiceInstance> instances = registry.subscribe(serviceKey, new NotifyListener() {
            @Override
            public void notify(List<ServiceInstance> instances) {

            }
        });


    }


    public Connection getConnection() {
        return loadBalancers.get("random").select(avaiableConnections);
    }


    public void initLoadBalancer() {
        loadBalancers.put(LoadBalanceEnum.RANDOM.getCode(), new RandomLoadbalance());
    }


}
