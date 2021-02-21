package com.vivi.basic.readwrite;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@ConditionalOnProperty(name = "database.readwrite.enable", havingValue = "true")
public class DatabaseReadWritAutoConfiguration {

    @Bean
    @ConfigurationProperties("spring.datasource")
    public DataSource dataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties("spring.datasource2")
    public DataSource dataSource2() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    @Primary
    public DataSource routingDatasource(@Qualifier("dataSource") DataSource masterDatasource,
                                        @Qualifier("dataSource2") DataSource slaveDatasource) {
        Map<Object, Object> targetDatasource = new HashMap<>(2);
        targetDatasource.put(DbContextHolder.MASTER, masterDatasource);
        targetDatasource.put(DbContextHolder.SLAVE, slaveDatasource);
        RoutingDataSource routingDataSource = new RoutingDataSource();
        routingDataSource.setDefaultTargetDataSource(masterDatasource);
        routingDataSource.setTargetDataSources(targetDatasource);
        return routingDataSource;
    }

    @Bean
    public AspectJExpressionPointcutAdvisor masterAdvice(@Value("${database.pointcut.master}") String pointcut) {
        AspectJExpressionPointcutAdvisor advisor = new AspectJExpressionPointcutAdvisor();
        advisor.setExpression(pointcut);
        advisor.setAdvice(new DataBaseMasterAdvice());
        advisor.setOrder(0);
        return advisor;
    }

    @Bean
    public AspectJExpressionPointcutAdvisor slaveAdvice(@Value("${database.pointcut.slave}") String pointcut) {
        AspectJExpressionPointcutAdvisor advisor = new AspectJExpressionPointcutAdvisor();
        advisor.setExpression(pointcut);
        advisor.setAdvice(new DatabaseSlaveAdvice());
        advisor.setOrder(0);
        return advisor;
    }
}
