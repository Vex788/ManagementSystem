package com.zeus.inc.afinams.services;

import com.zeus.inc.afinams.model.GlobalConfiguration;
import com.zeus.inc.afinams.repository.GlobalConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.List;

@Service
@EnableTransactionManagement
public class GlobalConfigurationServiceImpl implements GlobalConfigurationService {

    @Autowired
    private GlobalConfigurationRepository configurationRepository;

    @Override
    public List<GlobalConfiguration> getAll() {
        return configurationRepository.findAll();
    }

    @Override
    public GlobalConfiguration getByKey(String key) {
        GlobalConfiguration configuration = null;

        if (key != null && !key.isEmpty()) {
            configuration = configurationRepository.findByConfigKey(key);
        }

        return configuration;
    }

    @Override
    public GlobalConfiguration getById(Long id) {
        GlobalConfiguration configuration = null;

        if (id != null && id > 0L) {
            configuration = configurationRepository.findById(id).get();
        }

        return configuration;
    }

    @Override
    public boolean existsByKey(String key) {
        return configurationRepository.existsByConfigKey(key);
    }

    @Override
    public boolean save(GlobalConfiguration configuration) {
        if (configuration != null) {
            configurationRepository.save(configuration);

            return true;
        }
        return false;
    }

    @Override
    public boolean update(GlobalConfiguration configuration) {
        if (configuration != null) {
            configurationRepository.save(configuration);

            return true;
        }
        return false;
    }

    @Override
    public boolean delete(GlobalConfiguration configuration) {
        if (configuration != null) {
            configurationRepository.delete(configuration);

            return true;
        }
        return false;
    }


}