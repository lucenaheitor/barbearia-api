package lucenaheitor.io.barbearia.controler.validation_clientes;

import lucenaheitor.io.barbearia.domain.clientes.CadastroClienteDTO;
import lucenaheitor.io.barbearia.domain.clientes.ClienteRepository;
import lucenaheitor.io.barbearia.infra.exception.ValidationExeception;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class ValidationClienteCpf  implements  ValidationClientes{

    @Autowired
    private ClienteRepository repository;
    @Override
    public void validar(CadastroClienteDTO data) {
        if(repository.existsByCpf(data.cpf())){
            throw  new ValidationExeception("cpf ja existe");
        }
    }
}
