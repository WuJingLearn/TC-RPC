package org.javaboy.tcrpc.loadbalance;

import org.javaboy.tcrpc.client.remoting.connection.Connection;

import java.util.List;

/**
 * @author:majin.wj
 */
public interface LoadBalance {

    Connection select(List<Connection> connections);

}
