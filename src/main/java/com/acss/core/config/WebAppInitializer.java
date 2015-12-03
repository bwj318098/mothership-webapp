package com.acss.core.config;

import javax.servlet.Filter;
import javax.servlet.ServletRegistration;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.multipart.support.MultipartFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.acss.kaizen.jooq.poc.configuration.PersistenceContext;
public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
    
    @Override
    protected Filter[] getServletFilters() {
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);

        DelegatingFilterProxy securityFilterChain = new DelegatingFilterProxy("springSecurityFilterChain");
        MultipartFilter multipartFilter = new MultipartFilter();
        multipartFilter.setMultipartResolverBeanName("filterMultipartResolver");
        return new Filter[] {characterEncodingFilter,multipartFilter,securityFilterChain};
    }

    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {
        registration.setInitParameter("defaultHtmlEscape", "true");
        registration.setInitParameter("spring.profiles.active", "dev");
    }

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[] {ApplicationConfig.class,PersistenceContext.class,SecurityConfig.class,WebSocketConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[] {WebMvcConfig.class};
    }
}