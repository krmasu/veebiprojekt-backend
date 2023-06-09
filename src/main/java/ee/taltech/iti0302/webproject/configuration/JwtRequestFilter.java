package ee.taltech.iti0302.webproject.configuration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Key;
import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {
    private static final Logger log = LoggerFactory.getLogger(JwtRequestFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Optional<String> jwt = getToken(request);
        if (jwt.isEmpty()) {
            log.info("No authentication token found");
            filterChain.doFilter(request, response);
            return;
        }
        log.info("Found authentication token. Parsing token for claims");
        Claims tokenBody = parseToken(jwt.get());
        SecurityContext context = SecurityContextHolder.getContext();
        log.info("Building authentication token");
        UsernamePasswordAuthenticationToken authenticationToken = buildAuthToken(tokenBody);
        context.setAuthentication(authenticationToken);

        filterChain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken buildAuthToken(Claims claims) {
        return new UsernamePasswordAuthenticationToken(claims.get("sub"),
                "",
                List.of(new SimpleGrantedAuthority("USER")));
    }

    private Optional<String> getToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            return Optional.empty();
        }
        return Optional.of(header.substring("Bearer ".length()));
    }

    private Claims parseToken(String token) {
        byte[] keyBites = Decoders.BASE64.decode("OERweXEyQ1B1cmJaNW9taklBR0xrSnVKMGFmbG9wTXNJUjBiQkZrdQ==");
        Key key = Keys.hmacShaKeyFor(keyBites);
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
