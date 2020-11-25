package springboot.jpatest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class UserConfig extends WebSecurityConfigurerAdapter {



    @Autowired
    private CustomAuthenticationProvider authProvider;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN")	//Admin 권한이 있는 경우 접근 허용
                .antMatchers("/user/**").hasRole("USER")	//User 권한이 있는 경우 접근 허용
                .antMatchers("/", "/home","/joinf","/joinPre","/join","/findIdPop").permitAll() //해당 URL은 전체 접근 허용
                .anyRequest().authenticated()	//이외의 URL은 인증 절차를 수행하기 위해 login 페이지로 이동
                .and().csrf().disable()
                .formLogin()            //로그인 수행
                .loginPage("/loginf")
                .defaultSuccessUrl("/user/logOn")
                //.permitAll()
                .and()
                .logout()               //로그아웃 수행
                .permitAll();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/css/**", "/js/**", "/image/**")
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    } //spring security 적용하면 css/js/image 파일을 못쓰게 되는데?? 보안 필터 적용할 필요 없는 리소스 설정하는 것

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
