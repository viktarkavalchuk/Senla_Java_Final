package com.senla.course.announcementPlatform.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.web.servlet.ConditionalOnMissingFilterBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.filter.ForwardedHeaderFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.DispatcherType;


@Configuration
@EnableWebMvc
@EnableAutoConfiguration
@ComponentScan("com.senla.course")
public class SpringConfig implements WebMvcConfigurer {

    private final ApplicationContext applicationContext;

//    @Bean
//    @ConditionalOnMissingFilterBean(ForwardedHeaderFilter.class)
//    @ConditionalOnProperty(value = "server.forward-headers-strategy", havingValue = "framework")
//    public FilterRegistrationBean<ForwardedHeaderFilter> forwardedHeaderFilter() {
//        ForwardedHeaderFilter filter = new ForwardedHeaderFilter();
//        FilterRegistrationBean<ForwardedHeaderFilter> registration = new FilterRegistrationBean<>(filter);
//        registration.setDispatcherTypes(DispatcherType.REQUEST, DispatcherType.ASYNC, DispatcherType.ERROR);
//        registration.setOrder(Ordered.HIGHEST_PRECEDENCE);
//        return registration;
//    }

    @Autowired
    public SpringConfig(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH", "OPTION");
//    }
}
