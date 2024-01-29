package lucenaheitor.io.barbearia.domain.clientes;

import jakarta.validation.constraints.NotNull;

public record AtualizationClientesDTO(
        @NotNull
        Long id,
        String nome,
        String email,
        String telefone) {
}
