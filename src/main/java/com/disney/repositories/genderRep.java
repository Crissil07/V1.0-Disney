package com.disney.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.disney.entities.gender;

@Repository
public interface genderRep extends JpaRepository<gender, String> {

}
