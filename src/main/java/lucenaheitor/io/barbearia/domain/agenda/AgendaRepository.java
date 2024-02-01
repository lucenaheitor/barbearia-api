package lucenaheitor.io.barbearia.domain.agenda;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface AgendaRepository extends JpaRepository<Agenda, Long>{
    boolean existsBarbeiroLivreNoHorario(Long idBarbeiro, LocalDateTime date);

    boolean existsByClienteIfAndDateBetween(Long idCliente, LocalDateTime horarioAbertura, LocalDateTime horarioFechamento);
}
