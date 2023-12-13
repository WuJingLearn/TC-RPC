package org.javaboy.tcrpc;

import org.javaboy.tcrpc.annotation.EnableTcRpc;
import org.javaboy.tcrpc.config.RegistryConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @author:majin.wj
 */
@SpringBootApplication
@EnableTcRpc(packages = {"org.javaboy.tcrpc"})
public class ConsumerApplication {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        SpringApplication.run(ConsumerApplication.class, args);
    }

    @Bean
    public RegistryConfig registryConfig() {
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setType("nacos");
        registryConfig.setIp("127.0.0.1");
        registryConfig.setPort(8848);
        return registryConfig;
    }


}