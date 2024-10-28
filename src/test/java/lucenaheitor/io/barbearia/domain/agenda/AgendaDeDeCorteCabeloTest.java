package lucenaheitor.io.barbearia.domain.agenda;



import lucenaheitor.io.barbearia.domain.agenda.*;
import lucenaheitor.io.barbearia.domain.agenda.validacao_agenda.ValidacaoAgendamento;
import lucenaheitor.io.barbearia.domain.agenda.validation_cancel.CancelamentoAgenda;
import lucenaheitor.io.barbearia.domain.barbeiros.Barbeiro;
import lucenaheitor.io.barbearia.domain.barbeiros.BarbeiroRepository;
import lucenaheitor.io.barbearia.domain.clientes.Cliente;
import lucenaheitor.io.barbearia.domain.clientes.ClienteRepository;
import lucenaheitor.io.barbearia.infra.exception.ValidationExeception;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import jakarta.validation.ValidationException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AgendaDeDeCorteCabeloTest {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private BarbeiroRepository barbeiroRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private AgendaRepository agendaRepository;

    @Mock
    private List<ValidacaoAgendamento> validadores;

    @Mock
    private List<CancelamentoAgenda> cancelamentoValidacao;

    @InjectMocks
    private AgendaDeDeCorteCabelo agendaService;

    private AgendamentoCorteDTO agendamentoCorteDTO;
    private Agenda agendaMock;

    @BeforeEach
    void setUp() {
        agendamentoCorteDTO = new AgendamentoCorteDTO(1L, 1L, LocalDateTime.now().plusDays(1), null, Status.CONFIRMADO);
        agendaMock = new Agenda(1L, new Barbeiro(), new Cliente(), LocalDateTime.now(), null, Status.CONFIRMADO);
    }

    @Test
    public void testAgendarSucesso() {
        when(clienteRepository.existsById(anyLong())).thenReturn(true);
        when(barbeiroRepository.existsById(anyLong())).thenReturn(true);
        when(clienteRepository.getReferenceById(anyLong())).thenReturn(new Cliente());
        when(barbeiroRepository.getReferenceById(anyLong())).thenReturn(new Barbeiro());
        when(modelMapper.map(any(AgendamentoCorteDTO.class), eq(Agenda.class))).thenReturn(agendaMock);
        when(agendaRepository.save(any(Agenda.class))).thenReturn(agendaMock);
        when(modelMapper.map(any(Agenda.class), eq(DetalhamentoCorteDeCabelo.class))).thenReturn(new DetalhamentoCorteDeCabelo());

        DetalhamentoCorteDeCabelo result = agendaService.agendar(agendamentoCorteDTO);

        assertNotNull(result);
        verify(clienteRepository, times(1)).existsById(anyLong());
        verify(barbeiroRepository, times(1)).existsById(anyLong());
        verify(agendaRepository, times(1)).save(any(Agenda.class));
    }

    @Test
    public void testAgendarClienteInvalido() {
        when(clienteRepository.existsById(anyLong())).thenReturn(false);

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            agendaService.agendar(agendamentoCorteDTO);
        });

        assertEquals("Id do cliente inválido!", exception.getMessage());
    }

    @Test
    public void testAgendarBarbeiroInvalido() {
        when(clienteRepository.existsById(anyLong())).thenReturn(true);
        when(barbeiroRepository.existsById(anyLong())).thenReturn(false);

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            agendaService.agendar(agendamentoCorteDTO);
        });

        assertEquals("Id do barbeiro é inválido", exception.getMessage());
    }

    @Test
    public void testCancelarAgendamento() {
        CancelamentoDTO cancelamentoDTO = new CancelamentoDTO(1L, Cancelamento.BARBEIRO_CANCELOU);

        when(agendaRepository.existsById(anyLong())).thenReturn(true);
        when(agendaRepository.getReferenceById(anyLong())).thenReturn(agendaMock);

        agendaService.cancelar(cancelamentoDTO);

        verify(agendaRepository, times(1)).existsById(anyLong());
        verify(agendaRepository, times(1)).getReferenceById(anyLong());
        verify(agendaRepository, times(1)).save(any(Agenda.class));
    }

    @Test
    public void testAtualizaStatus() {
        StatusDto statusDto = new StatusDto(Status.PAGO);

        when(agendaRepository.findAgendaById(anyLong())).thenReturn(Optional.of(agendaMock));
        when(agendaRepository.save(any(Agenda.class))).thenReturn(agendaMock);

        DetalhamentoCorteDeCabelo result = agendaService.atualizaStatus(1L, statusDto);

        assertNotNull(result);
        assertEquals(Status.PAGO, agendaMock.getStatus());
        verify(agendaRepository, times(1)).findAgendaById(anyLong());
        verify(agendaRepository, times(1)).save(any(Agenda.class));
    }

    @Test
    public void testAprovaPagamentoPedido() {
        when(agendaRepository.findAgendaById(anyLong())).thenReturn(Optional.of(agendaMock));

        agendaService.aprovaPagamentoPedido(1L);

        assertEquals(Status.PAGO, agendaMock.getStatus());
        verify(agendaRepository, times(1)).findAgendaById(anyLong());
        verify(agendaRepository, times(1)).save(any(Agenda.class));
    }
}
