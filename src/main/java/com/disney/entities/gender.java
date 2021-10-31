package com.disney.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class gender {
	
	@Id
	@GeneratedValue (generator = "uuid")
	@GenericGenerator (name = "uuid", strategy = "uuid2")
	private String id;
	
	private String genderName;
	
	@OneToOne
	private photo photo;
	
	@OneToOne
	private movie associatedMovie;

}
