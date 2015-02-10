package com.c17.yyh.config;

import java.util.Arrays;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.google.code.ssm.CacheFactory;
import com.google.code.ssm.config.AddressProvider;
import com.google.code.ssm.config.DefaultAddressProvider;
import com.google.code.ssm.providers.CacheClientFactory;
import com.google.code.ssm.providers.xmemcached.MemcacheClientFactoryImpl;
import com.google.code.ssm.spring.SSMCache;
import com.google.code.ssm.spring.SSMCacheManager;

@EnableCaching
@Configuration
public class CacheConfig {
    private final String memcachedUrl = "127.0.0.1:11211";

  //memcached
  @Bean
  public CacheManager cacheManager() throws Exception {
      SSMCacheManager result = new SSMCacheManager();
      result.setCaches(Arrays.asList(new SSMCache(defaultCacheFactory().getObject(), 18000)));
      return result;
  }

  @Bean
  public CacheFactory defaultCacheFactory() {
      CacheFactory factory = new CacheFactory();
      factory.setCacheName("users");
      factory.setAddressProvider(addressProvider());
      factory.setCacheClientFactory(cacheClientFactory());
      factory.setConfiguration(cacheConfiguration());
      return factory;
  }

  @Bean
  @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
  public CacheClientFactory cacheClientFactory() {
      return new MemcacheClientFactoryImpl();
  }

  @Bean
  public AddressProvider addressProvider() {
      return new DefaultAddressProvider(memcachedUrl);
  }

  @Bean
  @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
  public com.google.code.ssm.providers.CacheConfiguration cacheConfiguration() {
      com.google.code.ssm.providers.CacheConfiguration configuration = new com.google.code.ssm.providers.CacheConfiguration();
      configuration.setConsistentHashing(true);
      return configuration;
  }
  
  //eHcache
//@Bean(name = "ehcache")
//public EhCacheManagerFactoryBean ehCacheManagerFactoryBean() {
//    EhCacheManagerFactoryBean ehcacheManagerFactory = new EhCacheManagerFactoryBean();
//    ehcacheManagerFactory.setConfigLocation(new ClassPathResource("ehcache.xml"));
//    ehcacheManagerFactory.setShared(true);
//    return ehcacheManagerFactory;
//}
//
//@Bean(name = "cacheManager")
//public EhCacheCacheManager ehCacheCacheManager() {
//    
//    EhCacheCacheManager cacheManager = new EhCacheCacheManager();
//    cacheManager.setCacheManager(ehCacheManagerFactoryBean().getObject());
//    return cacheManager;
//}
}
