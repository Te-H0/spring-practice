package me.teho.JwtTutorial.config;

import me.teho.JwtTutorial.jwt.JwtAccessDeniedHandler;
import me.teho.JwtTutorial.jwt.JwtAuthenticationEntryPoint;
import me.teho.JwtTutorial.jwt.JwtSecurityConfig;
import me.teho.JwtTutorial.jwt.TokenProvider;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class SecurityConfig {
    private final TokenProvider tokenProvider;
    //    private final CorsFilter corsFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    public SecurityConfig(
            TokenProvider tokenProvider,
//            CorsFilter corsFilter,
            JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
            JwtAccessDeniedHandler jwtAccessDeniedHandler
    ) {
        this.tokenProvider = tokenProvider;
//        this.corsFilter = corsFilter;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers(new AntPathRequestMatcher("/h2-console/**"))
                .requestMatchers(new AntPathRequestMatcher("/favicon.ico"));
        //h2-console 하위 요청, 파비콘 요청은 security 로직을 수행하지 않게 해줌.
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                .csrf(AbstractHttpConfigurer::disable)
                // .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .accessDeniedHandler(jwtAccessDeniedHandler)
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                )
                // 세션을 사용하지 않기 때문에 STATELESS로 설정
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests((registry) ->
                        registry.requestMatchers(new AntPathRequestMatcher("/api/hello"),
                                        new AntPathRequestMatcher("/api/authenticate"),
                                        new AntPathRequestMatcher("/api/signup")).permitAll()
                                .requestMatchers(PathRequest.toH2Console()).permitAll()
                                .anyRequest().authenticated()

                )
                .headers(headers ->
                        headers.frameOptions(options ->
                                options.sameOrigin()
                        )
                )
                .apply(new JwtSecurityConfig(tokenProvider));
        return httpSecurity.build();

    }
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                // token을 사용하는 방식이기 때문에 csrf를 disable합니다.
//                .csrf(csrf -> csrf.disable())
//                .exceptionHandling(exceptionHandling -> exceptionHandling
//                        .accessDeniedHandler(jwtAccessDeniedHandler)
//                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
//                )
//                .authorizeRequests() //HttpServletRequest를 사용하는 요청들에 대한 접근제한을 설정하겠다는 의미인데 이 기능 사라짐
//                .requestMatchers(new AntPathRequestMatcher("/api/hello")).permitAll() // api/hello에 대한 요청은 인증없이 접근 허용
//                .anyRequest().authenticated();//나머지 요청들은 모두 인증되어야 한다.
//
//        return http.build();
}

