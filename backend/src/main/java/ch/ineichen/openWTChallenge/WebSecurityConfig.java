package ch.ineichen.openWTChallenge;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import static org.springframework.security.config.Customizer.withDefaults;

@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        var passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        var manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("admin")
                .password("admin")
                .passwordEncoder(pw -> {
                    System.out.println(pw);
                    return passwordEncoder.encode(pw);
                })
                .roles("ADMIN").build());
        return manager;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .and()
                //.httpBasic(withDefaults())
                .authorizeRequests()
                .antMatchers("/assets/*", "/login*").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin(withDefaults()).authorizeRequests()
                .and()
                .logout().permitAll();
        return http.build();
    }
}
