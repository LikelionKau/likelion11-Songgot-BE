package likelion.underdog.songgotmae.util.jwt;

import likelion.underdog.songgotmae.domain.member.LoginMember;
import likelion.underdog.songgotmae.util.constant.JWT_VO;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (isHeaderVerify(request, response)) {
            String token = request.getHeader(JWT_VO.HEADER).replace(JWT_VO.BEARER_PREFIX, "");
            LoginMember loginMember = JwtProcess.verify(token);

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(loginMember, null, loginMember.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }

    private boolean isHeaderVerify(HttpServletRequest request, HttpServletResponse response) {
        String header = request.getHeader(JWT_VO.HEADER);
        if (header == null || !header.startsWith(JWT_VO.BEARER_PREFIX)) {
            return false;
        } else {
            return true;
        }
    }
}
