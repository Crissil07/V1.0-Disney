package com.disney.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.disney.entities.movie;

import net.bytebuddy.asm.Advice.OffsetMapping.Sort;

@Repository
public interface movieRep extends JpaRepository<movie, String>{
	
	@Query("SELECT m FROM movie m WHERE m.title = title")
	public movie MovieDetail(@Param("title")String title);
	
	@Query("SELECT m.photo, m.title, m.creation FROM movie m")
	public List<movie> findAllmovie1(Sort sort);
	
	@Query("SELECT m FROM movie m INNER JOIN gender g on g.genderName = m.associatedGender WHERE m.title = title ORDER BY m.creation ASC")
	public movie findBymovieASC(String title);
	
	@Query("SELECT m FROM movie m INNER JOIN gender g on g.genderName = m.associatedGender WHERE m.title = title ORDER BY m.creation DESC")
	public movie findBymovieDESC(String title);		
	
}
