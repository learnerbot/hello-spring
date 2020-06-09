package hu.learnerbot.hellospring.config;

import hu.learnerbot.hellospring.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class CustomWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/logout").authenticated()
                .antMatchers("/topics/**").authenticated()
                .antMatchers("/user/**").authenticated()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/css/**").permitAll()
                .antMatchers("/error").permitAll()
                .antMatchers("/register").anonymous()
                .antMatchers("/login").anonymous().and()
                .formLogin().loginPage("/login").failureUrl("/login?error").defaultSuccessUrl("/topics").and()
                .logout().logoutSuccessUrl("/login");

        //NOTE: the following two lines of code are only required to get the h2-console working and actuator shutdown
        http.csrf().ignoringAntMatchers("/h2-console/**").ignoringAntMatchers("/actuator/shutdown");
        http.headers().frameOptions().disable();
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder;
    }
}
