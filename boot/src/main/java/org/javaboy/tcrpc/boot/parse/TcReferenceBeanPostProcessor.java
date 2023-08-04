package org.javaboy.tcrpc.boot.parse;

import org.apache.commons.lang3.StringUtils;
import org.javaboy.tcrpc.annotation.TCReference;
import org.javaboy.tcrpc.client.proxy.ClientProxyFactory;
import org.javaboy.tcrpc.util.ServiceKeyHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.util.ReflectionUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author:majin.wj
 */
public class TcReferenceBeanPostProcessor implements BeanPostProcessor, BeanFactoryAware {

    private static Logger LOG = LoggerFactory.getLogger(TcReferenceBeanPostProcessor.class);

    private String[] packages;
    private DefaultListableBeanFactory beanFactory;

    private Map<String, Object> referenceBeanMap = new HashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (!needProcessTCReference(bean.getClass().getName())) {
            return bean;
        }
        // 处理TcReference
        ReflectionUtils.doWithFields(bean.getClass(), field -> {
            TCReference annotation = field.getAnnotation(TCReference.class);
            String name = field.getName();
            String id = annotation.id();
            Class<?> interfaceClass = field.getType();
            String referenceBeanName = StringUtils.isEmpty(id) ? name : id;
//            Object proxyBean = beanFactory.getBean(referenceBeanName);
            Object proxyBean = referenceBeanMap.get(referenceBeanName);
            if (proxyBean == null) {
                String version = annotation.version();
                proxyBean = ClientProxyFactory.getProxy(interfaceClass, ServiceKeyHelper.serviceKey(interfaceClass, version));
                beanFactory.registerSingleton(referenceBeanName, proxyBean);
                referenceBeanMap.put(referenceBeanName, proxyBean);
                LOG.info("ReferenceBeanPostProcessor create reference proxy success,referenceBeanType:{}", interfaceClass);
            }
            field.setAccessible(true);
            field.set(bean, proxyBean);

        }, field -> {
            TCReference annotation = field.getAnnotation(TCReference.class);
            return annotation != null;
        });
        return bean;
    }


    public boolean needProcessTCReference(String className) {
        if (packages != null) {
            for (String packageName : packages) {
                if (className.contains(packageName)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void setPackages(String[] packages) {
        this.packages = packages;
    }


    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (DefaultListableBeanFactory) beanFactory;
    }
}
