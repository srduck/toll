package srduck.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import srduck.services.MyUserDetailService;

/**
 * Created by igor on 12.08.2017.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    MyUserDetailService userDetailService;

    @Override
    protected void configure (HttpSecurity http) throws Exception{
        http
                .authorizeRequests()
                .antMatchers("/css/**","/img/**","/js/**","/error").permitAll()
                .antMatchers("/payments*","/routes*").hasRole("CLIENT")
                .antMatchers("/registerClient*").hasRole("MANAGER")
                .antMatchers("/registerManager*", "/users").hasRole("ROOT")
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
                .userDetailsService(userDetailService);
    }
}
