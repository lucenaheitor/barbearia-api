package lucenaheitor.io.barbearia.controler;

import lucenaheitor.io.barbearia.domain.barbeiros.CadastroBarbeiroDTO;
import lucenaheitor.io.barbearia.domain.clientes.CadastroClienteDTO;
import lucenaheitor.io.barbearia.infra.service.ClienteService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@AutoConfigureMockMvc(addFilters = false) // Desativa os filtros de segurança
@SpringBootTest
class ClientecontrollerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClienteService clienteService;

    @Test
    void register() throws Exception {
        CadastroClienteDTO dto = new CadastroClienteDTO(
          "TESTE",
          "Teste@test.com",
          "123.456.789-00",
          "(12) 12345-6789"
        );
        when(clienteService.createCliente(any(CadastroClienteDTO.class)))
                .thenReturn(dto);
        var  response =  mockMvc.perform(post("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\":\"Test\", " +
                                "\"email\":\"test@example.com\", " +
                                "\"cpf\":\"123.456.789-00\", " +
                                "\"telefone\":\"(12) 12345-6789\"}"))
                .andReturn().getResponse();

        Assertions.assertEquals(200, response.getStatus());

    }

    @Test
    void list() {
    }

    @Test
    void detail() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}