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

import com.generation.blogPessoal.model.Tema;
import com.generation.blogPessoal.repository.TemaRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/tema")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TemaController {

	
	@Autowired
	private TemaRepository temaRepository;
	
	

	@GetMapping("/all")
	public ResponseEntity<List<Tema>> getAll() {
		return ResponseEntity.ok(temaRepository.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Tema> getById(@PathVariable Long id) {
		return temaRepository.findById(id).map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.notFound().build());

	}

	@GetMapping("/descricao/{descricao}")
	public ResponseEntity<List<Tema>> getByTitle(@Valid @PathVariable String descricao) {
		return ResponseEntity.ok(temaRepository.findAllByDescricaoContainingIgnoreCase(descricao));

	}

	@PostMapping("/create")
	public ResponseEntity<Tema> createPostagem(@Valid @RequestBody Tema newTema) {
		return ResponseEntity.status(HttpStatus.CREATED).body(temaRepository.save(newTema));
	}
		
	/*@PutMapping("/update")
	public ResponseEntity<Tema> updatePost(@Valid @RequestBody Tema updateTema) {
	    return temaRepository.findById(updateTema.getId())
	    		.map(resposta -> {
	    			updateTema.setPostagem(resposta.getPostagem());
	    			Tema temaAtualizado = temaRepository.save(updateTema);
	    			
	        return ResponseEntity.status(HttpStatus.OK).body(temaAtualizado);
	    }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}*/
	
	@PutMapping("/update")
	public ResponseEntity<Tema> put(@Valid @RequestBody Tema tema){
		
		Tema temaAtualizado = temaRepository.save(tema);
		return temaRepository.findById(tema.getId())
				.map(resposta -> ResponseEntity.status(HttpStatus.CREATED)
						.body(temaAtualizado))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/delete/{id}")
	public void delete(@PathVariable Long id) {
		Optional<Tema> postagem = temaRepository.findById(id);

		if (postagem.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		temaRepository.deleteById(id);
	}

}
