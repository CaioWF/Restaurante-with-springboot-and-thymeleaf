package br.com.ufc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.ufc.security.UserDetailsServiceImplemments;
import br.com.ufc.util.MyLogoutSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private MyLogoutSuccessHandler mlsh;

	@Autowired
	private UserDetailsServiceImplemments userDetailsServiceImplemments;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests()
		
		.antMatchers("/").permitAll()
		.antMatchers("/about").permitAll()
		.antMatchers("/user/register").permitAll()
		.antMatchers("/user/save").permitAll()
		.antMatchers("/user/login").permitAll()
		.antMatchers("/user/delete").hasRole("USER")
		.antMatchers("/user/update").hasRole("USER")
		.antMatchers("/dish/dishes").permitAll()
		.antMatchers("/dish/register").hasRole("ADMIN")
		.antMatchers("/dish/save").hasRole("ADMIN")
		.antMatchers("/dish/delete/{id}").hasRole("ADMIN")
		.antMatchers("/dish/update/{id}").hasRole("ADMIN")
		.antMatchers("/pedido/save{address}").hasRole("USER")
		.antMatchers("/pedido/add-item-to-shoppingcart/{id}/{quantity}").hasRole("USER")
		.antMatchers("/pedido/delete-item-from-shoppingcart/{index}").hasRole("USER")
		.antMatchers("/pedido/pedidosUser").hasRole("USER")
		
		.anyRequest().authenticated()
		
		.and()
		.formLogin()
		.loginPage("/user/login").permitAll()
		
		.and()
		.logout()
		.logoutSuccessHandler(mlsh);
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsServiceImplemments).passwordEncoder(new BCryptPasswordEncoder());
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/css/**", "/js/**", "/images/**", "/img/**");
	}
}
