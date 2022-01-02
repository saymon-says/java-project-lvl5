package hexlet.code.app.config;

import hexlet.code.app.config.security.JwtTokenFilter;
import hexlet.code.app.repository.UserRepository;
import org.slf4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static java.lang.String.format;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserRepository userRepository;
    private final Logger logger;
    private final JwtTokenFilter filter;

    public WebSecurityConfig(UserRepository userRepository, Logger logger, JwtTokenFilter filter) {
        this.userRepository = userRepository;
        this.logger = logger;
        this.filter = filter;
        SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers(POST, "/api/users").permitAll()
                .antMatchers(GET, "/api/users").permitAll()
                .anyRequest().authenticated()
                .and().httpBasic();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(username -> (UserDetails) userRepository
                .findByFirstName(username)
                .orElseThrow(
                        () -> new UsernameNotFoundException(
                                format("User: %s, not found", username)
                        )
                ));
    }
}

