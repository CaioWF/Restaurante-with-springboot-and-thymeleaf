package br.com.ufc.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.ufc.model.Dish;
import br.com.ufc.model.Item;
import br.com.ufc.model.Pedido;
import br.com.ufc.model.ShoppingCart;
import br.com.ufc.model.User;
import br.com.ufc.service.DishService;
import br.com.ufc.service.ItemService;
import br.com.ufc.service.MailSenderService;
import br.com.ufc.service.PedidoService;
import br.com.ufc.service.UserService;

@Controller
@RequestMapping("/pedido")
public class PedidoController {
	
	private ShoppingCart shoppingCart = new ShoppingCart();
	@Autowired
	private MailSenderService mailSender = new MailSenderService();
	
	@Autowired
	private PedidoService pedidoService = new PedidoService();
	
	@Autowired
	private DishService dishService = new DishService();
	
	@Autowired
	private UserService userService = new UserService();
	
	@Autowired
	private ItemService itemService = new ItemService();
	
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
		redir.addFlashAttribute("totalPedido", shoppingCart.getTotal());
		return mv;
	}
	
	@RequestMapping("/delete-item-from-shoppingcart/{index}")
	public ModelAndView deleteItem(@PathVariable int index, RedirectAttributes redir){
		shoppingCart.deleteItem(index);
		ModelAndView mv = new ModelAndView("redirect:/dish/dishes");
		redir.addFlashAttribute("listitems", shoppingCart.getItems());
		redir.addFlashAttribute("totalPedido", shoppingCart.getTotal());
		return mv;
	}
	
	@RequestMapping("/save/{address}")
	public ModelAndView savePedido(@PathVariable String address) {
		Pedido pedido = new Pedido();
		pedido.setItems(shoppingCart.getItems());
		pedido.setTotalPrice(shoppingCart.getTotal());
		pedido.setDate(new Date());
		
		Object auth = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails user = (UserDetails) auth;
		User userConnected = userService.getByEmail(user.getUsername());
		
		if(address.equals("pattern")) {
			pedido.setDeliveryAddress(userConnected.getAddress());
		} else {
			pedido.setDeliveryAddress(address);
		}
		
		pedido.setUser(userConnected);
		pedidoService.registerPedido(pedido);
		
		shoppingCart.addIdPedido(pedido);
		itemService.saveAll(shoppingCart.getItems());
		
		//aquiii
		mailSender.sendMail(pedido.getTotalPrice(), userConnected.getEmail());
		
		shoppingCart = new ShoppingCart();
		
		ModelAndView mv = new ModelAndView("redirect:/");
		mv.addObject("message", "Pedido realizado!");
		return mv;
	}
	
	@RequestMapping("/pedidosUser")
	public ModelAndView AllPedidosByUser() {
		Object auth = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails user = (UserDetails) auth;
		User userConnected = userService.getByEmail(user.getUsername());
		
		ModelAndView mv = new ModelAndView("pedidosUser");
		
		List<Pedido> pedidos = pedidoService.getAllByUser(userConnected.getCode());
		
		
		mv.addObject("pedidos", pedidos);
		return mv;
	}

}
