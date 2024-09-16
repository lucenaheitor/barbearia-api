package lucenaheitor.io.barbearia.controler;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lucenaheitor.io.barbearia.controler.validation_barbeiros.ValidationBarbeiro;
import lucenaheitor.io.barbearia.domain.barbeiros.*;
import lucenaheitor.io.barbearia.domain.barbeiros.DetailsBarbeiros;
import lucenaheitor.io.barbearia.infra.service.BarbeiroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/barbeiros")
@SecurityRequirement(name = "bearer-key")
public class BarbeirosController {

        @Autowired
        private BarbeiroRepository repository;


        @Autowired
        private BarbeiroService service;

    @PostMapping
    @Transactional
    public ResponseEntity<CadastroBarbeiroDTO> register(@RequestBody @Valid CadastroBarbeiroDTO dto, UriComponentsBuilder uriBuilder){
            CadastroBarbeiroDTO barbeiroDto = service.register(dto);
            URI address = uriBuilder.path("/barbeiros/{id}").buildAndExpand(barbeiroDto.nome()).toUri();
            return ResponseEntity.created(address).body(barbeiroDto);
    }

    @GetMapping
    public ResponseEntity<Page<ListagemBarbeirosDTO>> list (@PageableDefault(size = 5, sort = {"nome"}) Pageable paginacao){
        Page page = service.getBarbeiro(paginacao);
        return  ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public  ResponseEntity<DetailsBarbeiros> detail(@PathVariable Long id){
        DetailsBarbeiros datilsDto =  service.datails(id);
        return ResponseEntity.ok(datilsDto);

    }

    @PutMapping
    @Transactional
    public ResponseEntity update(@RequestBody @Valid   AtualizationoBarbeirosDTO dto){
            AtualizationoBarbeirosDTO barbeirosDTO =  service.update(dto);
            return ResponseEntity.ok(barbeirosDTO);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public  ResponseEntity delete(@PathVariable Long id ){
            service.delete(id);
            return ResponseEntity.noContent().build();
    }
}
