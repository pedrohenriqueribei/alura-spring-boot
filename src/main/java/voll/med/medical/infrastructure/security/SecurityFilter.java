package voll.med.medical.infrastructure.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import voll.med.medical.domain.usuario.Usuario;
import voll.med.medical.domain.usuario.UsuarioRepository;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository repository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("Filtro de segurança sendo chamado");

        //recupera o token da requisição
        var tokenJWT = recuperarToken(request);

        if (tokenJWT != null) {
            //se trouxer o usuario, é pq o token está valido
            var subject = tokenService.getSubject(tokenJWT);
            System.out.println(subject);

            var usuario = repository.findByLogin(subject);

            //forçar autenticacao atraves do token
            var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            System.out.println("Logado na requisicao com token");
        }


        //filterChain representa a cadeia de filtros que existem na aplicação
        filterChain.doFilter(request, response);
    }

    private String recuperarToken(HttpServletRequest request) {
        var authorizationHeader = request.getHeader("Authorization");

        if(authorizationHeader != null){

            return authorizationHeader.replace("Bearer ", "");
        }

        return null;
    }
}
