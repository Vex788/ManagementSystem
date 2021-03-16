package com.zeus.inc.afinams.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class GlobalConfiguration {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String configKey;

    private String configValue;

    private String configDescription;


    public GlobalConfiguration() {
    }


    public GlobalConfiguration(Long id, String configKey, String configValue, String configDescription) {
        super();
        this.id = id;
        this.configKey = configKey;
        this.configValue = configValue;
        this.configDescription = configDescription;
    }


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getConfigKey() {
        return configKey;
    }


    public void setConfigKey(String configKey) {
        this.configKey = configKey;
    }


    public String getConfigValue() {
        return configValue;
    }


    public void setConfigValue(String configValue) {
        this.configValue = configValue;
    }


    public String getConfigDescription() {
        return configDescription;
    }


    public void setConfigDescription(String configDescription) {
        this.configDescription = configDescription;
    }
}
