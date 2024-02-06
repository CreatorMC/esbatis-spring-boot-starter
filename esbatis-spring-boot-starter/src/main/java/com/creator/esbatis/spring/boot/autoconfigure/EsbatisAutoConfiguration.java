package com.creator.esbatis.spring.boot.autoconfigure;

import com.creator.esbatis.spring.MapperFactoryBean;
import com.creator.esbatis.spring.MapperScannerConfigurer;
import com.creator.mybatis.session.SqlSessionFactory;
import com.creator.mybatis.session.SqlSessionFactoryBuilder;
import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Configuration
@ConditionalOnClass({SqlSessionFactory.class})
@EnableConfigurationProperties(EsbatisProperties.class)
public class EsbatisAutoConfiguration implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(EsbatisAutoConfiguration.class);

    @Bean
    @ConditionalOnMissingBean
    public SqlSessionFactory sqlSessionFactory(Connection connection, EsbatisProperties esbatisProperties) throws IOException, DocumentException {
        return new SqlSessionFactoryBuilder().build(connection, esbatisProperties.getMapperLocations());
    }

    @Bean
    @ConditionalOnMissingBean
    public Connection connection(EsbatisProperties esbatisProperties) {
        try {
//            Class.forName(esbatisProperties.getDriver());
            return DriverManager.getConnection(esbatisProperties.getUrl());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static class AutoConfiguredMapperScannerRegistrar implements EnvironmentAware, ImportBeanDefinitionRegistrar {

        private String basePackage;

        @Override
        public void setEnvironment(Environment environment) {
            this.basePackage = environment.getProperty("esbatis.datasource.base-dao-package");
        }

        @Override
        public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
            BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(MapperScannerConfigurer.class);
            builder.addPropertyValue("basePackage", basePackage);
            registry.registerBeanDefinition(MapperScannerConfigurer.class.getName(), builder.getBeanDefinition());
        }
    }

    /**
     * 引入并启动 {@link AutoConfiguredMapperScannerRegistrar} 类
     */
    @Configuration
    @Import(AutoConfiguredMapperScannerRegistrar.class)
    @ConditionalOnMissingBean({ MapperFactoryBean.class, MapperScannerConfigurer.class })
    public static class MapperScannerRegistrarNotFoundConfiguration implements InitializingBean {

        @Override
        public void afterPropertiesSet() {
            logger.debug(
                    "Not found configuration for registering mapper bean using @MapperScan, MapperFactoryBean and MapperScannerConfigurer.");
        }

    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
