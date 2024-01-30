package lucenaheitor.io.barbearia.controler.validation_clientes;

import lucenaheitor.io.barbearia.domain.clientes.CadastroClienteDTO;
import lucenaheitor.io.barbearia.domain.clientes.ClienteRepository;
import lucenaheitor.io.barbearia.infra.exception.ValidationExeception;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ValidationClienteEmail implements  ValidationClientes {

    @Autowired
    private ClienteRepository repository;

    @Override
    public void validar(CadastroClienteDTO data) {
        if(repository.existsByEmail(data.email())){
            throw  new ValidationExeception("Email ja existe!");
        }
    }
}
