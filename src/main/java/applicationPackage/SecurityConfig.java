package applicationPackage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final DataSource dataSource;

    @Autowired
    public SecurityConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    protected void configure(HttpSecurity http) {
        try {
            http.csrf().disable();
            http
                    .authorizeRequests()
                    .antMatchers("/registration.xhtml").permitAll()
                    .antMatchers("/javax.faces.resource/**").permitAll()
                    .antMatchers("/clients.xhtml").hasRole("VIEWER")
                    .anyRequest().authenticated()
                    .and()
                    .formLogin()
                    .loginPage("/login.xhtml")
                    .permitAll()
                    .failureUrl("/login.xhtml?error=true")
                    .defaultSuccessUrl("/Main.xhtml")
                    .and()
                    .logout()
                    .logoutSuccessUrl("/login.xhtml");
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Bean
    protected PasswordEncoder dummyPasswordEncoder() {
        return new PasswordEncoder() {

            @Override
            public String encode(CharSequence rawPassword) {
                return rawPassword.toString();
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return rawPassword.toString().equals(encodedPassword);
            }
        };
    }

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery("select e_mail as username, password, 1 as enabled from user where e_mail=?")
                .authoritiesByUsernameQuery("select e_mail as username, access_rights as role from user where e_mail=?");
    }
}
