package lucenaheitor.io.barbearia.controler;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lucenaheitor.io.barbearia.domain.agenda.AgendaDeDeCorteCabelo;
import lucenaheitor.io.barbearia.domain.agenda.AgendamentoCorteDTO;
import lucenaheitor.io.barbearia.domain.agenda.CancelamentoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/agenda")
public class AgendaController {

    @Autowired
    private AgendaDeDeCorteCabelo agenda;

    @PostMapping
    @Transactional
    public ResponseEntity agender(@RequestBody @Valid AgendamentoCorteDTO data){
        var dto = agenda.agendar(data);
        return  ResponseEntity.ok(dto);
    }

    @DeleteMapping
    @Transactional
    public  ResponseEntity cancelarAtendimento(@RequestBody @Valid CancelamentoDTO data){
        agenda.cancelarAtendimentoa(data);
        return  ResponseEntity.noContent().build();
    }

}
