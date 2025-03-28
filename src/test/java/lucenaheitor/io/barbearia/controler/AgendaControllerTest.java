package lucenaheitor.io.barbearia.controler;

import lucenaheitor.io.barbearia.domain.agenda.AgendaDeDeCorteCabelo;
import lucenaheitor.io.barbearia.domain.agenda.AgendaRepository;
import lucenaheitor.io.barbearia.domain.agenda.validacao_agenda.ValidacaoAgendamento;
import lucenaheitor.io.barbearia.domain.barbeiros.BarbeiroRepository;
import lucenaheitor.io.barbearia.domain.clientes.ClienteRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

@AutoConfigureMockMvc(addFilters = false) // Desativa os filtros de segurança
@SpringBootTest
class AgendaControllerTest {

    @Autowired
    private MockMvc mockMvc;

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
    private AgendaDeDeCorteCabelo agendaDeDeCorteCabelo;



    @Test
    void agenda_testSucefull(){


    }

    @Test
    void cancelarAtendimento() {
    }

    @Test
    void atualizaStatus() {
    }

    @Test
    void aprovaPagamento() {
    }
}