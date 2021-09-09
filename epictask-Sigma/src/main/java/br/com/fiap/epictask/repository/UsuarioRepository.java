package br.com.fiap.epictask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.fiap.epictask.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	Page<Usuario> findByNameContaining(String string, Pageable pageable);
	
}
