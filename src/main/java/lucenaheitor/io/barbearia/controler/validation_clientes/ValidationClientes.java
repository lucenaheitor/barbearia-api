package lucenaheitor.io.barbearia.controler.validation_clientes;

import lucenaheitor.io.barbearia.domain.clientes.CadastroClienteDTO;

public interface ValidationClientes {

    void validar(CadastroClienteDTO data);
}
