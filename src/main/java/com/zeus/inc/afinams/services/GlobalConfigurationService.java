package com.zeus.inc.afinams.services;

import com.zeus.inc.afinams.model.GlobalConfiguration;

import java.util.List;

public interface GlobalConfigurationService {

    List<GlobalConfiguration> getAll();

    GlobalConfiguration getByKey(String key);

    GlobalConfiguration getById(Long id);

    boolean existsByKey(String key);

    boolean save(GlobalConfiguration configuration);

    boolean update(GlobalConfiguration configuration);

    boolean delete(GlobalConfiguration configuration);
}