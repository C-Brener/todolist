package br.com.caiquebrener.todolist.filter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.caiquebrener.todolist.user.repository.IUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

    @Autowired
    private IUserRepository repository;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var servletPath = request.getServletPath();
        if (servletPath.equals("/tasks")) {

            //Pegar a auth
            String headerAuthorization = request.getHeader("Authorization");
            String authorization = headerAuthorization.substring("Basic".length()).trim();
            byte[] authDecode = Base64.getDecoder().decode(authorization);
            String authString = new String(authDecode);
            String[] credentials = authString.split(":");
            String username = credentials[0];
            String password = credentials[1];

            // Validar se o usuario existe
            var user = repository.findByUsername(username);
            if (user == null) {
                response.sendError(401, "User without auth");
            } else {
                // validar senha
                var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
                if (passwordVerify.verified) {
                    request.setAttribute("idUser", user.getId());
                    filterChain.doFilter(request, response);
                } else {
                    response.sendError(401, "User unauthorized");
                }
            }
        } else {
            filterChain.doFilter(request, response);
        }

    }
}
