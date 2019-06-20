package br.com.ufc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.ufc.model.User;
import br.com.ufc.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService = new UserService();
	
	@RequestMapping("/register")
	public ModelAndView registerUser() {
		ModelAndView mv = new ModelAndView("registerUser");
		mv.addObject("user", new User());
		return mv;
	}
	
	@RequestMapping("/save")
	public ModelAndView saveUser(@Validated User user, BindingResult result) {
		ModelAndView mv = new ModelAndView("registerUser");
		if(result.hasErrors()) {
			return mv;
		}
		userService.registerUser(user);
		mv.addObject("message", "Usuário cadastrado!");
		return mv;
	}
	
	@RequestMapping("/login")
	public ModelAndView formLogin() {
		ModelAndView mv = new ModelAndView("loginUser");
		return mv;
	}
	
	@RequestMapping("/delete/{code}")
	public ModelAndView deleteUser(@PathVariable Long code) {
		userService.deleteUser(code);
		ModelAndView mv = new ModelAndView("redirect:/");
		return mv;
	}

	@RequestMapping("/update/{code}")
	public ModelAndView updateUser(@PathVariable Long code) {
		User user = userService.getByCode(code);
		ModelAndView mv = new ModelAndView("registerUser");
		mv.addObject("user", user);
		return mv;
	}
}
