package srduck.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Created by igor on 12.08.2017.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure (HttpSecurity http) throws Exception{
        http
                .authorizeRequests()
                .antMatchers("/css/**","/img/**","/error").permitAll()
                .antMatchers("/payments*","/routes*").hasRole("CLIENT")
                .antMatchers("/registerClient*").hasRole("MANAGER")
                .antMatchers("/registerManager*").hasRole("ROOT")
                .antMatchers("/home").authenticated()
                .and()
                .formLogin()
                .loginPage("/login").permitAll().defaultSuccessUrl("/home", true)
                .and()
                .logout().permitAll();
    }

    @Autowired
    public void configureGlobal (AuthenticationManagerBuilder auth) throws Exception{
        auth
                .inMemoryAuthentication()
                .withUser("guest").password("").roles("")
                .and()
                .withUser("client").password("client").roles("CLIENT")
                .and()
                .withUser("manager").password("manager").roles("CLIENT","MANAGER")
                .and()
                .withUser("root").password("root").roles("CLIENT","MANAGER","ROOT");
    }
}
