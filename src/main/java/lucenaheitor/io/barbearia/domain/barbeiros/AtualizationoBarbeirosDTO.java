package lucenaheitor.io.barbearia.domain.barbeiros;

import jakarta.validation.constraints.NotNull;

public record AtualizationoBarbeirosDTO(
        @NotNull
        Long id,
        String nome,
        String email,
        String telefone) {
}
