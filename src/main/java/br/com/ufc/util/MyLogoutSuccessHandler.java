package br.com.ufc.util;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import br.com.ufc.model.ShoppingCart;

@Component
public class MyLogoutSuccessHandler implements LogoutSuccessHandler {
													
	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		ShoppingCart.clearShoppingCart();
		String url = "http://localhost:8080/user/login";
		response.sendRedirect(url);
	}

}
