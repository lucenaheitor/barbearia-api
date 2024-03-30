package lucenaheitor.io.barbearia.domain.agenda.validation_cancel;

import lucenaheitor.io.barbearia.domain.agenda.AgendaRepository;
import lucenaheitor.io.barbearia.domain.agenda.CancelamentoDTO;
import lucenaheitor.io.barbearia.infra.exception.ValidationExeception;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
@Component
public class VerificarHorarioCancelamento implements CancelamentoAgenda {


    @Autowired
    private  AgendaRepository agendaRepository;

    @Override
    public void cancelar(CancelamentoDTO data) {
        var agenda = agendaRepository.getReferenceById(data.idAgenda());
        var  agora = LocalDateTime.now();
        var diferencaEmHoras = Duration.between(agora, agenda.getDate()).toHours();

        if(diferencaEmHoras <24){
            throw  new ValidationExeception(("Só é possivel cancelar com 30 minutos de antecedencia"));

        }
        
    }
}
