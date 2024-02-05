package com.creator.esbatis.spring.boot.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = EsbatisProperties.ESBATIS_PREFIX)
public class EsbatisProperties {

    public static final String ESBATIS_PREFIX = "esbatis.datasource";

    private String driver;
    private String url;
    private String username;
    private String password;
    private String mapperLocations;
    private String baseDaoPackage;

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMapperLocations() {
        return mapperLocations;
    }

    public void setMapperLocations(String mapperLocations) {
        this.mapperLocations = mapperLocations;
    }

    public String getBaseDaoPackage() {
        return baseDaoPackage;
    }

    public void setBaseDaoPackage(String baseDaoPackage) {
        this.baseDaoPackage = baseDaoPackage;
    }
}
