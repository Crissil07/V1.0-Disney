package com.disney.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import javax.persistence.OneToOne;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class chharacter {	
	
	@OneToOne
	private photo photo;		
	
	private String name;
	
	@Id
	@GeneratedValue (generator = "uuid")
	@GenericGenerator (name = "uuid", strategy = "uuid2")
	private String id;
	
	private Integer age;
	
	private Integer weight;
	
	private String history;
	
	@ManyToOne
	private movie associatedMovie;
	
}