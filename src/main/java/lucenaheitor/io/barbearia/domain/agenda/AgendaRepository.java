package lucenaheitor.io.barbearia.domain.agenda;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

public interface AgendaRepository extends JpaRepository<Agenda, Long>{

    boolean existsByClienteIdAndDateBetween(Long idCliente, LocalDateTime horarioAbertura, LocalDateTime horarioFechamento);

    boolean existsByBarbeiroIdAndDateAndCancelamentoIsNull(Long idBarbeiro, LocalDateTime date);
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update Agenda a set a.status = :status where a = :agenda")
    void updateStatus(Status status, Agenda agenda);

    @Query("SELECT a FROM Agenda a LEFT JOIN FETCH a.barbeiro LEFT JOIN FETCH a.cliente WHERE a.id = :id")
    Optional<Agenda> findAgendaById(Long id);
}
