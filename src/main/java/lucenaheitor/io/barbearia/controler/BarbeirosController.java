package lucenaheitor.io.barbearia.controler;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lucenaheitor.io.barbearia.controler.validation.ValidationBarbeiro;
import lucenaheitor.io.barbearia.controler.validation.ValidationBarbeirosEmail;
import lucenaheitor.io.barbearia.controler.validation.ValidationCpfBarbeiros;
import lucenaheitor.io.barbearia.domain.barbeiros.*;
import lucenaheitor.io.barbearia.domain.barbeiros.DetailsBarbeiros;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/barbeiros")
public class BarbeirosController {

        @Autowired
        private BarbeiroRepository repository;

        @Autowired
        private List<ValidationBarbeiro> validadores;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid CadastroBarbeiroDTO data){
            var barbeiro = new Barbeiro(data);

            validadores.forEach(v -> v.validar(data));

            repository.save(barbeiro);
            return  ResponseEntity.ok(barbeiro);
    }
    @GetMapping
    public ResponseEntity<Page<ListagemBarbeirosDTO>> listar (@PageableDefault(size = 5, sort = {"nome"}) Pageable paginacao){
        var page = repository.findAll(paginacao).map(ListagemBarbeirosDTO::new);
        return  ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public  ResponseEntity detail(@PathVariable Long id){
        var barbeiro = repository.getReferenceById(id);
        return  ResponseEntity.ok(new DetailsBarbeiros(barbeiro));
    }

    @PutMapping
    @Transactional
    public ResponseEntity update(@RequestBody @Valid AtualizationoBarbeirosDTO data){
            var barbeiro = repository.getReferenceById(data.id());
            barbeiro.updateInfo(data);
            return  ResponseEntity.ok( new DetailsBarbeiros(barbeiro));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public  ResponseEntity delete(@PathVariable Long id ){
            repository.deleteById(id);
            return  ResponseEntity.noContent().build();
    }
}
