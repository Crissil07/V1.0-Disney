package com.disney.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.disney.entities.photo;
import com.disney.error.errorServ;
import com.disney.repositories.photoRep;


@Service
public class photoServ {
	
	@Autowired
	private photoRep photoRep;
	
	public photo save(MultipartFile file) throws errorServ{
		
		if(file != null) {
			
			try {
				photo photo = new photo();
				
				photo.setMime(file.getContentType());
				photo.setName(file.getName());
				//Seteo contenido de la foto.
				photo.setContent(file.getBytes());
				
				return photoRep.save(photo);
				
			} catch (Exception e) {
				System.err.println(e.getMessage());
				throw new errorServ("The file could not be read");				
			}			
			
		}
		
		return null;
		
	}
	
	public photo editPhoto (String id,MultipartFile file) throws errorServ{
		
        if(file != null) {
			
			try {
				photo photo = new photo();
				
				//Si ya habia una foto asignada, la id va a ser diferente de null y la busco por id a traves de optional.
				if(id != null) {
					Optional<photo> photo1 = photoRep.findById(id);
					
					//Si hay foto, a la variable la piso con lo que traigo de la base de datos.
					if(photo1.isPresent()) {
						photo = photo1.get();
					}
				}
				
				//Seteo el nuevo contenido de la foto.
				photo.setMime(file.getContentType());
				photo.setName(file.getName());
				photo.setContent(file.getBytes());
				
				return photoRep.save(photo);
				
			} catch (Exception e) {
				System.err.println(e.getMessage());
				throw new errorServ("The file could not be read");				
			}			
			
		}
		
		return null;
		
	}	
	
}
