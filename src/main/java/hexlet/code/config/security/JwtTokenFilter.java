package hexlet.code.config.security;

import hexlet.code.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.util.List.of;
import static java.util.Optional.ofNullable;
import static org.springframework.util.StringUtils.isEmpty;

@Component
@Log
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenUtils jwtTokenUtil;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected final void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        logger.info("Start filter token.....");
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
//        if (isEmpty(header) || !header.startsWith("Bearer ")) {
        if (isEmpty(header)) {
            chain.doFilter(request, response);
            return;
        }

        final String token = header.split(" ")[1].trim();
        if (!jwtTokenUtil.validate(token)) {
            chain.doFilter(request, response);
            return;
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(jwtTokenUtil.getUsername(token));

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null,
                ofNullable(userDetails).map(UserDetails::getAuthorities).orElse(of())
        );

        authentication
                .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }
}
