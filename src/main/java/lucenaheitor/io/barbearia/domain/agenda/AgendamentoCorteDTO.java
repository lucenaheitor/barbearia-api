package lucenaheitor.io.barbearia.domain.agenda;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lucenaheitor.io.barbearia.domain.barbeiros.Especialidade;


import java.time.LocalDateTime;

public record AgendamentoCorteDTO(
        Long idBarbeiro,
        @NotNull
        Long idCliente,

        String nomeCliente,

        String nomeBarbeiro,

        @NotNull
        @Future
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        LocalDateTime date,

        Especialidade especialidade) {

}

