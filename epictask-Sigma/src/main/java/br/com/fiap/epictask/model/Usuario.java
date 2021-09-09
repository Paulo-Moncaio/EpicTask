package br.com.fiap.epictask.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@Entity
public class Usuario {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "O nome é obrigatório")
	private String name;
	
	@NotBlank(message = "O email é obrigatório")
	private String email;
	
	@NotBlank(message = "A senha é obrigatoria")
	@Size(min = 8, message = "A senha deve conter pelo menos 8 caracteres!")
	private String password;
}
