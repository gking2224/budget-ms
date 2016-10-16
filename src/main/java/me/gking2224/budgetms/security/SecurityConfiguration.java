package me.gking2224.budgetms.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import me.gking2224.securityms.client.CommonSecurityConfiguration;
import me.gking2224.securityms.client.HttpSecurityConfigurer;

@Import(CommonSecurityConfiguration.class)
public class SecurityConfiguration {

    @Bean
    HttpSecurityConfigurer httpSecurityConfigurer() {
        return new HttpSecurityConfigurer() {

            @Override
            public void configure(final HttpSecurity http) throws Exception {
                http.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/budgets")
                    .hasAnyAuthority("Permission:ViewBudgetSummary", "Permission:ViewBudgetDetail")
                .antMatchers(HttpMethod.POST, "/budgets")
                    .hasAnyAuthority("Permission:CreateBudget")
                .antMatchers(HttpMethod.PUT, "/budgets/**")
                    .hasAnyAuthority("Permission:EditBudget")
                .antMatchers(HttpMethod.GET, "/budgets/**")
                    .hasAnyAuthority("Permission:ViewBudgetDetail")
                .antMatchers(HttpMethod.DELETE, "/budgets/**")
                    .hasAnyAuthority("Permission:DeleteBudget");
            }
        };
    }

}