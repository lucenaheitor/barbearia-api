package lucenaheitor.io.barbearia.controler;

import jakarta.validation.Valid;
import lucenaheitor.io.barbearia.domain.usuario.AuthenticationDTO;
import lucenaheitor.io.barbearia.domain.usuario.RegisterDTO;
import lucenaheitor.io.barbearia.domain.usuario.Usuario;
import lucenaheitor.io.barbearia.domain.usuario.UsuarioRepository;
import lucenaheitor.io.barbearia.infra.security.DataTokenJWT;
import lucenaheitor.io.barbearia.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class LoginController {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping("login")
    public  ResponseEntity login(@RequestBody @Valid AuthenticationDTO data){
       var userNamePaaword =  new UsernamePasswordAuthenticationToken(data.login(), data.password());
       var auth = this.authenticationManager.authenticate(userNamePaaword);
       var token = tokenService.generateToken((Usuario) auth.getPrincipal());

       return  ResponseEntity.ok(new DataTokenJWT(token));
//        try {
//
//            System.out.println("Login:"+data.login());
//            System.out.println("Password:"+data.password());
//
//            var authenticationToken = new UsernamePasswordAuthenticationToken(data.login(), data.password());
//
//            var auth = authenticationManager.authenticate(authenticationToken);
//            System.out.println("Authentication Object:"+auth);
//
//            var tokenJWT = tokenService.generateToken((Usuario)  auth.getPrincipal());
//
//            return ResponseEntity.ok(new DataTokenJWT(tokenJWT));
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
    }


    @PostMapping("/register")
    public  ResponseEntity register(@RequestBody @Valid RegisterDTO data){
        if (this.repository.findByLogin(data.login()) != null) return  ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        Usuario newUser = new Usuario(data.login(),  encryptedPassword, data.role());

        this.repository.save(newUser);
        return  ResponseEntity.ok().build();

    }
    
}
