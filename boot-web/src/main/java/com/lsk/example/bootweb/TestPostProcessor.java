package com.lsk.example.bootweb;

import lombok.Setter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import java.util.Arrays;

@Setter
public class TestPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println(Arrays.toString(beanFactory.getBeanNamesForType(BeanFactoryPostProcessor.class)));
    }
}
