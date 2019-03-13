package com.qien.api;

import com.qien.Gebruiker;
import com.qien.controller.GebruikerService;
import com.qien.exception.UserNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;




// adress in de api
@Path("/Gebruiker")
@Component
public class GebruikerEndpoint {
	@Autowired
	GebruikerService gebruikerService;
	
	
	// Stuur alle gebruikers
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@PreAuthorize("isAuthenticated()")
	public Response listGroep(){
		Iterable <Gebruiker> gebruikers = gebruikerService.findAll();
		return Response.ok(gebruikers).build();
	}
	
	// Stuur alle gebruikers behave de vrager;
	
	@GET
	@Path("/other")
	@Produces(MediaType.APPLICATION_JSON)
	@PreAuthorize("isAuthenticated()")
	public Response listSelectedGroep(){
		Iterable <Gebruiker> gebruikers = gebruikerService.findAll();
		SecurityContext securityContext = SecurityContextHolder.getContext();
		Gebruiker gebruikert = gebruikerService.findByNaam(securityContext.getAuthentication().getName());
		List<Gebruiker> deze  = new ArrayList<Gebruiker>();
		for(Gebruiker g: gebruikers) {
			if (!g.getNaam().equals(gebruikert.getNaam()))
				deze.add(g);
		}
		return Response.ok(deze).build();
	}
	
	
	
	// Voeg nieuwe gebruiker toe
	
	@POST
	@Path("/add")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	// Voeg nieuwe gebruiker toe middels post request (naam, wachtwoord,rol)
	public Response postGebruiker(Gebruiker gebruiker){
		Gebruiker result = gebruikerService.addGebruiker(gebruiker);
		return Response.accepted(result.getId()).build();	
	}
	
	// Verwijder gebruiker
	
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	@Path("{id}")
	public Response deleteGebruiker(@PathParam("id") Long id) {
		Optional<Gebruiker> gebruikerExisting = gebruikerService.findById(id);

		if (!gebruikerExisting.isPresent()) {
			return Response.status(Status.NOT_FOUND).build();			
		};
	
		if (!gebruikerService.delete(id)) {
			return Response.status(Status.NOT_FOUND).build();
		} else {	
			return Response.ok().build();
		}

	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{naam}")
	public Response getGebruiker(@PathParam("naam") String naam) {
		try {
			Gebruiker gebruiker = gebruikerService.findByNaam(naam);
			System.out.println(gebruiker.getNaam());
			return Response.ok(gebruiker).build();
		} catch(UserNotFoundException e) {
			return Response.status(Status.NOT_FOUND).build();
		}
	}	
	
	
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	@PreAuthorize("isAuthenticated()")
	@Path("/huidiggesprek")
	public Response setHuidigGesprek(Gebruiker henk) {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		System.out.println(securityContext.getAuthentication().getName() + "hallo" + henk.getRol());
		try {
			Gebruiker gebruiker = gebruikerService.findByNaam(securityContext.getAuthentication().getName());
			gebruiker.setPersoonlijkGesprek(henk.isPersoonlijkGesprek());
			gebruiker.setHuidigGesprek(henk.getHuidigGesprek());
			gebruikerService.save(gebruiker);
			return Response.accepted(gebruiker.getId()).build();
		} catch(UserNotFoundException e) {
			System.out.println("What is wrong?");
			return Response.status(Status.NOT_FOUND).build();
		}
	}	
}




