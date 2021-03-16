package com.zeus.inc.afinams.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.datatables.repository.DataTablesRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.zeus.inc.afinams.dt_repository", repositoryFactoryBeanClass = DataTablesRepositoryFactoryBean.class)
public class DataTablesConfiguration {
}