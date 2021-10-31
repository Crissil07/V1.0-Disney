package com.disney.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.disney.entities.user;
import com.disney.error.errorServ;
import com.disney.repositories.userRep;


@Service
public class userServ{
	
	@Autowired
	private userRep userRep;
	
	//Creacion de usuario
	public void singUp(String name, String lastName, String email, String userName, String password) throws errorServ {
		
		//Validacion de datos
		if (name == null || name.isEmpty()) {
			throw new errorServ("Name cannoat be null");
		}
		if (lastName == null || lastName.isEmpty()) {
			throw new errorServ("Last name cannot be null");
		}		
		if (email == null || email.isEmpty()) {
			throw new errorServ("Email cannot be null"); 
		}		
		if (userName == null || userName.isEmpty()) {
			throw new errorServ("User name cannot be null");
		}		
		if (password == null || password.isEmpty() || password.length() > 8) {
			throw new errorServ("Password cannot be null and must contain less than 8 characters and numbers");
		}		
		
		user user= new user();		
		user.setName(name);
		user.setLastName(lastName);
		user.setEmail(email);
		user.setUserName(userName);
		user.setPassword(password);
		
		userRep.save(user);
		
	}
	
	//Modificacion de datos
	public void userEdit(String email, String userName, String password) throws errorServ{
		
		validation(email, userName, password);
		
		java.util.Optional<user> user1 = userRep.findById(email);
		
		if (user1.isPresent()) {
			
			user user = userRep.findById(email).get();
			user.setEmail(email);
			user.setUserName(userName);
			user.setPassword(password);
			
			userRep.save(user);
			
		}else {
			throw new errorServ ("The user doesnt exist");
		}
		
	}
	
	//Eliminar usuario por id
	public void deleteUser(String email) throws errorServ {
		
         java.util.Optional<user> user1 = userRep.findById(email);
		
		 if (user1.isPresent()) {
			
			user user = userRep.findById(email).get();
			userRep.deleteById(email);
			
			userRep.save(user);
			
		 }else {
			throw new errorServ ("The user doesnt exist");
		 }
		
	}
	
	//Listar usuario por mail
	public List<user> searchByEmail (String email){
		
		List<user> listUser = userRep.searchByEmail(email);
		
		return listUser;
		
	}
	
	//Metodo de validacion
	public void validation (String email, String userName, String password) throws errorServ{
		
		if (email == null || email.isEmpty()) {
			throw new errorServ("Email cannot be null"); 
		}
		
		if (userName == null || userName.isEmpty()) {
			throw new errorServ("User name cannot be null");
		}
		
		if (password == null || password.isEmpty() || password.length() > 8) {
			throw new errorServ("Password cannot be null and must contain less than 8 characters and numbers");		
	}    
		
  }	
	
}
