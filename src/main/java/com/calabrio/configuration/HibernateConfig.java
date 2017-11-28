package com.calabrio.configuration;

import com.calabrio.datasource.MultiTenantConnectionProvider;
import com.calabrio.datasource.TenantResolver;
import com.calabrio.util.ConnectionUtil;
import com.calabrio.util.SpringUtil;
import org.apache.log4j.Logger;
import org.hibernate.MultiTenancyStrategy;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * (c) Copyright 2017 Calabrio, Inc.
 * All Rights Reserved. www.calabrio.com LICENSED MATERIALS
 * Property of Calabrio, Inc., Minnesota, USA
 * <p>
 * No part of this publication may be reproduced, stored or transmitted,
 * in any form or by any means (electronic, mechanical, photocopying,
 * recording or otherwise) without prior written permission from Calabrio Software.
 * <p>
 * <p>
 * Created by Brendan.Lesniak on 11/17/2017.
 */
@Configuration
@EnableTransactionManagement
public class HibernateConfig {
    private static final Logger log = Logger.getLogger(HibernateConfig.class);

    @Autowired
    private Environment env;

    @Autowired
    private SpringUtil springUtil;

    @Autowired
    @Qualifier("connectionProviderBean")
    private MultiTenantConnectionProvider connectionProvider;

    @Autowired
    @Qualifier("tenantResolverBean")
    private TenantResolver tenantResolver;

    @Bean
    @Autowired
    public LocalSessionFactoryBean sessionFactory() {
        springUtil.listAllBeans().forEach((b) -> log.debug(String.format("Bean: %s", b)));
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan("com.calabrio");
        sessionFactory.setHibernateProperties(hibernateProperties());
        sessionFactory.setCurrentTenantIdentifierResolver(tenantResolver);
        sessionFactory.setMultiTenantConnectionProvider(connectionProvider);
        return sessionFactory;
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getRequiredProperty("jdbc.driverClassName"));
        dataSource.setUrl(ConnectionUtil.connectionString(env.getProperty("jdbc.default.host"), env.getProperty("jdbc.default.port"),env.getProperty("jdbc.default.db"),env.getProperty("jdbc.username"),env.getProperty("jdbc.password")));
        return dataSource;
    }

    private Properties hibernateProperties() {
        Properties props = new Properties();
        props.put("hibernate.dialect", env.getRequiredProperty("hibernate.dialect"));
        props.put("hibernate.show_sql", env.getRequiredProperty("hibernate.show_sql"));
        props.put("hibernate.format_sql", env.getRequiredProperty("hibernate.format_sql"));
        props.put("hibernate.multiTenancy", MultiTenancyStrategy.DATABASE);
        return props;
    }

    @Bean
    @Autowired
    public HibernateTransactionManager transactionManager(SessionFactory s) {
        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(s);
        return txManager;
    }

    @Bean(name = "connectionProviderBean")
    public MultiTenantConnectionProvider connectionProviderBean() {
        return new MultiTenantConnectionProvider();
    }

    @Bean(name = "tenantResolverBean")
    public TenantResolver tenantResolverBean() {
        return new TenantResolver();
    }
}
