package org.javaboy.tcrpc.client.remoting.connection;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author:majin.wj
 */
public class ConnectionManager {

    public static ConnectionManager INSTANCE = new ConnectionManager();


    private final Map<String, ConnectionDirectory> connectionDirectoryMap = new ConcurrentHashMap<>();

    private Map<String, Object> serviceLock = new ConcurrentHashMap<>();

    public ConnectionDirectory getConnectionDirectory(String serviceKey) {
        ConnectionDirectory directory = connectionDirectoryMap.get(serviceKey);
        if (directory == null) {
            Object lock = serviceLock.computeIfAbsent(serviceKey, k -> new Object());
            synchronized (lock) {
                if (connectionDirectoryMap.get(serviceKey) == null) {
                    directory = new ConnectionDirectory(serviceKey);
                    connectionDirectoryMap.put(serviceKey, directory);
                }
            }
        }
        return directory;
    }


    public void destroyConnection() {
        for (ConnectionDirectory directory : connectionDirectoryMap.values()) {
            directory.close();
        }
    }

}
