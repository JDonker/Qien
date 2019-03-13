package com.qien.controller;import java.util.Optional;
import com.qien.Gebruiker;
import com.qien.config.SimpleSecurityController;
import com.qien.exception.UserNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;



@Controller
@Service
@Transactional
public class GebruikerService {
	
	@Autowired
	private gebruikerRepository gebruikersRepository;
	private final SimpleSecurityController securityController;
	private final PasswordEncoder encoder;
	
	@Autowired
	public GebruikerService(gebruikerRepository gebruikerRepository, SimpleSecurityController securityController, PasswordEncoder encoder) {
	    this.gebruikersRepository = gebruikerRepository;
	    this.securityController = securityController;
	    this.encoder = encoder;
	}
	
	
	
	
	
	// maakt een iterable lijst van alle verzorgers
	public Iterable<Gebruiker>  findAll() {
		return gebruikersRepository.findAll();
	}
	
	public Gebruiker findByNaam(String naam) {
		Optional<Gebruiker> result = gebruikersRepository.findByNaam(naam);
		if (result.isPresent()) {
		    return result.get();
		} else {
		    throw new UserNotFoundException("[Naam : " + naam + "] - Gebruiker niet gevonden");
		}
	}
	
	
	
	  public Gebruiker addGebruiker(Gebruiker gebruiker) {
	        try {
	        	findByNaam(gebruiker.getNaam());
	            return new Gebruiker();
	        } catch (UserNotFoundException e) {
	        	System.out.println("ik wordt toch gecatched?");
	        	gebruiker.setWachtwoord(encoder.encode(gebruiker.getWachtwoord()));
	            Gebruiker savedGebruiker = gebruikersRepository.save(gebruiker);
	            if (savedGebruiker != null) {
	                //Adding user to simpleSecurityController to let the user to be able to use his/her account
	                securityController.add(savedGebruiker.getNaam(), savedGebruiker.getWachtwoord(),savedGebruiker.getRol());
	            }
	            return savedGebruiker;
	        }
	    }
	  
	  
	
	// hier wordt een nieuw Gebruiker toegevoegd aan de repository
	public Gebruiker save(Gebruiker Gebruiker){
		return gebruikersRepository.save(Gebruiker);
	}
	
	public Optional<Gebruiker> findById(long id) {
		return gebruikersRepository.findById(id);
	}
	
	public String deleteGebruiker(String naam) {
		if (gebruikersRepository.findByNaam(naam).isPresent()) {
			gebruikersRepository.findByNaam(naam).ifPresent(d -> {gebruikersRepository.deleteById(d.getId());securityController.remove(d.getNaam());});
			return "Gebruiker verwijderd [Naam: " + naam + "]";
		} else {
			return "Delete user request can not proceed because of non existing user [username: " + naam + "]";
		}
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
	
	public void initGebruikers() {
		System.out.println("<----- User Initialization Started ----->");
		for (Gebruiker u : findAll()) {
		    securityController.add(u.getNaam(), u.getWachtwoord(), u.getRol());
		}
		System.out.println("<----- User Initialization Finished ----->");
	}
	
	
	
	


}