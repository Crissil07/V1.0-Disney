package com.disney.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.disney.entities.chharacter;
import com.disney.entities.movie;
import com.disney.entities.photo;
import com.disney.error.errorServ;
import com.disney.repositories.characterRep;




@Service
public class characterServ {
	
	@Autowired
	private characterRep characterRep;
	
	@Autowired
	private photoServ photoServ;
	
	//Creacion de Personaje
	@Transactional
	public void createChar (String id, MultipartFile file, String name, Integer age, Integer weight, movie associatedMovie) throws errorServ {
		
		validation(name, age, weight, associatedMovie);	
		
		//Seteo de valores
		chharacter character = new chharacter();
		character.setId(id);		
		character.setName(name);
		character.setAge(age);
		character.setWeight(weight);
		character.setAssociatedMovie(associatedMovie);
		
		photo photo = photoServ.save(file);		
		character.setPhoto(photo);		
		
		characterRep.save(character);
		
	}
	
	//Edicion de personaje
	@Transactional
	public void characterEdit(String id,MultipartFile file, String name, Integer age, Integer weight, movie associatedMovie) throws errorServ{
		
		validation(name, age, weight, associatedMovie);	
		
		
		Optional<chharacter> character1 = characterRep.findById(id);
		
		if(character1.isPresent()) {
			
			chharacter character = characterRep.findById(id).get();
			character.setName(name);
			character.setAge(age);
			character.setWeight(weight);
			character.setAssociatedMovie(associatedMovie);
			
			//Obtengo id de foto del personaje.
			String idPhoto = null;
			if(character.getPhoto() != null) {
				//Si tenia foto guardada, pido el id, sino es nulo.
				idPhoto= character.getPhoto().getId();
			}
			
			//Si es la primera vez que mando foto, la crea, si ya habia, la actualiza.
			photo photo = photoServ.editPhoto(idPhoto, file);
			character.setPhoto(photo);
			
			characterRep.save(character);			
			
		}else {
			throw new errorServ("Invalid character");
		}
		
	}
	
	//Eliminacion de personajes
	@Transactional
	public void deleteCharacter(String id) throws errorServ{
		
	Optional<chharacter> character1 = characterRep.findById(id);
		
		if(character1.isPresent()) {
			
			chharacter character = characterRep.findById(id).get();
			characterRep.deleteById(id);
			
			characterRep.save(character);
			
		}else {
			throw new errorServ("The character doesnt exist");
		}
		
	}
	
	//5-Detalle de personaje
	public chharacter CharacterDetail(String name) {
		
		Optional<chharacter> character1 = characterRep.findById(name);
		
		chharacter character = character1.get();
		
		return character;
		
	}
	
	//3-Listado de personajes
	public List<chharacter> findAllchharacter (String id){
		
		List<chharacter> listCharacter = characterRep.findAll();
		
		return listCharacter;		
		
	}
	
	//6-Busqueda de personaje:edad, peso y pelicula asociada
	public chharacter CharacterByAge(String name) {
		
		chharacter character1 = characterRep.CharacterByAge(name);
		
		return character1;
		
	}
	
	public chharacter CharacterByWeight(String name) {
		
		chharacter character1 = characterRep.CharacterByWeight(name);
		
		return character1;
		
	}
	
	public chharacter CharacterByAssoMovie(String name) {
		
		chharacter character1 = characterRep.CharacterByAssoMovie(name);
		
		return character1;
		
	}
	
	//Metodo de validacion
	public void validation (String name, Integer age, Integer weight, movie associatedMovie) throws errorServ{
		
		
		if (name == null || name.isEmpty()) {
			throw new errorServ("Name cannoat be null");
		}
		if (age == null || age.equals(' ')) {
			throw new errorServ("Age cannot be null or empty");
		}
		if (weight == null || weight.equals(' ')) {
			throw new errorServ("Weihgt cannoat be null or empty");
		}
		if (associatedMovie == null || associatedMovie.equals(" ")) {
			throw new errorServ("Associated movie cannot be null or empty");
		}		
		
	}

}
