package org.javaboy.tcrpc.boot.parse;

import org.javaboy.tcrpc.annotation.TCService;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.util.Set;

/**
 * @author:majin.wj
 */
public class TcServiceBeanDefinitionScanner extends ClassPathBeanDefinitionScanner {
    public TcServiceBeanDefinitionScanner(BeanDefinitionRegistry registry) {
        super(registry);
    }

    @Override
    public Set<BeanDefinitionHolder> doScan(String... basePackages) {
        AnnotationTypeFilter filter = new AnnotationTypeFilter(TCService.class);
        addIncludeFilter(filter);
        return super.doScan(basePackages);
    }
}
