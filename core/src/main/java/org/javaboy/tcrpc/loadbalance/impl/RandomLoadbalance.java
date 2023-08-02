package org.javaboy.tcrpc.loadbalance.impl;

import org.javaboy.tcrpc.client.remoting.connection.Connection;
import org.javaboy.tcrpc.loadbalance.LoadBalance;

import java.util.List;

/**
 * @author:majin.wj
 */
public class RandomLoadbalance implements LoadBalance {

    @Override
    public Connection select(List<Connection> connections) {
        return null;
    }
}
