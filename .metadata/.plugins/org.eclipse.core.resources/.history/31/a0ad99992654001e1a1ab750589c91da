package com.leo.crud.servicos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leo.crud.modelo.Estudante;
import com.leo.crud.repositorios.EstudanteRepositorio;

@Service
public class EstudanteServico {
	
	@Autowired
	private EstudanteRepositorio repo;
	
	public Estudante salvarEstudante(Estudante estudante) {
		return repo.save(estudante);
	}
	
	public List<Estudante> buscarTodosEstudante() {
		return repo.findAll();
	}
	
	

}
