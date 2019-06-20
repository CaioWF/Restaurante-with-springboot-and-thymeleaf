package br.com.ufc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.ufc.model.Dish;
import br.com.ufc.model.Item;
import br.com.ufc.model.Pedido;
import br.com.ufc.model.ShoppingCart;
import br.com.ufc.service.DishService;
import br.com.ufc.service.PedidoService;

@Controller
@RequestMapping("/pedido")
public class PedidoController {
	
	private ShoppingCart shoppingCart = new ShoppingCart();
	
	@Autowired
	private PedidoService pedidoService = new PedidoService();
	
	@Autowired
	private DishService dishService = new DishService();
	
	@RequestMapping("/add-item-to-shoppingcart/{id}/{quantity}")
	public ModelAndView addItem(@PathVariable Long id, @PathVariable int quantity, RedirectAttributes redir){
		Item item = new Item();
		Dish dish = dishService.getById(id);
		item.setDish(dish);
		item.setQuantity(quantity);
		item.setTotalPrice(dish.getPrice()*quantity);
		shoppingCart.addItem(item);
		ModelAndView mv = new ModelAndView("redirect:/dish/dishes");
		redir.addFlashAttribute("listitems", shoppingCart.getItems());
		return mv;
	}
	
	@RequestMapping("/delete-item-from-shoppingcart/{index}")
	public ModelAndView deleteItem(@PathVariable int index, RedirectAttributes redir){
		shoppingCart.deleteItem(index);
		ModelAndView mv = new ModelAndView("redirect:/dish/dishes");
		redir.addFlashAttribute("listitems", shoppingCart.getItems());
		return mv;
	}
	
	@RequestMapping("/save")
	public ModelAndView saveDish(@Validated Pedido pedido, BindingResult result) {
		ModelAndView mv = new ModelAndView("/dish/dishes");
		if(result.hasErrors()) {
			return mv;
		}
		pedidoService.registerPedido(pedido);
		mv.addObject("message", "Pedido cadastrado!");
		return mv;
	}

}
