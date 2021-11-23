package com.disney.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class user {	
	
	private String name;
	
	private String lastName;
	
	@Id
	private String email;
	
	private String userName;
	
	private String password;	

}
