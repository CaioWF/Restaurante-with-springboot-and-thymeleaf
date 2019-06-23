package br.com.ufc.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ufc.model.Pedido;
import br.com.ufc.repository.PedidoRepository;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository pedidoRepository;

	public void registerPedido(Pedido pedido) {
		pedidoRepository.save(pedido);
	}
	
	public void deletePedido(Long id) {
		pedidoRepository.deleteById(id);
	}
	
	public Pedido getById(Long id) {
		return pedidoRepository.getOne(id);
	}
	public List<Pedido> getAllByUser(Long user_code) {
		return pedidoRepository.findByUserCode(user_code);
	}
}
