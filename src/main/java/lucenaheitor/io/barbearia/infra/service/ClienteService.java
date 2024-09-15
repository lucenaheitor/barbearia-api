package lucenaheitor.io.barbearia.infra.service;

import lucenaheitor.io.barbearia.domain.clientes.CadastroClienteDTO;
import lucenaheitor.io.barbearia.domain.clientes.Cliente;
import lucenaheitor.io.barbearia.domain.clientes.ClienteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    private ModelMapper modelmapper;


    public CadastroClienteDTO createCliente(CadastroClienteDTO dto) {
        Cliente cliente = modelmapper.map(dto, Cliente.class);
         clienteRepository.save(cliente);

        return modelmapper.map(cliente, CadastroClienteDTO.class);
    }
}
