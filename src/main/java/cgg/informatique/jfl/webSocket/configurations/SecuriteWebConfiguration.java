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
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecuriteWebConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private MonUserDetailsService monUserDetailsService;

    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //https://www.baeldung.com/securing-a-restful-web-service-with-spring-security
        System.err.println("boum");
        http
                //Modification pour REST
                .csrf().disable().headers().frameOptions().disable().and()
                .exceptionHandling()
                .authenticationEntryPoint(new RestAuthenticationEntryPoint())
                .and()

                .authorizeRequests()

                //Permettre l'accès à la console H2 uniquement au rôle ADMIN
                .antMatchers("/kumite/**", "/kumite/").authenticated()
                .antMatchers("/passageDeGrades/**", "/passageDeGrades/").hasAnyAuthority("SENSEI","VENERABLE")
                .antMatchers("/AvatarsREST/**", "/AvatarsREST/").authenticated()
                .and()
                .formLogin()

                .successHandler(new MySavedRequestAwareAuthenticationSuccessHandler())
                .failureHandler(new SimpleUrlAuthenticationFailureHandler())
                .and()
                .logout()
                .logoutSuccessUrl("/dojo");

                /* Ancienne version
                 http
                .authorizeRequests()

                //Permettre l'accès à la console H2 uniquement au rôle ADMIN
                .antMatchers("/kumite/**", "/kumite/").authenticated()
                .antMatchers("/passageDeGrades/**", "/passageDeGrades/").hasAnyAuthority("SENSEI","VENERABLE")

                .and()
                //Activer le formulaire pour login
                .formLogin()
                .loginPage("/login")
                .failureUrl("/login?error=true")
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/dojo")
                .and()
                .csrf()
                .ignoringAntMatchers("/**", "/" )//ne pas appliquer le CSRF pour /consoleBD
                .and()
                .headers()
                .frameOptions()
                .sameOrigin();
                */
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
        //https://en.wikipedia.org/wiki/Bcrypt
        //La valeur par defaut est 10
        //https://docs.spring.io/spring-security/site/docs/4.2.7.RELEASE/apidocs/org/springframework/security/crypto/bcrypt/BCryptPasswordEncoder.html
        //https://www.browserling.com/tools/bcrypt
        return new BCryptPasswordEncoder();
    };




}