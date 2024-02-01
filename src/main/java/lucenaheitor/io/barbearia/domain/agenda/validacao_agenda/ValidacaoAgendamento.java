package lucenaheitor.io.barbearia.domain.agenda.validacao_agenda;

import lucenaheitor.io.barbearia.domain.agenda.AgendamentoCorteDTO;

public interface ValidacaoAgendamento {

    void  validar(AgendamentoCorteDTO data);
}
