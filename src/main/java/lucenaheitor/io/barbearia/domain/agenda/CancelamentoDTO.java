package lucenaheitor.io.barbearia.domain.agenda;

import jakarta.validation.constraints.NotNull;

public record CancelamentoDTO(
        @NotNull
        Long idAgenda,
        @NotNull
        Cancelamento  cancelamento) {
}
