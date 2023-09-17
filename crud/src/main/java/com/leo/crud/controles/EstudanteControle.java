package com.leo.crud.controles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.leo.crud.excecoes.EstudanteNotFoundException;
import com.leo.crud.modelo.Estudante;
import com.leo.crud.servicos.EstudanteServico;

import jakarta.validation.Valid;

@Controller
public class EstudanteControle {
	
	@Autowired
	private EstudanteServico estudanteServico;
	
	@GetMapping("/")
	public String listarEstudantes(Model model) {
		List<Estudante> estudantes = estudanteServico.buscarTodosEstudante();
		model.addAttribute("listaEstudantes", estudantes);
		return "/listar-estudantes";
	}
	
	@PostMapping("/buscar")
	public String buscarEstudantePorNome(@Param("nome") String nome, Model model) {
		if(nome == null) {
			return "redirect:/";
		}
		List<Estudante> estudantes = estudanteServico.buscarTodosEstudantesPorNome(nome);
		model.addAttribute("listaEstudantes", estudantes);
		return "/listar-estudantes";
	}
	
	@GetMapping("/novo")
	public String novoEstudante(Model model) {
		Estudante estudante = new Estudante();
		model.addAttribute("novoEstudante", estudante);
		return "/novo-estudante";
	}
	
	@PostMapping("/gravar")
	public String gravarEstudante(@ModelAttribute("novoEstudante") @Valid Estudante estudante,
			BindingResult erros ,RedirectAttributes attributes) {
		if(erros.hasErrors()) {
			return "/novo-estudante";
		}
		estudanteServico.salvarEstudante(estudante);
		attributes.addFlashAttribute("mensagem", "Estudante salvo com sucesso!");
		return "redirect:/novo";
	}
	
	@GetMapping("/apagar/{id}")
	public String apagarEstudante(@PathVariable("id") Long id, RedirectAttributes attributes) {
		try {
			estudanteServico.apagarEstudante(id);
		} catch (EstudanteNotFoundException e) {
			attributes.addFlashAttribute("mensagemErro", e.getMessage());
		}
		return "redirect:/";
	}

	@GetMapping("/editar/{id}")
	public String editarFormulario(@PathVariable("id") Long id, RedirectAttributes attributes, Model model) {
		try {
			Estudante estudante = estudanteServico.buscarEstudantePorId(id);
			model.addAttribute("objetoEstudante", estudante);
			return "/editar-estudante";
		} catch (EstudanteNotFoundException e) {
			attributes.addFlashAttribute("mensagemErro", e.getMessage());
		}
		return "redirect:/";
	}
	
	@PostMapping("/editar/{id}")
	public String editarEstudante(@PathVariable("id") Long id, 
			@ModelAttribute("objetoEstudante") @Valid Estudante estudante,
			BindingResult erros) {
		
		if(erros.hasErrors()) {
			estudante.setId(id);
			return "/editar-estudante";
		}
		estudanteServico.editarEstudante(estudante);
		return "redirect:/";
	}
}
