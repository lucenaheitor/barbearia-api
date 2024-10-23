package lucenaheitor.io.barbearia.service;

import jakarta.validation.ValidationException;
import lucenaheitor.io.barbearia.domain.agenda.*;
import lucenaheitor.io.barbearia.domain.agenda.validacao_agenda.ValidacaoAgendamento;
import lucenaheitor.io.barbearia.domain.barbeiros.BarbeiroRepository;
import lucenaheitor.io.barbearia.domain.clientes.Cliente;
import lucenaheitor.io.barbearia.domain.clientes.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

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
        when(clienteRespository.existsById(data.getIdCliente())).thenReturn(true);
        when(barbeiroRespository.existsById(data.getIdBarbeiro())).thenReturn(true);
        when(clienteRespository.getReferenceById(data.getIdCliente())).thenReturn(new Cliente());
        when(modelMapper.map(data, Agenda.class)).thenReturn(new Agenda());

        doNothing().when(validadores).forEach(any());
        doNothing().when(agendaRepository).save(any());

        DetalhamentoCorteDeCabelo detalhamento = agendaService.agendar(data);

        assertNotNull(detalhamento);
    }
}