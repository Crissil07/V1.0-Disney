package com.disney.services;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.disney.entities.user;
import com.disney.error.errorServ;
import com.disney.repositories.userRep;


@Service
public class userServ implements UserDetailsService{
	
	@Autowired
	private userRep userRep;
	
	@Autowired
	private notificationServ notificationServ;// vinculo la notificacion de servicio con usuario
	
	//Creacion de usuario
	@Transactional
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
		
		String encrypted = new BCryptPasswordEncoder().encode(password);// creamos una variable y de esa manera llamamos al mismo servicio de encriptar la clave y la pasamos.
		
		user.setPassword(encrypted); //en vez de setear la clave por default, la seteamos encriptada.
		
		userRep.save(user);
		
		notificationServ.sendMail("Bienvenido a Disney-Alkemy", "Disney-Alkemy", user.getEmail());
		
	}
	
	//Modificacion de datos
	@Transactional
	public void userEdit(String email, String userName, String password) throws errorServ{
		
		validation(email, userName, password);
		
		java.util.Optional<user> user1 = userRep.findById(email);
		
		if (user1.isPresent()) {
			
			user user = userRep.findById(email).get();
			user.setEmail(email);
			user.setUserName(userName);
			
			String encrypted = new BCryptPasswordEncoder().encode(password);// creamos una variable y de esa manera llamamos al mismo servicio de encriptar la clave y la pasamos.
			
			user.setPassword(encrypted); //en vez de setear la clave por default, la seteamos encriptada.
			
			userRep.save(user);
			
		}else {
			throw new errorServ ("The user doesnt exist");
		}
		
	}
	
	//Eliminar usuario por id
	@Transactional
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

	@Override
	public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException { //Este metodo es usado para cuando un usuario quiera autentificarse en la plataforma (log in)
		 
		user user01 = userRep.getById(mail); //busca al usuario por mail (id)
		
		if (user01 != null) {
			
			List<GrantedAuthority> userPermission = new ArrayList<>(); //list de la clase <GrantedAuthority> que va a contener el listado de permisos
			
			//Creamos permisos para el usuario (Un user simple, no admin para este ejemplo).
			GrantedAuthority p1 = new SimpleGrantedAuthority("PHOTO_MODULE");
			userPermission.add(p1);
			GrantedAuthority p2 = new SimpleGrantedAuthority("MOVIE_MODULE");
			userPermission.add(p2);
			GrantedAuthority p3 = new SimpleGrantedAuthority("CHARACTER_MODULE");
			userPermission.add(p3);
			GrantedAuthority p4 = new SimpleGrantedAuthority("GENDER_MODULE");
			userPermission.add(p4);
			
			
			User user = new User(user01.getEmail(), user01.getPassword(), userPermission);
			return user;
			
		}else {
			return null;
		}
		
	}	
	
}
