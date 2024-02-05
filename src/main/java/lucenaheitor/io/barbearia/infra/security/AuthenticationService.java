package lucenaheitor.io.barbearia.infra.security;

import lucenaheitor.io.barbearia.domain.usuario.UserRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements UserDetailsService {

    @Autowired
    private UserRespository  respository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return respository.findByLogin(username);
    }
}
