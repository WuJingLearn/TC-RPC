package org.javaboy.tcrpc.boot.parse;

import org.javaboy.tcrpc.annotation.EnableTcRpc;
import org.javaboy.tcrpc.config.ApplicationConfig;
import org.javaboy.tcrpc.config.RegistryConfig;
import org.javaboy.tcrpc.rpc.service.ServiceManger;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Map;
import java.util.Set;

/**
 * @author:majin.wj
 */
public class TcImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
        Map<String, Object> rpcAttributes = metadata.getAnnotationAttributes(EnableTcRpc.class.getName());
        String[] packages = (String[]) rpcAttributes.get("packages");

        // 扫描服务
        TcServiceBeanDefinitionScanner scanner = new TcServiceBeanDefinitionScanner(registry);
        Set<BeanDefinitionHolder> beanDefinitionHolders = scanner.doScan(packages);
        for (BeanDefinitionHolder beanDefinitionHolder : beanDefinitionHolders) {
            String serviceBeanName = beanDefinitionHolder.getBeanName();
            ServiceManger.DEFAULT.addServiceBeanName(serviceBeanName);
        }

        // 注册Bean后置处理器
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(TcReferenceBeanPostProcessor.class);
        MutablePropertyValues propertyValues = new MutablePropertyValues();
        propertyValues.add("packages", packages);
        beanDefinition.setPropertyValues(propertyValues);
        registry.registerBeanDefinition("tcReferenceBeanPostProcessor", beanDefinition);

        String registryType = (String) rpcAttributes.get("registryType");
        String registryIp = (String) rpcAttributes.get("registryIp");
        String registryPort = (String) rpcAttributes.get("registryPort");

        // 注册中心配置
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setType(registryType);
        registryConfig.setIp(registryIp);
        registryConfig.setPort(Integer.valueOf(registryPort));
        ApplicationConfig.DEFAULT.setRegistryConfig(registryConfig);
    }


}
