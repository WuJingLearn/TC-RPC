package org.javaboy.tcrpc.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author:majin.wj
 */
public class NetUtil {

    public static String getIp() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

}
