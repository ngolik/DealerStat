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
                    .antMatchers(HttpMethod.GET, "/objects").permitAll()
                    .antMatchers(HttpMethod.GET, "/objects/{\\\\d+}").permitAll()
                    .antMatchers(HttpMethod.POST, "/objects")
                .hasAnyAuthority("ROLE_ADMIN", "ROLE_TRADER")
                .antMatchers(HttpMethod.PUT,"/objects/{\\\\d+}")
                .hasAnyAuthority("ROLE_ADMIN", "ROLE_TRADER")
                .antMatchers(HttpMethod.DELETE,"/objects/{\\\\d+}")
                .hasAnyAuthority("ROLE_ADMIN", "ROLE_TRADER")
                .antMatchers("/objects/my","/objects/{\\\\d+}", "/objects/games")
                .hasAnyAuthority("ROLE_ADMIN", "ROLE_TRADER")
                .antMatchers("/objects/{\\\\d+}/approve", "/objects/{\\\\d+}/unapproved",
                        "comments/{\\\\d+}/approve", "comments/{\\\\d+}/unapproved",
                        "users/{\\\\d+}/change-role")
                .hasAnyAuthority("ROLE_ADMIN")
                .anyRequest().authenticated().and()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
