package com.disney.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.disney.entities.user;

@Repository
public interface userRep extends JpaRepository<user, String> {
	
	@Query("SELECT u FROM user u WHERE u.email = email")
	public List<user> searchByEmail (@Param("email")String email);

}
