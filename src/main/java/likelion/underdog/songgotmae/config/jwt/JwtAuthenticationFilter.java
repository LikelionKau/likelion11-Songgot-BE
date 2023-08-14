package likelion.underdog.songgotmae.config.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import likelion.underdog.songgotmae.config.auth.LoginMember;
import likelion.underdog.songgotmae.util.formatter.CustomResponseFormatter;
import likelion.underdog.songgotmae.web.dto.member.MemberRequestDto.LoginRequestDto;
import likelion.underdog.songgotmae.web.dto.member.MemberResponseDto.LoginResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
        setFilterProcessesUrl("/api/login");
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.debug("DEBUG : attemptAuthentication 호출됨");
        try {
            ObjectMapper om = new ObjectMapper();
            LoginRequestDto loginRequestDto = om.readValue(request.getInputStream(), LoginRequestDto.class);

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginRequestDto.getKauEmail(), loginRequestDto.getPassword());
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            return authentication;
        } catch (Exception e) {
            throw new InternalAuthenticationServiceException(e.getMessage());

        }
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        log.debug("DEBUG : unsuccessfulAuthentication 호출됨");
        CustomResponseFormatter.fail(response, "로그인에 실패하였습니다.", failed, HttpStatus.UNAUTHORIZED);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.debug("DEBUG : successfulAuthentication 호출됨");
        LoginMember loginMember = (LoginMember) authResult.getPrincipal();
        String accessToken = JwtProcess.create(loginMember);
        response.addHeader(JwtVO.HEADER, accessToken);
        LoginResponseDto loginResponseDto = new LoginResponseDto(loginMember.getMember());
        CustomResponseFormatter.success(response, loginResponseDto);
    }
}
