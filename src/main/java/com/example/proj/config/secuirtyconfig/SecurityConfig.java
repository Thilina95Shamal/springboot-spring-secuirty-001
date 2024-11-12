package com.example.proj.config.secuirtyconfig;

import com.example.proj.config.filter.JWTFilter;
import com.example.proj.servcie.userdetail.MyUserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity // This provides to configure the security not by default but by custom
public class SecurityConfig {

    private final MyUserDetailService myUserDetailService;
    
    private final JWTFilter jwtFilter;

    public SecurityConfig(MyUserDetailService myUserDetailService, JWTFilter jwtFilter) {
        this.myUserDetailService = myUserDetailService;
        this.jwtFilter = jwtFilter;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //http.build() return the object of SecurityFilterChain

//        http.csrf(customizer -> customizer.disable());
        // Add authentication to provide login restriction {access is denied}
//        http.authorizeHttpRequests(request -> request.anyRequest().authenticated());
        // Enable Default Form Login
        //http.formLogin(Customizer.withDefaults());
//        http.httpBasic(Customizer.withDefaults());
        // Why stateless : for every request you have to pass the credentials
        // For this to work in web you have to comment this : "http.formLogin(Customizer.withDefaults());"
//        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // You can create the above in one Object
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request
                        .requestMatchers("user/register","login").permitAll()    // Skipping auth for open links // This should the mapping url {login or user/login}
                        .anyRequest().authenticated()) // But for all other requests must be authenticated
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class) // means before UsernamePasswordAuthenticationFilter use jwtFiler
                .build();

    }

    // When we want our own credentials for logins from HARDCODING THE CREDENTIALS
    /** Note : UserDetailsService is an inbuilt interface from spring security , not a custom impl*/
    // We cant you this if we get from Database
    /*@Bean
    public UserDetailsService userDetailsService(){
        // You can add Multiple users
        UserDetails user1 = User
                .withDefaultPasswordEncoder()
                .username("shamal")
                .password("asdasd")
                .roles("USER")
                .build();

        UserDetails user2 = User
                .withDefaultPasswordEncoder()
                .username("perera")
                .password("qqqq")
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user1,user2);
    }*/

    @Bean
    public AuthenticationProvider authenticationProvider(){
        // It can have multiple authentication provider, but for databases DaoAuthenticationProvider
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        // NoOpPasswordEncoder.getInstance() <-- for the tutorial using default not bcrypt, if not it should be bcrypt
        // provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance()); {no need this since we use bcrypt}
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12)); // Use this is done by Encoder
        provider.setUserDetailsService(myUserDetailService);
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
