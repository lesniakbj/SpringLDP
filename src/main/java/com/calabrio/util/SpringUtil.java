package com.calabrio.util;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SpringUtil {
    private static final Logger log = Logger.getLogger(SpringUtil.class);

    @Autowired
    private ApplicationContext applicationContext;

    public List<String> listAllBeans() {
        return Arrays.stream(applicationContext.getBeanDefinitionNames()).sorted().collect(Collectors.toList());
    }
}
