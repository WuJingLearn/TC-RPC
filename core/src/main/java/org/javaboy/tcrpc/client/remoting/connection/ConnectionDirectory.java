package org.javaboy.tcrpc.client.remoting.connection;

import com.alibaba.fastjson.JSON;
import org.javaboy.tcrpc.config.ApplicationConfig;
import org.javaboy.tcrpc.config.RegistryConfig;
import org.javaboy.tcrpc.loadbalance.LoadBalance;
import org.javaboy.tcrpc.loadbalance.impl.LoadBalanceEnum;
import org.javaboy.tcrpc.loadbalance.impl.RandomLoadbalance;
import org.javaboy.tcrpc.registry.RegistryFactory;
import org.javaboy.tcrpc.registry.RegistryService;
import org.javaboy.tcrpc.registry.ServiceInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author:majin.wj 用于管理一个服务所有连接
 */
public class ConnectionDirectory {

    private static final Logger LOG = LoggerFactory.getLogger(ConnectionDirectory.class);

    private List<Connection> avaiableConnections = new ArrayList<>();
    /**
     * Key ip:port
     */
    private Map<String, Connection> connectionMap = new HashMap<>();
    private String serviceKey;
    private Map<String, LoadBalance> loadBalancers;

    private RegistryConfig registryConfig = ApplicationConfig.DEFAULT.getRegistryConfig();
    private RegistryService registry = RegistryFactory.DEFAULT.getRegistry(registryConfig);

    public ConnectionDirectory(String serviceKey) {
        this.serviceKey = serviceKey;
        init();
    }

    void init() {
        List<ServiceInstance> instances = registry.subscribe(serviceKey, instances1 -> {
            LOG.info("RegistryCenter notify ServiceChange instances:{} ", JSON.toJSONString(instances1));
            onServiceChange(instances1);
        });

        onServiceChange(instances);
    }

    private void onServiceChange(List<ServiceInstance> instances) {
        List<ServiceInstance> newAddInstances = new ArrayList<>();
        // 新增的连接
        for (ServiceInstance instance : instances) {
            String address = instance.getAddress();
            boolean exists = connectionMap.containsKey(address);
            if (!exists) {
                newAddInstances.add(instance);
            }
        }
        // 需要被销毁的连接
        List<String> destroyAddress = new ArrayList<>();
        Map<String, ServiceInstance> newInstances = instances.stream().collect(Collectors.toMap(ServiceInstance::getAddress, k -> k));
        for (String address : connectionMap.keySet()) {
            boolean exists = newInstances.containsKey(address);
            if (!exists) {
                destroyAddress.add(address);
            }
        }
        // 将需要被销毁的连接从map中移除
        List<Connection> destroyConnection = new ArrayList<>();
        for (String address : destroyAddress) {
            Connection toDestroyConnection = connectionMap.remove(address);
            destroyConnection.add(toDestroyConnection);
        }
        destroyConnections(destroyConnection);
        createConnections(newAddInstances,this.connectionMap);
    }

    void destroyConnections(List<Connection> connections) {
        for (Connection connection : connections) {
            connection.destroy();
        }
    }

    void createConnections(List<ServiceInstance> instances, Map<String, Connection> connectionMap) {
        for (ServiceInstance instance : instances) {
            Connection connection = new Connection(instance.getIp(), instance.getPort());
            connection.init();;
            connectionMap.put(instance.getAddress(), connection);
        }
    }


    public Connection getConnection() {
        return loadBalancers.get("random").select(avaiableConnections);
    }


    public void initLoadBalancer() {
        loadBalancers.put(LoadBalanceEnum.RANDOM.getCode(), new RandomLoadbalance());
    }


}
