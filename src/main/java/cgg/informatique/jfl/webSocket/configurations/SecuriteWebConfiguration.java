package cgg.informatique.jfl.webSocket.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecuriteWebConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private MonUserDetailsService monUserDetailsService;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //permettre toutes les requêtes
        http.authorizeRequests()
                .antMatchers("/","/dojo").permitAll()
                .antMatchers("/admin/**", "/admin/", "/Mystere", "/Mystere/**").hasAuthority("ADMIN")
                .antMatchers("/membre/**", "/membre/").authenticated()
                .and()
                //Activer le formulaire pour login
                .formLogin()
                .loginPage("/login")
                .failureUrl("/login?error=true")
                .and()
                .rememberMe()
                .alwaysRemember(true)
                .tokenValiditySeconds(30*5)
                .rememberMeCookieName("mouni")
                .key("somesecret")
                .userDetailsService(monUserDetailsService)
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/dojo")
                //pour la console H2
                .and()
                .csrf().disable()
                .headers().frameOptions().disable()
                ;

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(monUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    };

}
