package com.disney;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.disney.services.userServ;

@SpringBootApplication
public class DisneyApplication extends WebSecurityConfigurerAdapter{

	@Autowired
	private userServ userService;
	
	public static void main(String[] args) {
		SpringApplication.run(DisneyApplication.class, args);
	}
	
	@Autowired
	public void configureGlobal (AuthenticationManagerBuilder auth) throws Exception{ //Le dice a la config de spring security cual es el servicio que vamos a usar para autentificar el user
		auth.userDetailsService(userService).passwordEncoder(new BCryptPasswordEncoder());// y le setea un encriptador de password al servicio de usuario. Cada vez que se chequea la pass.
	}
	
	/*Faltante del ejercicio:
		2- Autenticacion por Tokens
		11- Envio de mails y crear un admin.		
		Crear Controladores y endpoints
		Documentacion con Postman
		Test (opcional)*/

}
