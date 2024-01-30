package lucenaheitor.io.barbearia.controler.validation_clientes;

import lucenaheitor.io.barbearia.domain.clientes.CadastroClienteDTO;
import lucenaheitor.io.barbearia.domain.clientes.ClienteRepository;
import lucenaheitor.io.barbearia.infra.controllerException.ValidationExeception;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
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
