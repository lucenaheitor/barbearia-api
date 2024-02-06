package lucenaheitor.io.barbearia.infra.security;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import lucenaheitor.io.barbearia.domain.usuario.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private  String secret;

    public  String generateToken(Usuario usuario){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer("barbearia_api")
                    .withSubject(usuario.getLogin())
                    .withExpiresAt(dataExpiration())
                    .sign(algorithm);

            return  token;
        } catch (JWTCreationException exception){
            throw  new RuntimeException("Error to generate token", exception);
        }
    }

    public String  getSubject(String tokenJWT){
        try {
            Algorithm algorithm  =  Algorithm.HMAC256(secret);
            return  JWT.require(algorithm)
                    .withIssuer("barbearia_api")
                    .build()
                    .verify(tokenJWT)
                    .getSubject();
        }catch(JWTVerificationException exception){
            exception.printStackTrace();
            throw  new RuntimeException("token invalido ou expirado!");
        }
    }

    private Instant dataExpiration() {
        return Instant.now().plus(2, ChronoUnit.HOURS);
    }
}
