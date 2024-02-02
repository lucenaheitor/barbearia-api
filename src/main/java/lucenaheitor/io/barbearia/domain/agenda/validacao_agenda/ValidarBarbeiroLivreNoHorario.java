package lucenaheitor.io.barbearia.domain.agenda.validacao_agenda;

import lucenaheitor.io.barbearia.domain.agenda.AgendaRepository;
import lucenaheitor.io.barbearia.domain.agenda.AgendamentoCorteDTO;
import lucenaheitor.io.barbearia.infra.exception.ValidationExeception;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidarBarbeiroLivreNoHorario  implements  ValidacaoAgendamento{

    @Autowired
    private AgendaRepository repository;
    @Override
    public void validar(AgendamentoCorteDTO data) {
        var barbeiroOcupado =  repository.existsByBarbeiroIdAndDateAndCancelamentoIsNull(data.idBarbeiro(), data.date());
        if(barbeiroOcupado){
            throw   new ValidationExeception("Barbeiro ocupado nesse horario");
        }
    }
}
