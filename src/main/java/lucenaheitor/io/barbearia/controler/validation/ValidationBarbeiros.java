package lucenaheitor.io.barbearia.controler.validation;

import lucenaheitor.io.barbearia.domain.barbeiros.BarbeiroRepository;
import lucenaheitor.io.barbearia.infra.controllerException.EmailExistsExeception;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ValidationBarbeiros {
    @Autowired
    private BarbeiroRepository repository;

    public void validar(String  email){
        if(repository.existsByEmail(email)){
            throw new EmailExistsExeception("Email  ja existe!");
        }

    }
}
