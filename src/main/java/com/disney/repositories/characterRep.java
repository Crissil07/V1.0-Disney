package com.disney.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.disney.entities.chharacter;

import net.bytebuddy.asm.Advice.OffsetMapping.Sort;

@Repository
public interface characterRep extends JpaRepository<chharacter, String> {	
	
	@Query("SELECT c.name, c.age, c.weight, c.history, c.associatedMovie FROM chharacter c WHERE c.name = name")
	public chharacter CharacterDetail(@Param("name")String name);
	
	@Query("SELECT c.photo, c.name FROM chharacter c")
	public List<chharacter> findAllchharacter(Sort sort);
	
	@Query("SELECT c From chharacter c WHERE c.name = name ORDER BY age")
	public chharacter CharacterByAge(@Param("name")String name);
	
	@Query("SELECT c From chharacter c WHERE c.name = name ORDER BY weight")
	public chharacter CharacterByWeight(@Param("name")String name);
	
	@Query("SELECT c From chharacter c WHERE c.name = name ORDER BY associatedMovie")
	public chharacter CharacterByAssoMovie(@Param("name")String name);
	
}
