package lucenaheitor.io.barbearia.domain.agenda;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface AgendaRepository extends JpaRepository<Agenda, Long>{

    boolean existsByClienteIdAndDateBetween(Long idCliente, LocalDateTime horarioAbertura, LocalDateTime horarioFechamento);

    boolean existsByBarbeiroIdAndDateAndCancelamentoIsNull(Long idBarbeiro, LocalDateTime date);
}
