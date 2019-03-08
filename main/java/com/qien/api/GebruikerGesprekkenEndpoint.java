package com.qien.api;

import com.qien.Gebruiker;
import com.qien.Gesprek;
import com.qien.controller.GebruikerService;
import com.qien.controller.GesprekService;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
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
import org.springframework.stereotype.Component;




// adress in de api
@Path("GebruikerGesprekken")
@Component
@Transactional
public class GebruikerGesprekkenEndpoint {

	@Autowired
	GebruikerService gebruikerService;
	@Autowired
	GesprekService gesprekService;
	



@GET
@Produces(MediaType.APPLICATION_JSON)
	// Deze sectie reageert op een post request dan wordt een verzorger toegevoegd doormiddel van save
	// Het nieuwe verzorger object komt binnen als een json string
	// Dit nieuwe verzorger object wordt direct toegevoegd aan de verzorger repository via de service
	// Als alles gelukt wordt er een response terug gestuurd van het id van de laatste dag. 
	// Deze response is in plain text. 
	
	@Path("{naam}")
	public Response getGebruiker(@PathParam("naam") String naam) {
		Optional<Gebruiker> gebruikerExisting = gebruikerService.findBynaam(naam);
		if(gebruikerExisting.isPresent()) {
			Gebruiker dezegebruiker = gebruikerExisting.get();
			Iterable<Gesprek> gesprekken = dezegebruiker.getGesprekken();
			return Response.ok(gesprekken).build();
		}
		return Response.status(Status.NOT_FOUND).build();
	}
}





