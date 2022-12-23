package com.preproject.server.config.auth;

import com.preproject.server.auth.filter.JwtAuthenticationFilter;
import com.preproject.server.auth.filter.JwtVerificationFilter;
import com.preproject.server.auth.handler.UserAccessDeniedHandler;
import com.preproject.server.auth.handler.UserAuthenticationEntryPoint;
import com.preproject.server.auth.handler.UserAuthenticationFailureHandler;
import com.preproject.server.auth.handler.UserAuthenticationSuccessHandler;
import com.preproject.server.utils.JwtAuthorityUtils;
import com.preproject.server.utils.JwtTokenizer;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenizer jwtTokenizer;

    private final JwtAuthorityUtils authorityUtils;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.headers().frameOptions().sameOrigin()
                .and()
                .csrf().disable()
                .cors(Customizer.withDefaults())
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin().disable()
                .httpBasic().disable()
                .exceptionHandling()
                .authenticationEntryPoint(new UserAuthenticationEntryPoint())
                .accessDeniedHandler(new UserAccessDeniedHandler())
                .and()
                .apply(new CustomFilterConfig())
                .and()
                .authorizeRequests(auth -> auth
                        // Login Verify
                        .mvcMatchers(HttpMethod.GET,"/auth/verify").hasRole("USER")
                        // User
                        .mvcMatchers(HttpMethod.GET,"/users").permitAll()
                        .mvcMatchers(HttpMethod.GET,"/users/**").hasRole("USER")
                        .mvcMatchers(HttpMethod.POST,"/users/**").permitAll()
                        .mvcMatchers(HttpMethod.PATCH,"/users/**").hasRole("USER")
                        .mvcMatchers(HttpMethod.DELETE,"/users/**").hasRole("USER")
                        // Question
                        .mvcMatchers(HttpMethod.GET,"/questions").permitAll()
                        .mvcMatchers(HttpMethod.POST,"/questions").hasRole("USER")
                        .mvcMatchers(HttpMethod.PATCH,"/questions/**").hasRole("USER")
                        .mvcMatchers(HttpMethod.DELETE,"/questions").hasRole("ADMIN")
                        .mvcMatchers(HttpMethod.DELETE,"/questions/**").hasRole("USER")
                        // Answer
                        .mvcMatchers(HttpMethod.GET,"/answers").permitAll()
                        .mvcMatchers(HttpMethod.POST,"/answers").hasRole("USER")
                        .mvcMatchers(HttpMethod.PATCH,"/answers/**").hasRole("USER")
                        .mvcMatchers(HttpMethod.DELETE,"/answers/**").hasRole("USER")
                        // Comment
                        .mvcMatchers(HttpMethod.POST,"/question-comment/**").hasRole("USER")
                        .mvcMatchers(HttpMethod.PATCH,"/question-comment/comment/**").hasRole("USER")
                        .mvcMatchers(HttpMethod.DELETE,"/question-comment/comment/**").hasRole("USER")
                        .mvcMatchers(HttpMethod.POST,"/answer-comment/**").hasRole("USER")
                        .mvcMatchers(HttpMethod.PATCH,"/answer-comment/comment/**").hasRole("USER")
                        .mvcMatchers(HttpMethod.DELETE,"/answer-comment/comment/**").hasRole("USER")
                        // Vote
                        .mvcMatchers(HttpMethod.POST,"/question-vote/**").hasRole("USER")
                        .mvcMatchers(HttpMethod.PATCH,"/question-vote/vote/**").hasRole("USER")
                        .mvcMatchers(HttpMethod.POST,"/answer-vote/**").hasRole("USER")
                        .mvcMatchers(HttpMethod.PATCH,"/answer-vote/vote/**").hasRole("USER")

                        .anyRequest().permitAll()
                );
        return http.build();
    }

    public class CustomFilterConfig extends AbstractHttpConfigurer<CustomFilterConfig, HttpSecurity> {
        @Override
        public void configure(HttpSecurity builder) throws Exception {
            AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);
            JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager, jwtTokenizer);

            jwtAuthenticationFilter.setFilterProcessesUrl("/auth/login");
            jwtAuthenticationFilter.setAuthenticationSuccessHandler(new UserAuthenticationSuccessHandler());
            jwtAuthenticationFilter.setAuthenticationFailureHandler(new UserAuthenticationFailureHandler());

            JwtVerificationFilter jwtVerificationFilter = new JwtVerificationFilter(jwtTokenizer, authorityUtils);

            builder.addFilter(jwtAuthenticationFilter)
                    .addFilterAfter(jwtVerificationFilter, JwtAuthenticationFilter.class);

        }
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(Arrays.asList("*"));
        corsConfiguration.setAllowedMethods(Arrays.asList("GET","POST", "PATCH", "DELETE"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

}
