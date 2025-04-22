package pfe.project.back.Config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static org.springframework.http.HttpMethod.*;
import static pfe.project.back.Entity.Permission.*;
import static pfe.project.back.Entity.Role.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {
    private  final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors()
                .and()


                .authorizeHttpRequests()
                .requestMatchers("/api/v1/user/**")
                .permitAll()
                .requestMatchers("/api/v1/employee/**").hasAnyRole(ADMIN.name(), EMPLOYEE.name())
                .requestMatchers("/api/dashboards/**").permitAll()
                .requestMatchers("/api/demandes/**").permitAll()
                /*.requestMatchers(GET,"/api/v1/employee/**").hasAnyAuthority(ADMIN_READ.name(),EMPLOYEE_READ.name())
                .requestMatchers(POST,"/api/v1/employee/**").hasAnyAuthority(ADMIN_CREATE.name(),EMPLOYEE_CREATE.name())
                .requestMatchers(PUT,"/api/v1/employee/**").hasAnyAuthority(ADMIN_UPDATE.name(),EMPLOYEE_UPDATE.name())
                .requestMatchers(DELETE,"/api/v1/employee/**").hasAnyAuthority(ADMIN_DELETE.name(),EMPLOYEE_DELETE.name())*/
                //.requestMatchers("/api/demandes/**").hasRole(USER.name())
                /*.requestMatchers(GET,"/api/demandes/**").hasAuthority(USER_READ.name())
                .requestMatchers(POST,"/api/demandes/**").hasAuthority(USER_CREATE.name())
                .requestMatchers(PUT,"/api/demandes/**").hasAuthority(USER_UPDATE.name())
                .requestMatchers(DELETE,"/api/demandes/**").hasAuthority(USER_DELETE.name())*/
               /* .requestMatchers("/api/v1/admin/**").hasRole(ADMIN.name())
               .requestMatchers("/api/dashboards/**").hasRole("ADMIN")

                .requestMatchers(GET,"/api/v1/admin/**").hasAuthority(ADMIN_READ.name())
                .requestMatchers(POST,"/api/v1/admin/**").hasAuthority(ADMIN_CREATE.name())
                .requestMatchers(PUT,"/api/v1/admin/**").hasAuthority(ADMIN_UPDATE.name())
                .requestMatchers(DELETE,"/api/v1/admin/**").hasAuthority(ADMIN_DELETE.name())
*/

                //.authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout()
                .logoutUrl("/api/v1/user/logout")


                ;
        return http.build();
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200")); // ou plusieurs origines si besoin
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
