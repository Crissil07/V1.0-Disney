package com.disney.services;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.disney.entities.gender;
import com.disney.entities.movie;
import com.disney.entities.photo;
import com.disney.error.errorServ;
import com.disney.repositories.genderRep;

@Service
public class genderServ {
	
	@Autowired
	private genderRep genderRep;
	
	@Autowired
	private photoServ photoServ;
	
	//Creacion de genero
	@Transactional
	public void createGender (String id, MultipartFile file, String genderName, movie associatedMovie) throws errorServ{
		
		validation(genderName, associatedMovie);
		
		//Seteo de valores
		gender gender = new gender();
		gender.setId(id);
		gender.setGenderName(genderName);
		gender.setAssociatedMovie(associatedMovie);
		
		photo photo = photoServ.save(file);
		gender.setPhoto(photo);
		
		genderRep.save(gender);
		
	}
	
	//Edicion de genero
	@Transactional
	public void genderEdit(String id, MultipartFile file, String genderName, movie associatedMovie) throws errorServ{
		
		Optional<gender> gender1 = genderRep.findById(id);
		
		if (gender1.isPresent()) {
			gender gender = genderRep.findById(id).get();
			gender.setGenderName(genderName);
			gender.setAssociatedMovie(associatedMovie);
			
			String idPhoto = null;
			if (gender.getPhoto() != null) {
				idPhoto = gender.getPhoto().getId();
			}
			
			photo photo = photoServ.editPhoto(idPhoto, file);
			gender.setPhoto(photo);
			
			genderRep.save(gender);
			
		}else {
			throw new errorServ("Invalid gender");
		}		
		
	}
	
	public void validation (String genderName, movie associatedMovie) throws errorServ{
		
		if (genderName == null || genderName.isEmpty()) {
			throw new errorServ("Gender cannot be null");
		}
		if (associatedMovie == null || associatedMovie.equals(" ")) {
			throw new errorServ("Associated movie cannot be null or empty");
		}
		
	}
	
}
