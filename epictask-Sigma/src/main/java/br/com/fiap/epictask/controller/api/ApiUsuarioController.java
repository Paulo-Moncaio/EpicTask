package br.com.fiap.epictask.controller.api;

import java.net.URI;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.fiap.epictask.model.Usuario;
import br.com.fiap.epictask.repository.UsuarioRepository;

@RestController
@RequestMapping("/api/usuario")
public class ApiUsuarioController {

	@Autowired
	private UsuarioRepository repository;
	
	@GetMapping
	@Cacheable("usuario")
	public Page<Usuario> index(
			@RequestParam(required = false) String name,
			@PageableDefault Pageable pageable
			) {
		
		if (name == null) 
			return repository.findAll(pageable);
		
		return repository
				.findByNameContaining(name, pageable);
	}
	
	@PostMapping
	@CacheEvict(value = "usuarios", allEntries = true)
	public ResponseEntity<Usuario> create(
			@RequestBody @Valid Usuario usuario,
			UriComponentsBuilder uriBuilder
			) {
		repository.save(usuario);
		
		URI uri = uriBuilder
				.path("api/usuario/{id}")
				.buildAndExpand(usuario.getId())
				.toUri();
		
		return ResponseEntity.created(uri).body(usuario);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Usuario>  get(@PathVariable Long id) {
		Optional<Usuario> usuario = repository.findById(id);
		
		return ResponseEntity.of(usuario);
	}
	
	@DeleteMapping("{id}")
	@CacheEvict(value = "usuarios", allEntries = true)
	public ResponseEntity<Usuario>  destroy(@PathVariable Long id) {
		Optional<Usuario> usuario = repository.findById(id);
		
		if (usuario.isEmpty()) 
			return ResponseEntity.notFound().build();
		
		repository.deleteById(id);
		return ResponseEntity.ok( usuario.get() ) ;
	}
	
	@PutMapping("{id}")
	@CacheEvict(value = "usuarios", allEntries = true)
	public ResponseEntity<Usuario> update(@PathVariable Long id, @RequestBody Usuario newUsuario) {
		Optional<Usuario> optional = repository.findById(id);
		
		if (optional.isEmpty()) 
			return ResponseEntity.notFound().build();
		
		Usuario usuario = optional.get();
		usuario.setName(newUsuario.getName());
		usuario.setEmail(newUsuario.getEmail());
		usuario.setPassword(newUsuario.getPassword());
		
		repository.save(usuario);
		return ResponseEntity.ok(usuario);
	}
	
	
	
	
	
}
