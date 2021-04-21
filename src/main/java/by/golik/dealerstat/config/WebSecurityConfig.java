package by.golik.dealerstat.config;

import by.golik.dealerstat.config.jwt.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author Nikita Golik
 */

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private JwtFilter jwtFilter;

    @Autowired
    public void setJwtFilter(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                    .antMatchers("/auth/**").permitAll()
                    .antMatchers(HttpMethod.GET, "/posts").permitAll()
                    .antMatchers(HttpMethod.GET, "/posts/{\\\\d+}").permitAll()
                    .antMatchers(HttpMethod.POST, "/posts")
                .hasAnyAuthority("ROLE_ADMIN", "ROLE_TRADER")
                .antMatchers(HttpMethod.PUT,"/posts/{\\\\d+}")
                .hasAnyAuthority("ROLE_ADMIN", "ROLE_TRADER")
                .antMatchers(HttpMethod.DELETE,"/posts/{\\\\d+}")
                .hasAnyAuthority("ROLE_ADMIN", "ROLE_TRADER")
                .antMatchers("/posts/my","/posts/{\\\\d+}", "/posts/games")
                .hasAnyAuthority("ROLE_ADMIN", "ROLE_TRADER")
                .antMatchers("/posts/{\\\\d+}/approve", "/posts/{\\\\d+}/unapproved",
                        "comments/{\\\\d+}/approve", "comments/{\\\\d+}/unapproved",
                        "users/{\\\\d+}/change-role")
                .hasAnyAuthority("ROLE_ADMIN")
                .anyRequest().authenticated().and()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
