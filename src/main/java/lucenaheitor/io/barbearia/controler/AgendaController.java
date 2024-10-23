package lucenaheitor.io.barbearia.controler;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lucenaheitor.io.barbearia.domain.agenda.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/agenda")
@SecurityRequirement(name = "bearer-key")
public class AgendaController {

    @Autowired
    private  AgendaDeDeCorteCabelo agenda;

    @PostMapping
    @Transactional
    public ResponseEntity agender(@RequestBody @Valid AgendamentoCorteDTO data, UriComponentsBuilder uriBuilder){
        var dto = agenda.agendar(data);

        URI adress = uriBuilder.path("/agenda/{id").buildAndExpand(dto.getId()).toUri();

        return  ResponseEntity.ok(adress);
    }

    @DeleteMapping
    @Transactional
    public  ResponseEntity cancelarAtendimento(@RequestBody @Valid CancelamentoDTO data){
        agenda.cancelar(data);
        return  ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<DetalhamentoCorteDeCabelo> atualizaStatus(@PathVariable Long id, @RequestBody StatusDto status){
        DetalhamentoCorteDeCabelo dto = agenda.atualizaStatus(id, status);

        return ResponseEntity.ok(dto);
    }


    @PutMapping("/{id}/pago")
    public ResponseEntity<Void> aprovaPagamento(@PathVariable @NotNull Long id) {
        agenda.aprovaPagamentoPedido(id);

        return ResponseEntity.ok().build();

    }

}
