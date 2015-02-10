package com.c17.yyh.config;

import javax.servlet.ServletContext;

import org.apache.commons.configuration.ConfigurationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServerMainConfig {
    @Autowired
    private ServletContext servletContext;
  @Bean
  public ServerConfig serverConfig(){
      ServerConfig serverConfig = new ServerConfig();
      try {
        serverConfig.initialize(servletContext);
    } catch (ConfigurationException e) {
        e.printStackTrace();
    }
      return serverConfig;
  }
}
