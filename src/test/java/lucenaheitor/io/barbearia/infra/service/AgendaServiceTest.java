package lucenaheitor.io.barbearia.service;

import jakarta.validation.ValidationException;
import lucenaheitor.io.barbearia.domain.agenda.*;
import lucenaheitor.io.barbearia.domain.agenda.validacao_agenda.ValidacaoAgendamento;
import lucenaheitor.io.barbearia.domain.barbeiros.Barbeiro;
import lucenaheitor.io.barbearia.domain.barbeiros.BarbeiroRepository;
import lucenaheitor.io.barbearia.domain.barbeiros.Especialidade;
import lucenaheitor.io.barbearia.domain.clientes.Cliente;
import lucenaheitor.io.barbearia.domain.clientes.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AgendaServiceTest {

    @Mock
    private ClienteRepository clienteRespository;

    @Mock
    private BarbeiroRepository barbeiroRespository;

    @Mock
    private AgendaRepository agendaRepository;

    @Mock
    private ModelMapper modelMapper;


    @Mock
    private List<ValidacaoAgendamento> validadores;


    @InjectMocks
    private AgendaDeDeCorteCabelo agendaService;

    private AgendamentoCorteDTO data;

    @BeforeEach
    void setUp() {
        data = new AgendamentoCorteDTO();
        data.setIdCliente(1L);
        data.setIdBarbeiro(1L);
    }

    @Test
    public void agendarClienteInvalido() {
        when(clienteRespository.existsById(data.getIdCliente())).thenReturn(false);

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            agendaService.agendar(data);
        });

        assertEquals("Id do cliente inválido!", exception.getMessage());
    }

    @Test
    public void agendarBarbeiroInvalido() {
        when(clienteRespository.existsById(data.getIdCliente())).thenReturn(true);
        when(barbeiroRespository.existsById(data.getIdBarbeiro())).thenReturn(false);

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            agendaService.agendar(data);
        });

        assertEquals("Id do barbeiro é inválido", exception.getMessage());
    }

    @Test
    public void agendarSucesso() {
        // Configuração dos dados de teste
        Long idCliente = 1L;
        Long idBarbeiro = 2L;
        AgendamentoCorteDTO data = new AgendamentoCorteDTO(idCliente, idBarbeiro, LocalDateTime.now(), null, null);

        Cliente clienteMock = new Cliente();
        Barbeiro barbeiroMock = new Barbeiro();
        Agenda agendaMock = new Agenda(
                1L,
                barbeiroMock,
                clienteMock,
                LocalDateTime.now(),
                null,
                Status.CONFIRMADO
        );
        DetalhamentoCorteDeCabelo detalhamentoMock = new DetalhamentoCorteDeCabelo(agendaMock);

        // Configuração dos mocks
        when(clienteRespository.existsById(idCliente)).thenReturn(true);
        when(barbeiroRespository.existsById(idBarbeiro)).thenReturn(true);
        when(clienteRespository.getReferenceById(idCliente)).thenReturn(clienteMock);
        when(modelMapper.map(data, Agenda.class)).thenReturn(agendaMock);
        when(agendaRepository.save(any(Agenda.class))).thenReturn(agendaMock);
        doNothing().when(validadores).forEach(any());
        when(modelMapper.map(agendaMock, DetalhamentoCorteDeCabelo.class)).thenReturn(detalhamentoMock);

        // Execução do método a ser testado
        DetalhamentoCorteDeCabelo detalhamento = agendaService.agendar(data);

        // Verificações
        assertNotNull(detalhamento); // Verifica se o detalhamento retornado não é nulo
        verify(clienteRespository, times(1)).existsById(idCliente); // Verifica se o método existsById foi chamado
        verify(barbeiroRespository, times(1)).existsById(idBarbeiro); // Verifica se o barbeiro foi verificado
        verify(clienteRespository, times(1)).getReferenceById(idCliente); // Verifica se o cliente foi buscado
        verify(modelMapper, times(1)).map(data, Agenda.class); // Verifica se o mapeamento foi feito corretamente
        verify(validadores, times(1)).forEach(any()); // Verifica se os validadores foram executados
        verify(agendaRepository, times(1)).save(agendaMock); // Verifica se a agenda foi salva corretamente
    }
}