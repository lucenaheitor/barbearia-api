package lucenaheitor.io.barbearia.domain.barbeiros;

import jakarta.validation.constraints.NotBlank;

public record ListagemBarbeirosDTO(Long id, String nome,  Especialidade especialidade) {

     public ListagemBarbeirosDTO(Barbeiro barbeiro){
         this(barbeiro.getId(), barbeiro.getNome(), barbeiro.getEspecialidade());
     }

}
