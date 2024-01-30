package lucenaheitor.io.barbearia.controler.validation_barbeiros;

import lucenaheitor.io.barbearia.domain.barbeiros.BarbeiroRepository;
import lucenaheitor.io.barbearia.domain.barbeiros.CadastroBarbeiroDTO;
import lucenaheitor.io.barbearia.infra.controllerException.ValidationExeception;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ValidationBarbeirosEmail implements ValidationBarbeiro {
    @Autowired
    private BarbeiroRepository repository;



    @Override
    public void validar(CadastroBarbeiroDTO data) {
        if(repository.existsByEmail(data.email())){
            throw new ValidationExeception("Email  ja existe!");
        }
    }
}
