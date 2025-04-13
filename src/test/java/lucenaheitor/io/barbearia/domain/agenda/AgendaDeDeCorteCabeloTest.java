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
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import jakarta.validation.ValidationException;

import javax.annotation.meta.When;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    private Barbeiro barbeiro;

    @Mock
    private Cliente cliente;

    @InjectMocks
    private AgendaDeDeCorteCabelo agendaService;

    private AgendamentoCorteDTO agendamentoCorteDTO;
    private Agenda agendaMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        agendamentoCorteDTO = new AgendamentoCorteDTO(1L, 1L, LocalDateTime.now().plusDays(1), null, Status.CONFIRMADO);
        agendaMock = new Agenda(1L, new Barbeiro(), new Cliente(), LocalDateTime.now(), null , Status.REALIZADO);
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
        // Criando um DTO com ID válido
        CancelamentoDTO dto = new CancelamentoDTO(1L, Cancelamento.BARBEIRO_CANCELOU);

        // Criando uma agenda simulada
        Agenda agenda = mock(Agenda.class);

        // Configuração dos mocks
        when(agendaRepository.existsById(dto.idAgenda())).thenReturn(true);
        when(agendaRepository.getReferenceById(dto.idAgenda())).thenReturn(agenda);

        // Chamando o método de serviço
        agendaService.cancelar(dto);

        // Verificando se os métodos foram chamados corretamente
        verify(agendaRepository).existsById(dto.idAgenda());
        verify(agendaRepository).getReferenceById(dto.idAgenda());
        verify(agenda).cancelar(dto.cancelamento());
    }

    @Test
    void deveAtualizarStatusComSucesso() {
        // Arrange
        Long id = 1L;
        StatusDto dto = new StatusDto();
        dto.setStatus(Status.REALIZADO);

        DetalhamentoCorteDeCabelo detalheMock = new DetalhamentoCorteDeCabelo();

        when(agendaRepository.findAgendaById(id)).thenReturn(Optional.of(agendaMock));
        when(modelMapper.map(any(Agenda.class), eq(DetalhamentoCorteDeCabelo.class)))
                .thenReturn(detalheMock);

        // Simulamos a atualização do status no repositório
        doAnswer(invocation -> {
            agendaMock.setStatus(dto.getStatus());
            return null;
        }).when(agendaRepository).updateStatus(dto.getStatus(), agendaMock);

        // Act
        DetalhamentoCorteDeCabelo resultado = agendaService.atualizaStatus(id, dto);

        // Assert
        assertEquals(Status.REALIZADO, agendaMock.getStatus());
        assertEquals(detalheMock, resultado);

        verify(agendaRepository).findAgendaById(id);
        verify(agendaRepository).updateStatus(dto.getStatus(), agendaMock);
        verify(modelMapper).map(agendaMock, DetalhamentoCorteDeCabelo.class);
    }

    @Test
    public void testAprovaPagamentoPedido() {
        // Arrange
        Long id = 1L;

        when(agendaRepository.findAgendaById(id)).thenReturn(Optional.of(agendaMock));

        // Act
        agendaService.aprovaPagamentoPedido(id);

        // Assert
        assertEquals(Status.PAGO, agendaMock.getStatus());
        verify(agendaRepository).findAgendaById(id);
        verify(agendaRepository).updateStatus(Status.PAGO, agendaMock);
    }
}
