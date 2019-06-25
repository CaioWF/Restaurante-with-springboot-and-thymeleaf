package br.com.ufc.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class ShoppingCart {
	public static List<Item> items = new ArrayList<Item>();
	private Double total;
	
	public List<Item> getItems() {
		return items;
	}
	
	public static void setItems(List<Item> items) {
		ShoppingCart.items = items;
	}
		
	public Double getTotal() {
		total = 0.0;
		for (Item i: ShoppingCart.items) {
			total += i.getTotalPrice();
		}
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
	
	public void addIdPedido(Pedido pedido) {
		for (Item item: items) {
			item.setPedido(pedido);
		}
	}
	
	public static void clearShoppingCart() {
		items.clear();
	}
}
