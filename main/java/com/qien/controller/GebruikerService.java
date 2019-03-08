package com.qien.controller;import java.util.Optional;
import com.qien.Gebruiker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;



@Controller
@Service
@Transactional

public class GebruikerService {
	
	@Autowired
	private gebruikerRepository gebruikersRepository;
    //private final PasswordEncoder encoder;
	
	// maakt een iterable lijst van alle verzorgers
	public Iterable<Gebruiker>  findAll() {
		Iterable<Gebruiker> gebruikers = gebruikersRepository.findAll();
		return gebruikers;
	}
	
//	
//	  public Gebruiker addGebruiker(Gebruiker gebruiker) {
//	        try {
//	        	findBynaam(gebruiker.getNaam());
//	            return new Gebruiker();
//	        } catch (UserNotFoundException e) {
//	            Gebruiker savedUser = gebruikersRepository.save(gebruiker.setPassword(encoder.encode(gebruiker.getWachtwoord()())));
//	            if (savedUser != null) {
//	                //Adding user to simpleSecurityController to let the user to be able to use his/her account
//	                securityController.add(savedUser.getNaam(), savedUser.getWachtwoord());
//	            }
//	            return savedUser;
//	        }
//	    }
	
	// hier wordt een nieuw Gebruiker toegevoegd aan de repository
	public Gebruiker save(Gebruiker Gebruiker){
		return gebruikersRepository.save(Gebruiker);
	}
	
	
	
	public void delete(Gebruiker Gebruiker){
		gebruikersRepository.delete(Gebruiker); 
	}
	
	
	
	public Optional<Gebruiker> findById(long id) {
		return gebruikersRepository.findById(id);
	}
	
	public Optional<Gebruiker> findBynaam(String naam) {
		System.out.println(naam);
		return gebruikersRepository.findByNaam(naam);
	}
	
	

	public boolean delete(long id) {
		if (id<=0) {
			return false;
		}
		if (!gebruikersRepository.findById(id).isPresent()) {
			return false;
		};
		gebruikersRepository.deleteById(id);
		return true;
	}
	
	
	
	


}