package lucenaheitor.io.barbearia.domain.clientes;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.intellij.lang.annotations.RegExp;

public record CadastroClienteDTO(
        @NotBlank
        String nome,
        @NotBlank
        @Email
        String email,
        @NotBlank
        @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}")
        String cpf,
        @NotBlank
        @Pattern(regexp="(^$|\\(\\d{2}\\)\\s\\d{4,5}-\\d{4})")
        String telefone) {
}
