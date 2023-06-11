package voll.med.medical.infrastructure.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/*
declarar que é uma classe de configuracao
personalizar configurações de sergurança (enableWebSecurity)

 */
@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

    @Autowired
    private SecurityFilter securityFilter;

    /*
    Esta classe vai configurar no Spring que a configuração padrão de segurança vai ser desabilitado
    Stateless
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                //desabilitar proteção contra CSRF
        return http.csrf().disable()
                //definir que a politica da aplicação é STATELESS
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                //GUIA de autorização de urls
                .and().authorizeHttpRequests()
                //definir as urls que serão liberadas, ou seja, permitir todas requisicoes para /login
                .requestMatchers(HttpMethod.POST, "/login").permitAll()
                //liberar a páginas do Spring Doc
                .requestMatchers("/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**").permitAll()
                // todas as outras vez ser autenticadas
                .anyRequest().authenticated()
                //definir que o meu filtro de seguração para autenticacao do usuário através do token seja chamado antes do filtro do Spring
                .and().addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    /*
    para Spring usar o algoritmo de senha BCrypt
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
