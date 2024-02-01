package lucenaheitor.io.barbearia.domain.agenda.validacao_agenda;

import lucenaheitor.io.barbearia.domain.agenda.AgendamentoCorteDTO;
import lucenaheitor.io.barbearia.infra.exception.ValidationExeception;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class ValidadorHoraDeFuncionamentoBarbearia implements  ValidacaoAgendamento{
    @Override
    public void validar(AgendamentoCorteDTO data) {
        LocalDateTime dataHoraAtendimento = data.date();


        if (dataHoraAtendimento.getDayOfWeek() == DayOfWeek.SUNDAY){
            throw  new ValidationExeception("Barbearia só funciona de SEG a SAB");
        }

        var horarioDefuncionamento = dataHoraAtendimento.getHour();

        if (horarioDefuncionamento < 8 && horarioDefuncionamento >= 20){
            throw  new ValidationExeception(("Barbearia só funciona das  21 da noite"));
        }

    }
}
