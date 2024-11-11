package lucenaheitor.io.barbearia.controler;

import lucenaheitor.io.barbearia.domain.barbeiros.Barbeiro;
import lucenaheitor.io.barbearia.domain.barbeiros.BarbeiroRepository;
import lucenaheitor.io.barbearia.domain.barbeiros.CadastroBarbeiroDTO;
import lucenaheitor.io.barbearia.domain.barbeiros.Especialidade;
import lucenaheitor.io.barbearia.infra.service.BarbeiroService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@AutoConfigureMockMvc(addFilters = false) // Desativa os filtros de segurança
@SpringBootTest
class BarbeirosControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BarbeiroRepository barbeiroRepository;

    @MockBean
    private BarbeiroService service;

     @Test
     @WithMockUser(username = "testuser", roles = {"ADMIN"})
     void register() throws Exception {
        CadastroBarbeiroDTO dto = new CadastroBarbeiroDTO(
                "Test",
                "test@example.com",
                "123.456.789-00",
                "1234567890",
                Especialidade.CORTE_BARBA );
        when(service.register(any(CadastroBarbeiroDTO.class)))
                .thenReturn(dto);
       var  response =  mockMvc.perform(post("/barbeiros")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nome\":\"Test\", \"email\":\"test@example.com\", \"cpf\":\"123.456.789-00\", \"telefone\":\"1234567890\", \"especialidade\":\"CORTE\"}"))
                        .andReturn().getResponse();

                Assertions.assertEquals(201, response.getStatus());
        }

//    @Test
//    @WithMockUser(username = "testuser", roles = {"ADMIN"})
//    void list() throws Exception {
//        List<Barbeiro> barbeiros = new ArrayList<>();
//        barbeiros.add(new Barbeiro());
//        when(service.getBarbeiro(any())).thenReturn(barbeiros);
//
//        mockMvc.perform(get("/barbeiros")
//                        .param("page", "0")
//                        .param("size", "5"))
//                .andExpect(status().isOk());
//    }

//    @Test
//    @WithMockUser(username = "testuser", roles = {"ADMIN"})
//    void detail() throws Exception {
//        Barbeiro barbeiro = new Barbeiro();
//        when(service.datails(anyLong())).thenReturn(barbeiro);
//
//        mockMvc.perform(get("/barbeiros/1"))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    @WithMockUser(username = "testuser", roles = {"ADMIN"})
//    void update() throws Exception {
//        Barbeiro barbeiro = new Barbeiro();
//        when(service.update(any())).thenReturn(barbeiro);
//
//        mockMvc.perform(put("/barbeiros")
//                        .contentType("application/json")
//                        .content("{\"nome\":\"Test\", \"email\":\"test@example.com\", \"cpf\":\"123.456.789-00\", \"telefone\":\"1234567890\", \"especialidade\":\"CORTE\"}"))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    @WithMockUser(username = "testuser", roles = {"ADMIN"})
//    void delete() throws Exception {
//        doNothing().when(service).delete(anyLong());
//
//        mockMvc.perform(delete())
//                .andExpect(status().isNoContent());
//    }
}
