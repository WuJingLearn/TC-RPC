package org.javaboy.tcrpc.boot.parse;

import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * @author:majin.wj
 */
public class TcReferenceBeanPostProcessor implements BeanPostProcessor {

    private String[] packages;


    public void setPackages(String[] packages) {
        this.packages = packages;
    }
}
