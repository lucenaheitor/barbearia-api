package lucenaheitor.io.barbearia.controler;

import lucenaheitor.io.barbearia.domain.barbeiros.Barbeiro;
import lucenaheitor.io.barbearia.domain.barbeiros.BarbeiroRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class BarbeirosControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BarbeiroRepository barbeiroRepository;

    @Test
    @WithMockUser(username = "testuser", roles = {"ADMIN"})
    public void testCadastrar() throws Exception {
        mockMvc.perform(post("/barbeiros")
                        .contentType("application/json")
                        .content("{\"nome\":\"Test\", \"email\":\"test@example.com\", \"cpf\":\"123.456.789-00\", \"telefone\":\"1234567890\", \"especialidade\":\"CORTE\"}"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"ADMIN"})
    public void testDeletar() throws Exception {
        mockMvc.perform(delete("/barbeiros/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"ADMIN"})
    public void  testListar() throws Exception {
        List<Barbeiro> barbeiros = new ArrayList<>();

        mockMvc.perform(get("/barbeiros"))
                .andExpect(status().isOk());

    }

    @Test
    @WithMockUser(username = "testuser", roles = {"ADMIN"})
    public void testDelete() throws Exception {
        mockMvc.perform(delete("/barbeiros/1"))
                .andExpect(status().isNoContent());
    }

}