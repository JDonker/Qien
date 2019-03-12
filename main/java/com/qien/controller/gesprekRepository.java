package com.qien.controller;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import com.qien.Gebruiker;
import com.qien.Gesprek;


@Component
public interface gesprekRepository extends CrudRepository<Gesprek, Long> { 
	
	  Optional<Gesprek> findByNaam(String naam);
}
