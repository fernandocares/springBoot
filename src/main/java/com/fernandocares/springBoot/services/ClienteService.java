package com.fernandocares.springBoot.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.fernandocares.springBoot.domain.Cidade;
import com.fernandocares.springBoot.domain.Cliente;
import com.fernandocares.springBoot.domain.Endereco;
import com.fernandocares.springBoot.domain.enums.TipoCliente;
import com.fernandocares.springBoot.dto.ClienteDTO;
import com.fernandocares.springBoot.dto.ClienteNewDTO;
import com.fernandocares.springBoot.repositories.CidadeRepository;
import com.fernandocares.springBoot.repositories.ClienteRepository;
import com.fernandocares.springBoot.repositories.EnderecoRepository;
import com.fernandocares.springBoot.services.exception.DataIntegrityException;
import com.fernandocares.springBoot.services.exception.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	public Cliente find(Integer id) {
		Optional<Cliente> cliente = repository.findById(id);
		
		return cliente.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto nao encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}
	
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		repository.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos());
		return obj;
	}
	
	public Cliente update(Cliente obj) {
		Cliente newObj = find(obj.getId());
		updateData(newObj, obj);
		return repository.save(newObj);
	}
	
	public void delete(Integer id) {
		find(id);
		
		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Falha ao excluir: este registro possui relação com outra tabela");
		}
	}
	
	public List<Cliente> findAll(){
		return repository.findAll();
	}
	
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String ordeBy, String direction){
		PageRequest pageRequest = new PageRequest(page, linesPerPage, Direction.valueOf(direction), ordeBy);
		
		return repository.findAll(pageRequest);
	}
	
	public Cliente fromDTO(ClienteDTO objDTO) {
		return new Cliente(objDTO.getId(), objDTO.getNome(), objDTO.getEmail(), null, null);
	}
	
	public Cliente fromDTO(ClienteNewDTO objDTO) {
		Cliente cliente = new Cliente(null, objDTO.getNome(), objDTO.getEmail(), 
				objDTO.getCpfOuCnpj(), TipoCliente.toEnum(objDTO.getTipo()));
		
		Optional<Cidade> cidade = cidadeRepository.findById(objDTO.getCidadeId());
		
		Endereco endereco = new  Endereco(null, objDTO.getLogradouro(), objDTO.getNumero(), 
				objDTO.getComplemento(), objDTO.getBairro(), objDTO.getCep(), cliente, cidade.get());
		
		cliente.getEnderecos().add(endereco);
		cliente.getTelefones().add(objDTO.getTelefone1());
		
		if(objDTO.getTelefone2() != null) {
			cliente.getTelefones().add(objDTO.getTelefone2());
		}
		
		if(objDTO.getTelefone3() != null) {
			cliente.getTelefones().add(objDTO.getTelefone3());
		}
		
		return cliente;
	}
	
	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
}
