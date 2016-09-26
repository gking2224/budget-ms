package me.gking2224.budgetms.mvc;

import javax.servlet.Filter;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class TestWebAppInitializer
extends AbstractAnnotationConfigDispatcherServletInitializer {

    
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] { WebAppTestConfigurer.class };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] { WebAppTestConfigurer.class };
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] { "/*" };
    }
    
    @Override
    protected Filter[] getServletFilters() {
        return new Filter[] {};
    } 

}
