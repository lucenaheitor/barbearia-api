package lucenaheitor.io.barbearia.domain.barbeiros;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "barbeiros")
@Entity( name = "Barbeiro")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Barbeiro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    private String nome;
    private String email;
    private  String cpf;
    private String telefone;
    @Enumerated(EnumType.STRING)
    private Especialidade especialidade;


    public Barbeiro(CadastroBarbeiroDTO data) {
        this.nome = data.nome();
        this.email = data.email();
        this.cpf = data.cpf();
        this.telefone = data.telefone();
        this.especialidade = data.especialidade();

    }

    public void updateInfo(AtualizationoBarbeirosDTO data) {
        if (data.nome() != null){
            this.nome = data.nome();
        }
        if (data.email() != null){
            this.email = data.email();
        }
        if (data.telefone() != null){
            this.telefone = data.telefone();
        }
    }
}
