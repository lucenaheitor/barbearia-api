package lucenaheitor.io.barbearia.controler;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lucenaheitor.io.barbearia.controler.validation_clientes.ValidationClientes;
import lucenaheitor.io.barbearia.domain.clientes.*;
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

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid CadastroClienteDTO data){
        var cliente = new Cliente(data);

        validadores.forEach(v -> v.validar(data));

        repository.save(cliente);
        return  ResponseEntity.ok(cliente);
    }
    @GetMapping
    public ResponseEntity<Page<ListagemClientesDTO>> listar (@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao){
        var page = repository.findAll(paginacao).map(ListagemClientesDTO::new);
        return  ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public  ResponseEntity detail(@PathVariable Long id){
        var barbeiro = repository.getReferenceById(id);
        return  ResponseEntity.ok(new DetailsClientesDTO(barbeiro));
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
        repository.deleteById(id);
        return  ResponseEntity.noContent().build();
    }
}
