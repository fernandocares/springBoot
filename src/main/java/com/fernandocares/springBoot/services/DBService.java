package com.fernandocares.springBoot.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.fernandocares.springBoot.domain.Categoria;
import com.fernandocares.springBoot.domain.Cidade;
import com.fernandocares.springBoot.domain.Cliente;
import com.fernandocares.springBoot.domain.Endereco;
import com.fernandocares.springBoot.domain.Estado;
import com.fernandocares.springBoot.domain.ItemPedido;
import com.fernandocares.springBoot.domain.Pagamento;
import com.fernandocares.springBoot.domain.PagamentoComBoleto;
import com.fernandocares.springBoot.domain.PagamentoComCartao;
import com.fernandocares.springBoot.domain.Pedido;
import com.fernandocares.springBoot.domain.Produto;
import com.fernandocares.springBoot.domain.enums.EstadoPagamento;
import com.fernandocares.springBoot.domain.enums.Perfil;
import com.fernandocares.springBoot.domain.enums.TipoCliente;
import com.fernandocares.springBoot.repositories.CategoriaRepository;
import com.fernandocares.springBoot.repositories.CidadeRepository;
import com.fernandocares.springBoot.repositories.ClienteRepository;
import com.fernandocares.springBoot.repositories.EnderecoRepository;
import com.fernandocares.springBoot.repositories.EstadoRepository;
import com.fernandocares.springBoot.repositories.ItemPedidoRepository;
import com.fernandocares.springBoot.repositories.PagamentoRepository;
import com.fernandocares.springBoot.repositories.PedidoRepository;
import com.fernandocares.springBoot.repositories.ProdutoRepository;

@Service
public class DBService {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public void instantiateDatabase() throws ParseException {
		
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		Categoria cat3 = new Categoria(null, "Cama mesa e banho");
		Categoria cat4 = new Categoria(null, "Eletrônicos");
		Categoria cat5 = new Categoria(null, "Jardinagem");
		
		Produto p1 = new  Produto(null, "Computador", 2000.00);
		Produto p2 = new  Produto(null, "Impressora", 800.00);
		Produto p3 = new  Produto(null, "Mouse", 80.00);
		Produto p4 = new Produto(null, "Mesa de Escritório", 300.00);
		Produto p5 = new Produto(null, "Toalha", 50.00);
		Produto p6 = new Produto(null, "Abajour", 100.00);
		Produto p7 = new Produto(null, "Pendente", 180.00);
		Produto p8 = new Produto(null, "Shampoo", 90.00);
		
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2, p4));
		cat3.getProdutos().addAll(Arrays.asList(p5, p6));
		cat4.getProdutos().addAll(Arrays.asList(p1, p2, p3, p7));
		cat5.getProdutos().addAll(Arrays.asList(p5, p8));
		
		p1.getCategorias().addAll(Arrays.asList(cat1, cat4));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2, cat4));
		p3.getCategorias().addAll(Arrays.asList(cat1, cat4));
		p4.getCategorias().addAll(Arrays.asList(cat2));
		p5.getCategorias().addAll(Arrays.asList(cat3));
		p6.getCategorias().addAll(Arrays.asList(cat3));
		p7.getCategorias().addAll(Arrays.asList(cat4));
		p8.getCategorias().addAll(Arrays.asList(cat5));
		
		
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5));
		
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3, p2, p3, p4, p5, p6, p7, p8));
		
		Estado est1 = new Estado( null, "Minas Gerais");
		Estado est2 = new Estado( null, "São Paulo");
		
		Cidade c1 = new Cidade(null, "Uberlandia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));
		
		estadoRepository.saveAll(Arrays.asList(est1, est2));
		
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));
		
		Cliente cli1 = new Cliente(null, "Maria Silva", "fernandocareswork@gmail.com", 
				"36378912377", TipoCliente.PESSOAFISICA, bCryptPasswordEncoder.encode("teste123"));
		cli1.getTelefones().addAll(Arrays.asList("27363323", "93838393"));
		
		Cliente cli2 = new Cliente(null, "Fernando Cares", "fernandocareswork@outlook.com", 
				"41296822885", TipoCliente.PESSOAFISICA, bCryptPasswordEncoder.encode("teste123"));
		cli2.addPerfil(Perfil.ADMIN);
		cli2.getTelefones().addAll(Arrays.asList("18997594542", "18997594542"));
		
		
		Endereco e1 = new Endereco(null, "Rua Flores", "300", "Apto 303", "Jardim", "38220834", cli1, c1);
		Endereco e2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012", cli1, c2);
		Endereco e3 = new Endereco(null, "Rua Flores", "300", "Apto 303", "Jardim", "38220834", cli2, c1);
		
		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));
		cli2.getEnderecos().addAll(Arrays.asList(e3));
		
		clienteRepository.saveAll(Arrays.asList(cli1, cli2));
		enderecoRepository.saveAll(Arrays.asList(e1, e2, e3));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Pedido ped1 = new Pedido( null, sdf.parse("31/07/2018 19:46"), cli1, e1);
		Pedido ped2 = new Pedido( null, sdf.parse("31/07/2018 20:00"), cli1, e2);
		
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);
		
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 00:00"), null);
		ped2.setPagamento(pagto2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));
		
		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		
		pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));
		
		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00);
		
		ped1.getItens().addAll(Arrays.asList(ip1, ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));
		
		p1.getItens().addAll(Arrays.asList(ip2));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));
		
		itemPedidoRepository.saveAll(Arrays.asList(ip1, ip2, ip3));
		
	}
}
