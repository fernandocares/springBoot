package com.fernandocares.springBoot.services;

import org.springframework.security.core.context.SecurityContextHolder;

import com.fernandocares.springBoot.security.UserSS;

/**
 * 
 * @author Fernando Cares
 * Classe para retornar o usuario logado no sistema
 */
public class UserService {

	public static UserSS authenticated() {
		
		try {
			return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			return null;
		}
		
	}
}
