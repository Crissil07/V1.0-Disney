package com.disney.entities;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class photo {
	
	@Id
	@GeneratedValue (generator = "uuid")
	@GenericGenerator (name = "uuid", strategy = "uuid2")	
	private String id;
	
	private String name;
	
	//Indica el tipo de archivo de la foto
	private String mime;	
	
	//Lob: tipo de dato byte, lo uso cuando es pesado
	//Basic lazy: indicamos que cargue el contenido cuando lo requerimos (Querys mas livianas)
	@Lob
	@Basic(fetch = FetchType.LAZY)
	private byte[] content;		

}
