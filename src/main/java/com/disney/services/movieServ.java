package com.disney.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.disney.entities.chharacter;
import com.disney.entities.gender;
import com.disney.entities.movie;
import com.disney.entities.photo;
import com.disney.error.errorServ;
import com.disney.repositories.movieRep;

@Service
public class movieServ {
	
	@Autowired
	private movieRep movieRep;
	
	@Autowired
	private photoServ photoServ;
	
	//Creacion de pelicula
	@Transactional
	public void createMovie (String id, MultipartFile file,String title, Date creation, 
			String calification, chharacter associaterdCharacter, gender associatedGender)throws errorServ{
		
		validation(title, creation, calification, associaterdCharacter, associatedGender);
		
		//Seteo de valores
		movie movie = new movie();
		movie.setId(id);
		movie.setTitle(title);
		movie.setCreation(creation);
		movie.setCalification(calification);
		movie.setAssociatedCharacter(associaterdCharacter);
		movie.setAssociatedGender(associatedGender);
		
		photo photo = photoServ.save(file);
		movie.setPhoto(photo);
		
		movieRep.save(movie);		
		
	}
	
	//Edicion de pelicula
	@Transactional
	public void movieEdit(String id, MultipartFile file,String title, Date creation, 
			String calification, chharacter associaterdCharacter, gender associatedGender) throws errorServ{
		
		Optional<movie> movie1 = movieRep.findById(id);
		
		if(movie1.isPresent()) {
			
			movie movie = movieRep.findById(id).get();
			movie.setTitle(title);
			movie.setCreation(creation);
			movie.setCalification(calification);
			movie.setAssociatedCharacter(associaterdCharacter);
			movie.setAssociatedGender(associatedGender);
			
			String idPhoto = null;
			if(movie.getPhoto() != null) {
				idPhoto = movie.getPhoto().getId();
			}
			
			photo photo = photoServ.editPhoto(idPhoto, file);
			movie.setPhoto(photo);
			
			movieRep.save(movie);
			
		}else {
			throw new errorServ("Invalid movie");
		}
		
	}
	
	//Eliminacion de pelicula
	@Transactional
	public void deleteMovie(String id) throws errorServ{
		
		Optional<movie> movie1 = movieRep.findById(id);
		
		if (movie1.isPresent()) {
			movie movie = movieRep.findById(id).get();
			movieRep.delete(movie);
			
		}else {
			throw new errorServ("The movie doesnt exist");
		}
		
	}
	
	//7-Listado de peliculas
	public List<movie> findAllmovie1 (String id){
		
		List<movie> listMovie = movieRep.findAll();
		
		return listMovie;
		
	}
	
	//8-Detalle pelicula
	public movie MovieDetail (String title) {
		
		Optional<movie> movie1 = movieRep.findById(title);
		
		movie movie = movie1.get();
		
		return movie;
		
	}
	
	//10-Busqueda de pelicula por titulo, filtro por genero, orden por creacion asc y desc.
	public movie findBymovieASC (String title) {
		
		movie movie1 = movieRep.findBymovieASC(title);
		
		return movie1;
		
	}
	
    public movie findBymovieDESC (String title) {
		
		movie movie1 = movieRep.findBymovieDESC(title);
		
		return movie1;
		
	}	
	
	public void validation (String title, Date creation, String calification,
			chharacter associaterdCharacter, gender associatedGender) throws errorServ {
		
		if (title == null || title.isEmpty()) {
			throw new errorServ("Title cannoat be null");
		}
		if (creation == null || creation.equals(' ')) {
			throw new errorServ("Creation date cannot be null or empty");
		}
		if (calification == null || calification.equals(' ')) {
			throw new errorServ("Calification cannot be null or empty");
		}
		if (associaterdCharacter == null || associaterdCharacter.equals(" ")) {
			throw new errorServ("Associated character cannot be null or empty");
		}
		if (associatedGender == null || associatedGender.equals(" ")) {
			throw new errorServ("Associated gender cannot be null or empty");
		}	
		
	}

}
