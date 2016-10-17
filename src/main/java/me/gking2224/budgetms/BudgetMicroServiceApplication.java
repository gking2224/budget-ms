package me.gking2224.budgetms;


import javax.security.auth.message.config.AuthConfigFactory;
import javax.servlet.ServletContext;

import org.apache.catalina.authenticator.jaspic.AuthConfigFactoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.AnnotationConfigEmbeddedWebApplicationContext;
import org.springframework.boot.web.support.ServletContextApplicationContextInitializer;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.web.context.support.StandardServletEnvironment;

import me.gking2224.budgetms.db.DatabaseConfiguration;
import me.gking2224.budgetms.db.EmbeddedDatabaseConfiguration;
import me.gking2224.budgetms.jms.MessagingConfiguration;
import me.gking2224.budgetms.security.SecurityConfiguration;
import me.gking2224.budgetms.web.WebAppConfiguration;
import me.gking2224.common.CommonConfiguration;
import uk.org.lidalia.sysoutslf4j.context.SysOutOverSLF4J;

@Configuration
@ComponentScan(basePackages={"me.gking2224.budgetms.service", "me.gking2224.budgetms.model"})
@Import({
    WebAppConfiguration.class,
    DatabaseConfiguration.class,
    EmbeddedDatabaseConfiguration.class,
    CommonConfiguration.class,
    SecurityConfiguration.class,
    MessagingConfiguration.class
})
public class BudgetMicroServiceApplication extends SpringBootServletInitializer{

    private ServletContext servletContext;
    
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        ConfigurableEnvironment environment = new StandardServletEnvironment();
//        ApplicationListener<?> listeners = new ApplicationListenerImplementation(this);
        ApplicationContextInitializer<?> initializers = initializer();
        return application
                .contextClass(AnnotationConfigEmbeddedWebApplicationContext.class)
                .environment(environment)
//                .listeners(listeners)
                .initializers(initializers)
                .registerShutdownHook(true)
                .web(true)
                .logStartupInfo(true)
                .beanNameGenerator(new AnnotationBeanNameGenerator())
                .sources(BudgetMicroServiceApplication.class);
    }
    private ServletContextApplicationContextInitializer initializer() {
        ServletContextApplicationContextInitializer initializer = new ServletContextApplicationContextInitializer(servletContext);
        return initializer;
    }
    public static void main(String[] args) {
        SysOutOverSLF4J.sendSystemOutAndErrToSLF4J();

        // http://stackoverflow.com/questions/38802437/upgrading-spring-boot-from-1-3-7-to-1-4-0-causing-nullpointerexception-in-authen
        if (AuthConfigFactory.getFactory() == null) {
            AuthConfigFactory.setFactory(new AuthConfigFactoryImpl());
        }
        SpringApplication.run(BudgetMicroServiceApplication.class, args);
    }
}