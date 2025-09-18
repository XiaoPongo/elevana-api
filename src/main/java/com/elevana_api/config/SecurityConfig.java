    package com.elevana_api.config;

import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer.AuthorizedUrl;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
   @Value("${supabase.jwt.secret:${SUPABASE_JWT_SECRET}}")
   private String jwtSecret;

   @Bean
   public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
      http.cors(Customizer.withDefaults()).csrf((csrf) -> {
         csrf.disable();
      }).sessionManagement((session) -> {
         session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
      }).authorizeHttpRequests((authorize) -> {
         ((AuthorizedUrl)((AuthorizedUrl)authorize.requestMatchers(new String[]{"/api/**"})).authenticated().anyRequest()).permitAll();
      }).oauth2ResourceServer((oauth2) -> {
         oauth2.jwt((jwt) -> {
            jwt.decoder(this.jwtDecoder());
         });
      });
      return (SecurityFilterChain)http.build();
   }

   @Bean
   public JwtDecoder jwtDecoder() {
      SecretKeySpec secretKey = new SecretKeySpec(this.jwtSecret.getBytes(), "HmacSHA256");
      return NimbusJwtDecoder.withSecretKey(secretKey).build();
   }

   @Bean
   public CorsFilter corsFilter() {
      UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
      CorsConfiguration config = new CorsConfiguration();
      config.setAllowCredentials(true);
      config.addAllowedOriginPattern("*");
      config.addAllowedHeader("*");
      config.addAllowedMethod("*");
      source.registerCorsConfiguration("/**", config);
      return new CorsFilter(source);
   }
}
  