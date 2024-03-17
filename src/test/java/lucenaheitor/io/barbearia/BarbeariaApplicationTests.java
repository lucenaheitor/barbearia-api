package lucenaheitor.io.barbearia;

import lucenaheitor.io.barbearia.domain.barbeiros.Barbeiro;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BarbeariaApplicationTests {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private JacksonTester<DadosCadastroMedico> dadosCadastroMedicoJson;

	@Autowired
	private JacksonTester<DadosDetalhamentoMedico> dadosDetalhamentoMedicoJson;

	@MockBean
	private MedicoRepository repository;

	@Test
	@DisplayName("Deveria devolver codigo http 400 quando informacoes estao invalidas")
	@WithMockUser
	void cadastrar_cenario1() throws Exception {
		var response = mvc
				.perform(post("/medicos"))
				.andReturn().getResponse();

		assertThat(response.getStatus())
				.isEqualTo(HttpStatus.BAD_REQUEST.value());
	}

	@Test
	@DisplayName("Deveria devolver codigo http 200 quando informacoes estao validas")
	@WithMockUser
	void cadastrar_cenario2() throws Exception {
		var dadosCadastro = new DadosCadastroMedico(
				"Barbeiro",
				"barbeiro@gmail.com",
				"000.000.000-1",
				"123456",
				Especialidade.CORTE,

		when(repository.save(any())).thenReturn(new Barbeiro(dados));

		var response = mvc
				.perform(post("/medicos")
						.contentType(MediaType.APPLICATION_JSON)
						.content(dadosCadastroMedicoJson.write(dadosCadastro).getJson()))
				.andReturn().getResponse();

		var dadosDetalhamento = new DadosDetalhamentoMedico(
				null,
				dadosCadastro.nome(),
				dadosCadastro.email(),
				dadosCadastro.crm(),
				dadosCadastro.telefone(),
				dadosCadastro.especialidade(),
				new Endereco(dadosCadastro.endereco())
		);
		var jsonEsperado = dadosDetalhamentoMedicoJson.write(dadosDetalhamento).getJson();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
		assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
	}

	private DadosEndereco dadosEndereco() {
		return new DadosEndereco(
				"rua xpto",
				"bairro",
				"00000000",
				"Brasilia",
				"DF",
				null,
				null
		);
}
