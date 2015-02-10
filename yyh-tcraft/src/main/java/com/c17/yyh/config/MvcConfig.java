package com.c17.yyh.config;

import java.util.List;

import com.c17.yyh.core.social.controllers.MMPaymentsController;
import com.c17.yyh.core.social.controllers.OKPaymentsController;
import com.c17.yyh.core.social.controllers.VKPaymentsController;
import com.c17.yyh.core.social.type.SocialNetwork;
import com.c17.yyh.mvc.converter.ExtraFieldSerializer;
import com.c17.yyh.mvc.interceptors.RequestLogInterceptor;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import com.fasterxml.jackson.databind.ser.std.BeanSerializerBase;
import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.models.dto.ApiInfo;
import com.mangofactory.swagger.plugin.EnableSwagger;
import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@EnableWebMvc
@EnableSwagger
@Configuration
@EnableAsync(mode = AdviceMode.ASPECTJ)
@ComponentScan(basePackages = {"com.c17.yyh"})
@Import({DataSourceConfig.class})
public class MvcConfig extends WebMvcConfigurerAdapter {
    @Autowired
    ServerMainConfig serverMainConfig;
    
	private SpringSwaggerConfig springSwaggerConfig;
	 
	@Autowired
	public void setSpringSwaggerConfig(SpringSwaggerConfig springSwaggerConfig) {
		this.springSwaggerConfig = springSwaggerConfig;
	}
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RequestLogInterceptor()).addPathPatterns("/billing**", "/billing/**");
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(getConverter());
    }

    @Bean
    public HttpMessageConverter<?> getConverter(){
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(getObjectMapper());
        return converter;
    }
    
    @Bean
    public ObjectMapper getObjectMapper(){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(Include.NON_NULL);
        objectMapper.registerModule(getSimpleModule());
        return objectMapper;
    }
    
    @Bean
    public SimpleModule getSimpleModule(){
        return new SimpleModule() {
            private static final long serialVersionUID = 1L;
            public void setupModule(SetupContext context) {
                super.setupModule(context);
                context.addBeanSerializerModifier(getBeanSerializerModifier());
            }
        };
    }

    @Bean
    public BeanSerializerModifier getBeanSerializerModifier() {
        return new BeanSerializerModifier() {
            public JsonSerializer<?> modifySerializer(SerializationConfig config,BeanDescription beanDesc,JsonSerializer<?> serializer) {
                if (serializer instanceof BeanSerializerBase) { 
                      return new ExtraFieldSerializer((BeanSerializerBase) serializer, serverMainConfig.serverConfig().serverVersion, serverMainConfig.serverConfig().levelSetVersion);
                } 
                return serializer; 
            }
        };
    }
    
    @Bean
    public VKPaymentsController vkPaymentsController() {
        return serverMainConfig.serverConfig().socialNetwork == SocialNetwork.VK ? new VKPaymentsController() : null;
    }

    @Bean
    public MMPaymentsController mmPaymentsController() {
        return serverMainConfig.serverConfig().socialNetwork == SocialNetwork.MM ? new MMPaymentsController() : null;
    }

    @Bean
    public OKPaymentsController okPaymentsController() {
        return serverMainConfig.serverConfig().socialNetwork == SocialNetwork.OK ? new OKPaymentsController() : null;
    }

    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(7);
        executor.setMaxPoolSize(42);
        executor.setQueueCapacity(11);
        executor.setThreadNamePrefix("TcraftExecutor-");
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.initialize();
        configurer.setTaskExecutor(executor);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/docs/**").addResourceLocations("/resources/public/docs/");
    }
    
	@Bean
	public SwaggerSpringMvcPlugin customImplementation() {
		return new SwaggerSpringMvcPlugin(this.springSwaggerConfig).apiInfo(apiInfo());
	}
 
	private ApiInfo apiInfo() {
		ApiInfo apiInfo = new ApiInfo("Treasure Legends API", "API for Treasure Legends",
				"Treasure Legends API terms of service", "vilyamm@gmail.com",
				"Treasure Legends API Licence Type", "Treasure Legends API License URL");
		return apiInfo;
	}
    
}
