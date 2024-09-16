package lucenaheitor.io.barbearia.controler;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lucenaheitor.io.barbearia.controler.validation_clientes.ValidationClientes;
import lucenaheitor.io.barbearia.domain.clientes.*;
import lucenaheitor.io.barbearia.infra.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
@SecurityRequirement(name = "bearer-key")
public class Clientecontroller {

    @Autowired
    private ClienteRepository repository;

    @Autowired
    private List<ValidationClientes> validadores;

    @Autowired
    private ClienteService service;

    @PostMapping
    @Transactional
    public ResponseEntity<CadastroClienteDTO> cadastrar(@RequestBody @Valid CadastroClienteDTO dto){
      CadastroClienteDTO clienteDTO = service.createCliente(dto);
      return ResponseEntity.ok(clienteDTO);
    }
    @GetMapping
    public ResponseEntity<Page<ListagemClientesDTO>> list (@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao){
        Page clienteDto = service.getClient(paginacao);
        return  ResponseEntity.ok(clienteDto);
    }

    @GetMapping("/{id}")
    public  ResponseEntity detail(@PathVariable Long id){
        DetailsClientesDTO cliente = service.detailClient(id);
        return ResponseEntity.ok(cliente);
    }

    @PutMapping
    @Transactional
    public ResponseEntity update(@RequestBody @Valid AtualizationClientesDTO data){
        var barbeiro = repository.getReferenceById(data.id());
        barbeiro.updateInfo(data);
        return  ResponseEntity.ok( new DetailsClientesDTO(barbeiro));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity delete(@PathVariable Long id ){
        service.deleteClient(id);
        return  ResponseEntity.noContent().build();
    }
}
