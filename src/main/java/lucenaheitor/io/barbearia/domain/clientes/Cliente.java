package lucenaheitor.io.barbearia.domain.clientes;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "clientes")
@Entity(name = "Cliente")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    private String nome;
    private String email;
    private  String cpf;
    private String telefone;

    public Cliente(CadastroClienteDTO data) {
        this.nome = data.nome();
        this.email = data.email();
        this.cpf = data.cpf();
        this.telefone = data.telefone();
    }

    public void updateInfo(AtualizationClientesDTO data) {
        if(data.nome() != null){
           this.nome = data.nome();
        }
        if(data.email() != null){
            this.email = data.email();
        }
        if(data.telefone() != null){
            this.telefone = data.telefone();
        }
    }
}
