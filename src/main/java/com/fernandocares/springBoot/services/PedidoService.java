package com.fernandocares.springBoot.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fernandocares.springBoot.domain.ItemPedido;
import com.fernandocares.springBoot.domain.PagamentoComBoleto;
import com.fernandocares.springBoot.domain.Pedido;
import com.fernandocares.springBoot.domain.enums.EstadoPagamento;
import com.fernandocares.springBoot.repositories.ItemPedidoRepository;
import com.fernandocares.springBoot.repositories.PagamentoRepository;
import com.fernandocares.springBoot.repositories.PedidoRepository;
import com.fernandocares.springBoot.repositories.ProdutoRepository;
import com.fernandocares.springBoot.services.exception.ObjectNotFoundException;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository repo;

	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	public Pedido find(Integer id) {
		Optional<Pedido> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto nao encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));	
	}
	
	public Pedido insert(Pedido obj) {
		
		obj.setId(null);
		obj.setInstante(new Date());
		obj.getPagamento().setEstadoPagamento(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		
		if(obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
		}
		
		obj = repo.save(obj);
		
		pagamentoRepository.save(obj.getPagamento());
		
		for(ItemPedido item : obj.getItens()) {
			
			item.setDesconto(0.0);
			item.setPreco(produtoService.find(item.getProduto().getId()).getPreco());
			item.setPedido(obj);
		}
		
		itemPedidoRepository.saveAll(obj.getItens());
		
		return obj;
	}
}
