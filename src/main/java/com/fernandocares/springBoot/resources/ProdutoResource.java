package com.fernandocares.springBoot.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fernandocares.springBoot.domain.Categoria;
import com.fernandocares.springBoot.domain.Produto;
import com.fernandocares.springBoot.dto.CategoriaDTO;
import com.fernandocares.springBoot.dto.ProdutoDTO;
import com.fernandocares.springBoot.resources.utils.URL;
import com.fernandocares.springBoot.services.ProdutoService;

@RestController
@RequestMapping(value="/produtos")
public class ProdutoResource {
	
	@Autowired
	private ProdutoService service;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Produto> find(@PathVariable Integer id) {
		 
		Produto obj = service.find(id);
		
		return ResponseEntity.ok().body(obj);
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<Page<ProdutoDTO>> findPage(
			@RequestParam(value="nome", defaultValue="") String nome,
			@RequestParam(value="categorias", defaultValue="") String categorias,
			@RequestParam(value="page", defaultValue="0") Integer page, 
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage, 
			@RequestParam(value="ordeBy", defaultValue="nome") String ordeBy, 
			@RequestParam(value="direction", defaultValue="ASC") String direction) {
		 
		List<Integer> ids = URL.decodeIntList(categorias);
		
		String nomeDecoded = URL.decodeParam(nome);
		
		Page<Produto> list = service.search(nomeDecoded, ids, page, linesPerPage, ordeBy, direction);
		
		Page<ProdutoDTO> listDTO = list.map(obj -> new ProdutoDTO(obj));
		
		return ResponseEntity.ok().body(listDTO);
	}
}
