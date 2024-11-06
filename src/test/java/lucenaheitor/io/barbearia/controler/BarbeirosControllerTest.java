package lucenaheitor.io.barbearia.controler;

import lucenaheitor.io.barbearia.domain.agenda.DetalhamentoCorteDeCabelo;
import lucenaheitor.io.barbearia.domain.barbeiros.*;
import lucenaheitor.io.barbearia.controler.BarbeirosController;
import lucenaheitor.io.barbearia.infra.service.BarbeiroService;
import lucenaheitor.io.barbearia.infra.security.TokenService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = BarbeirosController.class)
@AutoConfigureMockMvc(addFilters = false) // Desativa filtros de segurança
@ExtendWith(MockitoExtension.class)
@AutoConfigureJsonTesters
class BarbeirosControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JacksonTester<CadastroBarbeiroDTO> cadastroBarbeiroDTOJson;

    @Autowired
    private JacksonTester<DetailsBarbeiros> detailsBarbeirosJson;

    @MockBean
    private BarbeiroRepository barbeiroRepository;

    @MockBean
    private BarbeiroService barbeiroService;

    @MockBean
    private TokenService tokenService; // Mock do TokenService

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testCadastrar() throws Exception {
        var cadastroBarbeiroDTO = new CadastroBarbeiroDTO(
                "teste",
                "barbeiro@barbearia.com",
                "61999999999",
                "(11)123456789",
                Especialidade.CORTE_BARBA
        );

        when(barbeiroRepository.save(any())).thenReturn(new Barbeiro(cadastroBarbeiroDTO));

        var response = mockMvc.perform(post("/barbeiros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(cadastroBarbeiroDTOJson.write(cadastroBarbeiroDTO).getJson()))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        var details = new DetailsBarbeiros(
                null,
                cadastroBarbeiroDTO.nome(),
                cadastroBarbeiroDTO.email(),
                cadastroBarbeiroDTO.cpf(),
                cadastroBarbeiroDTO.telefone(),
                cadastroBarbeiroDTO.especialidade()
        );

        var jsonEsperado = detailsBarbeirosJson.write(details).getJson();
        assertEquals(jsonEsperado, response.getContentAsString());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testDeletar() throws Exception {
        doNothing().when(barbeiroRepository).deleteById(anyLong());

        mockMvc.perform(delete("/barbeiros/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testListar() throws Exception {
        List<Barbeiro> barbeiros = new ArrayList<>();
        barbeiros.add(new Barbeiro());
        when(barbeiroRepository.findAll()).thenReturn(barbeiros);

        mockMvc.perform(get("/barbeiros"))
                .andExpect(status().isOk());
    }
}