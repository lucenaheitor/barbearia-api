package lucenaheitor.io.barbearia.domain.barbeiros;

public record DetailsBarbeiros(Long id, String nome, String email, String cpf, String trelefone, Especialidade especialidade) {

   public DetailsBarbeiros(Barbeiro barbeiro){
       this(barbeiro.getId(), barbeiro.getNome(), barbeiro.getEmail(), barbeiro.getCpf(), barbeiro.getTelefone(), barbeiro.getEspecialidade());
   }
}
