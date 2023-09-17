package com.leo.crud.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.leo.crud.modelo.Estudante;

public interface EstudanteRepositorio extends JpaRepository<Estudante, Long>{
	List<Estudante> findByNomeContainingIgnoreCase(String nome);
}
