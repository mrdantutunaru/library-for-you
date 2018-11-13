package com.demo.libraryProject.config;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private DataSource dataSource;

    @Value("${spring.queries.users-query}")
    private String usersQuery;

    @Value("${spring.queries.roles-query}")
    private String rolesQuery;


    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.
                jdbcAuthentication()
                .usersByUsernameQuery(usersQuery)
                .authoritiesByUsernameQuery(rolesQuery)
                .dataSource(dataSource)
                .passwordEncoder(bCryptPasswordEncoder);
    }

    //TODO Double check the authorization.
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/registration").permitAll()
                //.anyRequest()//allow all urls
                //.authenticated()//all URLs are allowed by any authenticated user, no role restrictions.
               // .antMatchers("*/book-controller-rest-api/*","*/swagger-ui.html#/*").hasRole("SWAGGER")
                .and()
                .formLogin()//enable form based authentication
                .loginPage("/login")
                .defaultSuccessUrl("/admin/index")
                .usernameParameter("email")
                .passwordParameter("password")//use a custom login URI
                .permitAll(true)
                .and()
                .httpBasic()//login URI can be accessed by anyone
                .and()
                .logout()//default logout handling//our new logout success url, we are not replacing other defaults.
                .permitAll();//allow all as it will be accessed when user is not logged in anymore
        http.csrf().disable();
        http.x509();

    }

//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication().withUser("admin").password("admin").authorities("SWAGGER");
//    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
