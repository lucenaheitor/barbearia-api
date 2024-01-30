package lucenaheitor.io.barbearia.controler.validation_clientes;

import lucenaheitor.io.barbearia.domain.clientes.CadastroClienteDTO;
import lucenaheitor.io.barbearia.domain.clientes.ClienteRepository;
import lucenaheitor.io.barbearia.infra.controllerException.ValidationExeception;
import org.springframework.stereotype.Service;

@Service
public class ValidationClienteEmail {

    private ClienteRepository repository;

    public void VALIDAR(CadastroClienteDTO data){
        if(repository.existsByEmail(data.email())){
            throw  new ValidationExeception("Email ja existe!");
        }
    }

}
