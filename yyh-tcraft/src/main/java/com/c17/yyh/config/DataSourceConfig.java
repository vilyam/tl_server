package com.c17.yyh.config;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;
import org.springframework.data.couchbase.core.CouchbaseTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.couchbase.client.CouchbaseClient;

@Configuration
@EnableScheduling
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableTransactionManagement(mode = AdviceMode.ASPECTJ)
@Import({CacheConfig.class, ServerMainConfig.class})
@PropertySource({
    "WEB-INF/yyh.common.properties", 
    "WEB-INF/yyh.secure.properties",
    "WEB-INF/config/${adventure.config.dir.name:adventure}/adventure.properties"})
public class DataSourceConfig extends AbstractCouchbaseConfiguration{
    @Resource
    private Environment env;
    @Autowired
    ServerMainConfig serverMainConfig;
    
    @Bean(name = "dataSource")
    public DataSource dataSource() {
        DataSource dataSource = getConfiguredDataSource();
        dataSource.setUrl("jdbc:mysql://" + env.getRequiredProperty("database.host") + "/" + env.getRequiredProperty("database.name"));
        dataSource.setUsername(env.getRequiredProperty("database.username"));
        dataSource.setPassword(env.getRequiredProperty("database.password"));
        dataSource.setMaxActive(Integer.parseInt(env.getRequiredProperty("database.poolsize")));
        dataSource.setMinIdle(Integer.parseInt(env.getRequiredProperty("database.minIdle")));
        dataSource.setMaxIdle(Integer.parseInt(env.getRequiredProperty("database.maxIdle")));
        dataSource.setMaxWait(Integer.parseInt(env.getRequiredProperty("database.maxWait")));
        return dataSource;
    }

    @Bean(name = "dataSourceDump")
    public DataSource dataSourceDump() {
        DataSource dataSource = null;
        if (serverMainConfig.serverConfig().isEnableDump()) {
            dataSource = getConfiguredDataSource();
            dataSource.setUrl("jdbc:mysql://" + env.getRequiredProperty("databasedump.host") + "/" + env.getRequiredProperty("databasedump.name"));
            dataSource.setUsername(env.getRequiredProperty("databasedump.username"));
            dataSource.setPassword(env.getRequiredProperty("databasedump.password"));
            dataSource.setMaxActive(Integer.parseInt(env.getRequiredProperty("databasedump.poolsize")));
            dataSource.setMinIdle(Integer.parseInt(env.getRequiredProperty("databasedump.minIdle")));
            dataSource.setMaxIdle(Integer.parseInt(env.getRequiredProperty("databasedump.maxIdle")));
            dataSource.setMaxWait(Integer.parseInt(env.getRequiredProperty("databasedump.maxWait")));
        }
        return dataSource;
    }

    @Bean(name = "transactionManager")
    public DataSourceTransactionManager dataSourceTransactionManager() {
        DataSourceTransactionManager manager = new DataSourceTransactionManager();
        manager.setDataSource(dataSource());
        return manager;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

    private DataSource getConfiguredDataSource() {
        DataSource dataSource = new DataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setConnectionProperties("useUnicode=yes;characterEncoding=utf8;autoReconnect=true");
        dataSource.setValidationQuery("select 1");
        dataSource.setValidationInterval(30000);
        dataSource.setTestWhileIdle(true);
        dataSource.setTestOnBorrow(true);
        dataSource.setTestOnReturn(false);
        dataSource.setRemoveAbandonedTimeout(60);
        dataSource.setRemoveAbandoned(true);
        dataSource.setLogAbandoned(true);
        dataSource.setTimeBetweenEvictionRunsMillis(5000);
        dataSource.setMinEvictableIdleTimeMillis(30000);
        dataSource.setFairQueue(true);
        dataSource.setJdbcInterceptors("org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;"
                + "org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer;"
                + "org.apache.tomcat.jdbc.pool.interceptor.StatementCache(callable=true,max=300)"
                + "org.apache.tomcat.jdbc.pool.interceptor.SlowQueryReportJmx(threshold=5000)");
        return dataSource;
    }

    @Bean(destroyMethod = "shutdown")
    @Override
    public CouchbaseClient couchbaseClient() throws Exception {
        return serverMainConfig.serverConfig().useNoSql ? super.couchbaseClient() : null;
    }

    @Bean
    @Override
    public CouchbaseTemplate couchbaseTemplate() throws Exception {
        return serverMainConfig.serverConfig().useNoSql ? super.couchbaseTemplate() : null;
    }

    @Override
    protected List<String> bootstrapHosts() {
        return Arrays.asList(env.getRequiredProperty("couchbase.host"));
    }

    @Override
    protected String getBucketName() {
        return env.getRequiredProperty("couchbase.bucket");
    }

    @Override
    protected String getBucketPassword() {
        return env.getRequiredProperty("couchbase.password");
    }
}
