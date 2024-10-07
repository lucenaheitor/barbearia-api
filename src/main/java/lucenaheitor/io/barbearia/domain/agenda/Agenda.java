package lucenaheitor.io.barbearia.domain.agenda;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lucenaheitor.io.barbearia.domain.barbeiros.Barbeiro;
import lucenaheitor.io.barbearia.domain.clientes.Cliente;

import java.time.LocalDateTime;

@Table(name = "agendas")
@Entity(name = "Agenda")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Agenda {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private  Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "barbeiro_id")
    private Barbeiro barbeiro;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    private LocalDateTime date;

    @Column(name = "cancelamento")
    @Enumerated(EnumType.STRING)
    private Cancelamento cancelamento;

    @NotNull
    @Enumerated(EnumType.STRING)
    Status status;

    public void cancelar(Cancelamento cancelamento) {

        this.cancelamento = cancelamento;
    }
}
