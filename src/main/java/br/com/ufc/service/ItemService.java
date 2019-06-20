package br.com.ufc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ufc.model.Item;
import br.com.ufc.repository.ItemRepository;

@Service
public class ItemService {
	
	@Autowired
	private ItemRepository itemRepository;

	public void registerItem(Item item) {
		itemRepository.save(item);
	}
	
	public void deleteItem(Long id) {
		itemRepository.deleteById(id);
	}
	
	public Item getById(Long id) {
		return itemRepository.getOne(id);
	}
}
