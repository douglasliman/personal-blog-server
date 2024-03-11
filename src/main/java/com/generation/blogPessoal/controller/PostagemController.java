package com.generation.blogPessoal.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.generation.blogPessoal.model.Postagem;
import com.generation.blogPessoal.repository.PostagemRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/postagens")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PostagemController {

	@Autowired
	private PostagemRepository postagemRepository;

	@GetMapping("/all")
	public ResponseEntity<List<Postagem>> getAll() {
		return ResponseEntity.ok(postagemRepository.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Postagem> getById(@PathVariable Long id) {
		return postagemRepository.findById(id).map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.notFound().build());

	}

	@GetMapping("/title/{title}")
	public ResponseEntity<List<Postagem>> getByTitle(@Valid @PathVariable String title) {
		return ResponseEntity.ok(postagemRepository.findAllByTituloContainingIgnoreCase(title));

	}

	@PostMapping("/post")
	public ResponseEntity<Postagem> createPostagem(@Valid @RequestBody Postagem newPost) {
		return ResponseEntity.status(HttpStatus.CREATED).body(postagemRepository.save(newPost));
	}
		
	@PutMapping("/update")
	public ResponseEntity<Postagem> updatePost(@Valid @RequestBody Postagem updatePost) {
	    return postagemRepository.findById(updatePost.getId())
	    		.map(resposta -> {updatePost.setCreatedAt(resposta.getCreatedAt());
	    		
	        Postagem postagemAtualizada = postagemRepository.save(updatePost);
	        return ResponseEntity.status(HttpStatus.OK).body(postagemAtualizada);
	    }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}


	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/delete/{id}")
	public void delete(@PathVariable Long id) {
		Optional<Postagem> postagem = postagemRepository.findById(id);

		if (postagem.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		postagemRepository.deleteById(id);
	}

}
