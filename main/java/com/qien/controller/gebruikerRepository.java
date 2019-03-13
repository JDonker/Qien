package com.qien.controller;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import com.qien.Gebruiker;


@Component
public interface gebruikerRepository extends CrudRepository<Gebruiker, Long> {
	  Optional<Gebruiker> findByNaam(String naam);
}
