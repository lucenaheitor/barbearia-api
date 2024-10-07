package lucenaheitor.io.barbearia.domain.agenda.validacao_agenda;


import lucenaheitor.io.barbearia.domain.agenda.AgendamentoCorteDTO;

import lucenaheitor.io.barbearia.infra.exception.ValidationExeception;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
@Component
public class ValidadorHoraDeFuncionamentoBarbearia implements  ValidacaoAgendamento{
    @Override
    public void validar(AgendamentoCorteDTO data) {
        LocalDateTime dataHoraAtendimento = data.getDate();

        // Verificar se é domingo
        if (dataHoraAtendimento.getDayOfWeek() == DayOfWeek.SUNDAY) {
            throw new ValidationExeception("Barbearia só funciona de SEG a SAB");
        }

        // Verificar o horário de funcionamento
        int horarioDeFuncionamento = dataHoraAtendimento.getHour();
        if (horarioDeFuncionamento < 8 || horarioDeFuncionamento >= 21) {
            throw new ValidationExeception("Barbearia só funciona das 8 da manhã às 21 da noite");
        }

        // Verificar o horário de agendamento
        if (horarioDeFuncionamento >= 20) {
            throw new ValidationExeception("Só é possível agendar até as 20h");
        }

    }
}
