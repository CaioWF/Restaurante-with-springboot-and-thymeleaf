	package br.com.ufc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import br.com.ufc.model.Dish;
import br.com.ufc.service.DishService;

@Controller
@RequestMapping("/dish")
public class DishController {

	@Autowired
	private DishService dishService = new DishService();
	
	@RequestMapping("/register")
	public ModelAndView registerDish() {
		ModelAndView mv = new ModelAndView("registerDish");
		mv.addObject("dish", new Dish());
		return mv;
	}
	
	@RequestMapping("/save")
	public ModelAndView saveDish(@Validated Dish dish, BindingResult result, @RequestParam(value="photo") MultipartFile photo) {
		ModelAndView mv = new ModelAndView("registerDish");
		if(result.hasErrors()) {
			return mv;
		}
		dishService.registerDish(dish, photo);
		mv.addObject("message", "Prato cadastrado!");
		mv.addObject("dish", new Dish());
		return mv;
	}
	
	@RequestMapping("/delete/{id}")
	public ModelAndView deleteDish(@PathVariable Long id) {
		dishService.deleteDish(id);
		ModelAndView mv = new ModelAndView("redirect:/dish/dishes");
		return mv;
	}

	@RequestMapping("/update/{id}")
	public ModelAndView updateDish(@PathVariable Long id) {
		Dish dish = dishService.getById(id);
		ModelAndView mv = new ModelAndView("registerDish");
		mv.addObject("dish", dish);
		return mv;
	}
	
	@RequestMapping("/dishes")
	public ModelAndView listDishes(){
		List<Dish> dishes = dishService.returnDishes();
		ModelAndView mv = new ModelAndView("dishes");
		mv.addObject("listdishes", dishes);
		return mv;
	}
}
