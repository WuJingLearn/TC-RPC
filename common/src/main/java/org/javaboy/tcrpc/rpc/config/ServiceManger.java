package org.javaboy.tcrpc.rpc.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author:majin.wj
 */
public class ServiceManger {

    public static final ServiceManger DEFAULT = new ServiceManger();

    private ServiceManger() {

    }

    private List<String> serviceBeanNames = new ArrayList<>();


    public void addServiceBeanName(String beanName) {
        serviceBeanNames.add(beanName);
    }

    public List<String> getServiceBeanNames(){
        return Collections.unmodifiableList(serviceBeanNames);
    }

}
