package com.anil.erp.util;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/erpsystem/login/validate", 
                		"/erpsystem/login/publishCourse/all", 
                		"/erpsystem/login/publishCourse/top6", 
                		"/erpsystem/login/publishCourse", 
                		"/erpsystem/user/signup",
                		"/erpsystem/student/**",
                		"/erpsystem/user/parent/**",
                		"/erpsystem/tutor/enrolTutor/**").permitAll()
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                // Teacher-only upload
                .requestMatchers("/erpsystem/upload/**","/erpsystem/zoom/**","/erpsystem/teacher/**").hasRole("TEACHER")

                // User-only dashboard/profile
                .requestMatchers("/erpsystem/dashboard/**", "/erpsystem/user/**").hasRole("USER")

                // Student-only section
                .requestMatchers("/erpsystem/student/**").hasRole("STUDENT")

                // Admin full access
                .requestMatchers("/erpsystem/admin/**").hasRole("ADMIN")

                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
    
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of(
        		"http://localhost:5173", 
        		"https://tankstudentportalreact1-8hcxkhour-anilsonone.vercel.app",
        		"https://tankstudentportalreact1.vercel.app")); // React dev server
        configuration.addAllowedOriginPattern("https://*.vercel.app");
        configuration.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization","Content-Type", "Accept"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
