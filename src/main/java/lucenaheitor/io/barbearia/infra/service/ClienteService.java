package lucenaheitor.io.barbearia.infra.service;

import jakarta.persistence.EntityNotFoundException;
import lucenaheitor.io.barbearia.controler.validation_barbeiros.ValidationBarbeiro;
import lucenaheitor.io.barbearia.controler.validation_clientes.ValidationClientes;
import lucenaheitor.io.barbearia.domain.barbeiros.AtualizationoBarbeirosDTO;
import lucenaheitor.io.barbearia.domain.barbeiros.Barbeiro;
import lucenaheitor.io.barbearia.domain.clientes.*;
import org.aspectj.apache.bcel.generic.InstructionConstants;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    private ModelMapper modelmapper;

    @Autowired
    private List<ValidationClientes> validadores;


    public CadastroClienteDTO createCliente(CadastroClienteDTO dto) {
        Cliente cliente = modelmapper.map(dto, Cliente.class);
        //validadores.forEach(v -> v.validar(dto));
        clienteRepository.save(cliente);

        return modelmapper.map(cliente, CadastroClienteDTO.class);
    }


    public Page<ListagemClientesDTO> getClient(Pageable pageable) {
        return clienteRepository.findAll(pageable)
                .map(cliente -> modelmapper.map(cliente, ListagemClientesDTO.class));
    }

    public DetailsClientesDTO detailClient(Long id){
        var cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Barbeiro.class.getName()));
        return modelmapper.map(cliente, DetailsClientesDTO.class);
    }

    public AtualizationClientesDTO updateClient(AtualizationClientesDTO dto){
        clienteRepository.getReferenceById(dto.id());
        Cliente cliente  = modelmapper.map(dto, Cliente.class);
        clienteRepository.save(cliente);
        return  modelmapper.map(cliente, AtualizationClientesDTO.class);
    }

    public void  deleteClient(Long id){

        clienteRepository.deleteById(id);
    }
}
