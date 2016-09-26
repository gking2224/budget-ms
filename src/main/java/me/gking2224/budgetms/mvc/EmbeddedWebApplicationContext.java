package me.gking2224.budgetms.mvc;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
public class EmbeddedWebApplicationContext
        extends org.springframework.boot.context.embedded.EmbeddedWebApplicationContext {

    @Override
    @Bean
    public EmbeddedServletContainerFactory getEmbeddedServletContainerFactory() {
        TomcatEmbeddedServletContainerFactory factory = new TomcatEmbeddedServletContainerFactory();
        factory.setPort(8082);
        factory.setSessionTimeout(50, TimeUnit.MINUTES);
        
        return factory;
    }
    
}
