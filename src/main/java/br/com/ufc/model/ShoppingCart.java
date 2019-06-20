package br.com.ufc.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class ShoppingCart {
	private List<Item> items = new ArrayList<Item>();
	private Double total = 0.0;
	
	public List<Item> getItems() {
		return items;
	}
	
	public void setItems(List<Item> items) {
		this.items = items;
	}
		
	public Double getTotal() {
		return total;
	}
	
	public void setTotal(Double total) {
		this.total = total;
	}
	
	public void addItem(Item item) {
		items.add(item);
		setTotal(getTotal() + item.getTotalPrice());
	}
	
	public void deleteItem(int index) {
		setTotal(getTotal() - items.get(index).getTotalPrice());
		items.remove(index);
	}
}
