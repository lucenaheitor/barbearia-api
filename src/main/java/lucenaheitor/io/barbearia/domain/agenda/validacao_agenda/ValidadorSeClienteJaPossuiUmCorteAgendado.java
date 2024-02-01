package lucenaheitor.io.barbearia.domain.agenda.validacao_agenda;

import lucenaheitor.io.barbearia.domain.agenda.AgendaRepository;
import lucenaheitor.io.barbearia.domain.agenda.AgendamentoCorteDTO;
import lucenaheitor.io.barbearia.infra.exception.ValidationExeception;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorSeClienteJaPossuiUmCorteAgendado implements  ValidacaoAgendamento{

    @Autowired
    private AgendaRepository repository;

    @Override
    public void validar(AgendamentoCorteDTO data) {
        var horarioAbertura = data.date().withHour(7);
        var horarioFechamento = data.date().withHour(20);
        var clienteTemHorarioNoDia =  repository.existsByClienteIfAndDateBetween(data.idCliente(), horarioAbertura, horarioFechamento);
        if(clienteTemHorarioNoDia){
            throw  new ValidationExeception("Cliente ja possui horario nesse dia");
        }
    }
}
