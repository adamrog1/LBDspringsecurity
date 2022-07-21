package com.example.springsecurity.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
//                .antMatchers("/user**").hasAnyRole("ADMIN","USER")
                ///ROLE
//                .antMatchers(HttpMethod.GET,"/user").permitAll()
//                .antMatchers(HttpMethod.PUT,"/user").hasAnyRole("USER","ADMIN")
//                .antMatchers(HttpMethod.GET,"/admin").hasAnyRole("USER","ADMIN")
//                .antMatchers(HttpMethod.POST,"/admin").hasRole("ADMIN")
//                .antMatchers(HttpMethod.DELETE,"/admin").hasRole("ADMIN")
//                .antMatchers("/admin**").hasRole("ADMIN")
                //UPRAWNIENIA
                .antMatchers(HttpMethod.GET,"/user**").hasAnyAuthority(UserPermissions.USER_READ.toString(),UserPermissions.ADMIN.toString())
                .antMatchers(HttpMethod.PUT,"/user**").hasAnyAuthority(UserPermissions.USER_EDIT.toString(),UserPermissions.ADMIN.toString())
                .antMatchers(HttpMethod.GET,"/admin**").hasAuthority(UserPermissions.ADMIN.toString())
                .antMatchers(HttpMethod.POST,"/admin**").hasAuthority(UserPermissions.ADMIN.name())
                .antMatchers(HttpMethod.DELETE, "/admin**").hasAuthority(UserPermissions.ADMIN.name())
                //.anyRequest().authenticated()
//                .and()
//                .formLogin()
                .and()
                .csrf().disable()
        ;
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(10);
    }

    @Override
    @Bean
    public UserDetailsService userDetailsService(){
        UserDetails admin= User.builder()
                .username("admin")
                .password(passwordEncoder().encode("admin"))
//                .roles("ADMIN")
                .authorities(UserPermissions.ADMIN.toString())
                .build();
        UserDetails user=User.builder()
                .username("user")
                .password(passwordEncoder().encode("user"))
//                .roles("USER")
                .authorities(UserPermissions.USER_READ.toString(),UserPermissions.USER_EDIT.toString())
                .build();
        UserDetails spectator=User.builder()
                .username("spectator")
                .password(passwordEncoder().encode("spectator"))
                .authorities(UserPermissions.USER_READ.toString())
                .build();
        return new InMemoryUserDetailsManager(admin,user,spectator);
    }
}
