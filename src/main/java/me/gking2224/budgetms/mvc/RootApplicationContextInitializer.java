package me.gking2224.budgetms.mvc;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

public class RootApplicationContextInitializer implements ApplicationContextInitializer<AnnotationConfigWebApplicationContext> {

    @SuppressWarnings("unused")
    private AnnotationConfigWebApplicationContext applicationContext;

    @Override
    public void initialize(AnnotationConfigWebApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
}
