package cristinauzunov.u5d15.security;

import cristinauzunov.u5d15.entity.Utente;
import cristinauzunov.u5d15.service.UtenteService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JWTFilter extends OncePerRequestFilter {

    private final JWTTools jwtTools;
    private final UtenteService utenteService;

    public JWTFilter(JWTTools jwtTools, UtenteService utenteService) {
        this.jwtTools = jwtTools;
        this.utenteService = utenteService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // leggo l'header Authorization
        String authHeader = request.getHeader("Authorization");

        // se non c'è o non inizia con "Bearer ", lascio proseguire senza autenticare
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // tolgo la scritta "Bearer " e tengo solo il token
        String token = authHeader.substring(7);

        // controllo che il token sia valido (firma giusta e non scaduto)
        jwtTools.verificaToken(token);

        // estraggo l'id dell'utente e lo recupero dal database
        Long id = jwtTools.estraiId(token);
        Utente utente = utenteService.trovaPerId(id);

        // preparo il ruolo nel formato che Spring Security si aspetta
        List<SimpleGrantedAuthority> autorizzazioni = new ArrayList<>();
        autorizzazioni.add(new SimpleGrantedAuthority("ROLE_" + utente.getRuolo().name()));

        // dico a Spring che questa richiesta è autenticata, e da chi
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(utente, null, autorizzazioni);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // proseguo verso il controller
        filterChain.doFilter(request, response);
    }
}