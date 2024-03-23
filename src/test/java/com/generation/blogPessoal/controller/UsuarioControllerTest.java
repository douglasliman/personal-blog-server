package com.generation.blogPessoal.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.generation.blogPessoal.model.Usuario;
import com.generation.blogPessoal.repository.UsuarioRepository;
import com.generation.blogPessoal.service.UsuarioService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioControllerTest {

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@BeforeAll
	void start() {
		usuarioRepository.deleteAll();

		usuarioService.cadastrarUsuario(new Usuario(0L, "Root", "root@root.com", "rootroot", "-"));
	}

	@Test
	@DisplayName("Cadastrar um Usuário")
	public void deveCriarUmUsuario() {

		HttpEntity<Usuario> corpoDaRequisicao = new HttpEntity<Usuario>(
				new Usuario(0L, "Douglas", "Douglas@root.com", "123456789", " - "));

		ResponseEntity<Usuario> corpoDaResposta = testRestTemplate.exchange("/usuarios/cadastrar", HttpMethod.POST,
				corpoDaRequisicao, Usuario.class);

		assertEquals(HttpStatus.CREATED, corpoDaResposta.getStatusCode());

	}

	@Test
	@DisplayName("Não deve permitir duplicação do Usuário")
	public void naoDeveDuplicarUsuario() {
		usuarioService.cadastrarUsuario(new Usuario(0L, "Douglas", "Douglas@root.com", "123456789", " - "));

		HttpEntity<Usuario> corpoDaRequisicao = new HttpEntity<Usuario>(
				new Usuario(0L, "Douglas", "Douglas@root.com", "123456789", " - "));

		ResponseEntity<Usuario> corpoDaResposta = testRestTemplate.exchange("/usuarios/cadastrar", HttpMethod.POST,
				corpoDaRequisicao, Usuario.class);

		assertEquals(HttpStatus.BAD_REQUEST, corpoDaResposta.getStatusCode());
	}

	@Test
	@DisplayName("Atualizar um Usuário")
	public void deveAtualizarUmUsuario() {

		Optional<Usuario> usuarioCadastro = usuarioService.cadastrarUsuario(
				new Usuario(0L, "Juliana Andrews Ramos", "juliana_ramos@email.com.br", "Juliana123", "-"));

		Usuario usuarioUpdate = new Usuario(usuarioCadastro.get().getId(), "Juliana Andrews Ramos",
				"juliana_ramos@email.com.br", "juliana123", "-");

		HttpEntity<Usuario> corpoDaRequisicao = new HttpEntity<Usuario>(usuarioUpdate);

		ResponseEntity<Usuario> corpoDaResposta = testRestTemplate
				.withBasicAuth("root@root.com", "rootroot")
				.exchange("/usuarios/atualizar", HttpMethod.PUT, corpoDaRequisicao, Usuario.class);

		assertEquals(HttpStatus.OK, corpoDaResposta.getStatusCode());
	}
	
	

	@Test
	@DisplayName("Listar todos os Usuarios")
	public void deveMostrarTodosUsuarios() {

		usuarioService.cadastrarUsuario(new Usuario(0L, "Douglas", "Douglas@root.com", "123456789", " - "));

		usuarioService.cadastrarUsuario(new Usuario(0L, "marcilinho", "marcilinho@root.com", "123456789", " - "));

		ResponseEntity<String> resposta = testRestTemplate
				.withBasicAuth("root@root.com", "rootroot")
				.exchange("/usuarios/all", HttpMethod.GET, null, String.class);

		assertEquals(HttpStatus.OK, resposta.getStatusCode());
	}
	

	  
}
