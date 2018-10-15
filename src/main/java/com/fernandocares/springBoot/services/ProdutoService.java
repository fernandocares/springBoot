package com.fernandocares.springBoot.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.fernandocares.springBoot.domain.Categoria;
import com.fernandocares.springBoot.domain.Produto;
import com.fernandocares.springBoot.repositories.CategoriaRepository;
import com.fernandocares.springBoot.repositories.ProdutoRepository;
import com.fernandocares.springBoot.services.exception.ObjectNotFoundException;

@Service
public class ProdutoService {
	
	@Autowired
	public ProdutoRepository repository;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public Produto find(Integer id) {
		Optional<Produto> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto nao encontrado! Id: " + id + ", Tipo: " + Produto.class.getName()));	
	}
	
	public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String ordeBy, String direction){
		PageRequest pageRequest = new PageRequest(page, linesPerPage, Direction.valueOf(direction), ordeBy);
		
		List<Categoria> categorias = categoriaRepository.findAllById(ids);
				
		return repository.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);
	}
}
