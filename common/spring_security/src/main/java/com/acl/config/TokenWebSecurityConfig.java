package com.acl.config;

import com.acl.filter.TokenAuthenticationFilter;
import com.acl.filter.TokenLoginFilter;
import com.acl.security.DefaultPasswordEncoder;
import com.acl.security.TokenLogoutHandler;
import com.acl.security.TokenManager;
import com.acl.security.UnauthorizedEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled=true, prePostEnabled = true)
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class TokenWebSecurityConfig extends WebSecurityConfigurerAdapter {

    private UserDetailsService userDetailsService;

    private TokenManager tokenManager;

    private DefaultPasswordEncoder defaultPasswordEncoder;
//    private RedisTemplate redisTemplate;

    @Autowired
    public TokenWebSecurityConfig(UserDetailsService userDetailsService, DefaultPasswordEncoder defaultPasswordEncoder,
                                  TokenManager tokenManager){//, RedisTemplate redisTemplate) {
        this.userDetailsService = userDetailsService;
        this.defaultPasswordEncoder = defaultPasswordEncoder;
        this.tokenManager = tokenManager;
//        this.redisTemplate = redisTemplate;
    }

    /**
     * 配置设置
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.exceptionHandling()
                .authenticationEntryPoint(new UnauthorizedEntryPoint())
                .and().csrf().disable()

                .formLogin() // 表单登录
                    .loginPage("/login.html")
                    .loginProcessingUrl("/admin/acl/login") // 设置哪个是登录的 url。
                    //.defaultSuccessUrl("/loginsuccess") // 登录成功之后跳转到哪个 url
                    .defaultSuccessUrl("/loginsuccess.html") // 登录成功之后跳转到哪个 url
                    .failureUrl("/loginfail")
                    //.failureForwardUrl("/loginfail")
                    .permitAll() //必须在这用 .permitAll()，或是下面的.antMatchers()中加入"/login.html"，不然login.html无法显示
                    .and()

                .authorizeRequests()
                    //.antMatchers( "/hello", "/login.html").permitAll()
                    .anyRequest().authenticated()

                .and().logout().logoutUrl("/admin/acl/index/logout")
                .addLogoutHandler(new TokenLogoutHandler(tokenManager)).and()

                .addFilter(new TokenLoginFilter(authenticationManager(), tokenManager))
                .addFilter(new TokenAuthenticationFilter(authenticationManager(), tokenManager)).httpBasic();

//                .addLogoutHandler(new TokenLogoutHandler(tokenManager,redisTemplate)).and()
//                .addFilter(new TokenLoginFilter(authenticationManager(), tokenManager, redisTemplate))
//                .addFilter(new TokenAuthenticationFilter(authenticationManager(), tokenManager, redisTemplate)).httpBasic();
    }

    //其实不需要在这配UserDetailsService和PasswordEncoder
    //因为会在下面这个类中注入
    //InitializeUserDetailsBeanManagerConfigurer.InitializeUserDetailsManagerConfigurer.configure()
//    /**
//     * 密码处理
//     * @param auth
//     * @throws Exception
//     */
//    @Override
//    public void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService).passwordEncoder(defaultPasswordEncoder);
//    }

    /**
     * 配置哪些请求不拦截
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/api/**",
                "/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**",
                "/hello","/favicon.ico","/login.html"
        );
    }
}