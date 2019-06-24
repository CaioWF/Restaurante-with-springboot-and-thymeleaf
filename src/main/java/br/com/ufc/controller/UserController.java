package br.com.ufc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
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
	
	@RequestMapping("/delete")
	public ModelAndView deleteUser() {
		Object auth = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails user = (UserDetails) auth;
		User userConnected = userService.getByEmail(user.getUsername());
		userService.deleteUser(userConnected.getCode());
		ModelAndView mv = new ModelAndView("redirect:/logout");
		return mv;
	}

	@RequestMapping("/update")
	public ModelAndView updateUser() {
		Object auth = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails user = (UserDetails) auth;
		User userConnected = userService.getByEmail(user.getUsername());
		ModelAndView mv = new ModelAndView("registerUser");
		mv.addObject("user", userConnected);
		return mv;
	}
}
