package springboot.jpatest.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import springboot.jpatest.service.StudentService;

@Slf4j
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider{

    @Autowired
    private StudentService studentService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = authentication.getName();
        String password = (String) authentication.getCredentials();

        UsernamePasswordAuthenticationToken authToken = (UsernamePasswordAuthenticationToken) authentication;

        UserDetails user = studentService.loadUserByUsername(authToken.getName());

        //입력 받은 비밀번호와 DB에 저장된 비밀번호를 비교하여 인증 처리를 한다.
        if(!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("password error");
        }

        return new UsernamePasswordAuthenticationToken(username, password, user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
