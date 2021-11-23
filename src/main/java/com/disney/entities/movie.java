package com.disney.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class movie {
	
	@Id
	@GeneratedValue (generator = "uuid")
	@GenericGenerator (name = "uuid", strategy = "uuid2")
	private String id;
	
	@OneToOne
	private photo photo;
	
	private String title;
	
	@Temporal (TemporalType.TIMESTAMP)
	private Date creation;
	
	private String calification;
	
	@ManyToOne
	private chharacter associatedCharacter;
	
	@ManyToOne
	private gender associatedGender;

}
