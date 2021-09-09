package br.com.fiap.epictask.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.fiap.epictask.model.Usuario;
import br.com.fiap.epictask.repository.UsuarioRepository;

@Controller
public class UsuarioController {

	@Autowired
	private UsuarioRepository repository;
	
	@GetMapping("/usuario")
	public ModelAndView index() {
		ModelAndView modelAndView = new ModelAndView("usuarios");
		List<Usuario> usuarios = repository.findAll();
		modelAndView.addObject("usuarios", usuarios);
		return modelAndView;
	}
	
	@RequestMapping("/cadastro")
	public String cadastro( Usuario usuario) {
		return "cadastro";
	}
	
	@PostMapping("/usuario")
	public String save(@Valid Usuario usuario, BindingResult result) {
		if(result.hasErrors()) return "cadastro";
		repository.save(usuario);
		return "usuarios";
	}
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
}
