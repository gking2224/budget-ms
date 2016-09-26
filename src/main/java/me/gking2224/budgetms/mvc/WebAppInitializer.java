package me.gking2224.budgetms.mvc;

import javax.servlet.Filter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.context.ApplicationContextInitializer;

import me.gking2224.common.web.WebAppConfigurer;

public class WebAppInitializer
//extends AbstractAnnotationConfigDispatcherServletInitializer {
{
//    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] { WebAppConfigurer.class };
    }

//    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] { WebAppConfigurer.class };
    }

//    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }
    
//    @Override
    protected Filter[] getServletFilters() {
        return new Filter[] {};
    }
//    @Override
    protected ApplicationContextInitializer<?>[] getRootApplicationContextInitializers() {
        return new ApplicationContextInitializer<?>[] {
                new RootApplicationContextInitializer()
                };
    }

//    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
//        super.onStartup(servletContext);
        servletContext.setInitParameter("spring.profiles.default", "dev");
    }

}
