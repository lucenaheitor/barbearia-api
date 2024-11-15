package lucenaheitor.io.barbearia.controler;

import lucenaheitor.io.barbearia.domain.barbeiros.*;
import lucenaheitor.io.barbearia.domain.clientes.AtualizationClientesDTO;
import lucenaheitor.io.barbearia.infra.service.BarbeiroService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc(addFilters = false) // Desativa os filtros de segurança
@SpringBootTest
class BarbeirosControllerTest {

    @Autowired
    private MockMvc mockMvc;

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
                .content("{\"nome\":\"Test\", " +
                        "\"email\":\"test@example.com\", " +
                        "\"cpf\":\"123.456.789-00\", " +
                        "\"telefone\":\"1234567890\"," +
                        " \"especialidade\":\"CORTE\"}"))
                        .andReturn().getResponse();

                Assertions.assertEquals(201, response.getStatus());
        }

    @Test
    @WithMockUser(username = "testuser", roles = {"ADMIN"})
    void list() throws Exception {
         List<ListagemBarbeirosDTO> barbeiros = new ArrayList<>();
            barbeiros.add(new ListagemBarbeirosDTO(1L, "Test", Especialidade.CORTE_BARBA));
          Page<ListagemBarbeirosDTO> page = new PageImpl<>(barbeiros, PageRequest.of(0, 5), barbeiros.size());
          when(service.getBarbeiro(any())).thenReturn(page);
         var response =  mockMvc.perform(get("/barbeiros")
                 .param("page", "0")
                         .param("size", "5"))
                         .andReturn().getResponse();

        Assertions.assertEquals(200, response.getStatus() );
    }

    @Test @WithMockUser(username = "testuser", roles = {"ADMIN"})
    void testDetail() throws Exception {
         DetailsBarbeiros detailsDto = new DetailsBarbeiros(
                 1L,
                 "Test Barbeiro",
                 "test@example.com",
                 "123.456.789-00",
                 "(00) 12345-789",
                 Especialidade.CORTE_BARBA
                 );
         when(service.datails(anyLong())).thenReturn(detailsDto);
         var mvcResult = mockMvc.perform(get("/barbeiros/1"))
                 .andReturn().getResponse();
         Assertions.assertEquals(200, mvcResult.getStatus()); }



    @Test
    @WithMockUser(username = "testuser", roles = {"ADMIN"})
    void update() throws Exception {
        AtualizationoBarbeirosDTO updateDto = new AtualizationoBarbeirosDTO(
                1L,
                "Test",
                "test@example.com",
                "(00) 12345-6789");
        when(service.update(any(AtualizationoBarbeirosDTO.class))).thenReturn(updateDto);
        String jsonContent = "{ \"id\": 1," +
                " \"name\": \"Test\", " +
                "\"email\": \"test2@example.com\", " +
                "\"telefone\": \"(12) 12345-6789\" }";
        var mvcResult = mockMvc.perform(put("/barbeiros")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent))
                .andReturn().getResponse();
        Assertions.assertEquals(200, mvcResult.getStatus());
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"ADMIN"})
    void delete() throws Exception {
        var mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .delete("/barbeiros/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        Assertions.assertEquals(204, mvcResult.getStatus());
    }
}
