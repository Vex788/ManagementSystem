package com.zeus.inc.afinams.repository;

import com.zeus.inc.afinams.model.GlobalConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface GlobalConfigurationRepository extends JpaRepository<GlobalConfiguration, Long> {

    ArrayList<GlobalConfiguration> findAll();

    GlobalConfiguration findByConfigKey(String configKey);

    boolean existsByConfigKey(String configKey);
}
