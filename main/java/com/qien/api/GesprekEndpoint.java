package com.qien.api;

import com.qien.Bericht;
import com.qien.Gebruiker;
import com.qien.Gesprek;
import com.qien.controller.BerichtenService;
import com.qien.controller.GebruikerService;
import com.qien.controller.GesprekService;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;

import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import javax.ws.rs.core.MediaType;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;




// adress in de api
@Path("Gesprek")
@Component
@Transactional
public class GesprekEndpoint {

	@Autowired
	GesprekService gesprekService;
	@Autowired
	GebruikerService gebruikerService;
	@Autowired
	BerichtenService berichtenService;
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	
	// Deze sectie reageert op een get request 
	// ontvangt geen input
	// returned de verzorgers in JSON format.
	
	public Response listGroep(){
		Iterable <Gesprek> gespreken = gesprekService.findAll();
		return Response.ok(gespreken).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	
	// Deze sectie reageert op een post request dan wordt een verzorger toegevoegd doormiddel van save
	// Het nieuwe verzorger object komt binnen als een json string
	// Dit nieuwe verzorger object wordt direct toegevoegd aan de verzorger repository via de service
	// Als alles gelukt wordt er een response terug gestuurd van het id van de laatste dag. 
	// Deze response is in plain text. 
	
	public Response postGesprek(Gesprek gesprek){
		Optional<Gebruiker> gesprekExisting = gebruikerService.findBynaam(gesprek.getNaam());
			if(gesprekExisting.isPresent())
				return Response.status(Status.NOT_ACCEPTABLE).build();
		// voeg start datum aan bericht toe
		Bericht eerste = new Bericht();
		eerste.setInhoud("Gesprek gestart op " + LocalDateTime.now().toString());
		eerste.setVerzenderID(gesprek.getGebruikerID());
		Bericht deze  = berichtenService.save(eerste);
		Gesprek result = gesprekService.save(gesprek);
		System.out.println(result.getId());
		result.getBerichten().add(deze);
		result = gesprekService.save(result);
		return  addGebruiker(result.getId(),gesprek.getGebruikerID());
	}
	
	
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	@Path("{id}/{user}")
	public Response putGebruiker(@PathParam("id") Long gesprekid, @PathParam("user") Long userid ){
		return  addGebruiker(gesprekid,userid);
	}
	
	
	
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
		
	// Deze sectie reageert op een post request dan wordt een verzorger toegevoegd doormiddel van save
	// Het nieuwe verzorger object komt binnen als een json string
	// Dit nieuwe verzorger object wordt direct toegevoegd aan de verzorger repository via de service
	// Als alles gelukt wordt er een response terug gestuurd van het id van de laatste dag. 
	// Deze response is in plain text. 
	
@Path("{id}")
public Response deleteGesprek(@PathParam("id") Long id) {
	System.out.println("hallo hier ben ik");
	Optional<Gesprek> gesprekExisting = gesprekService.findById(id);

	if (!gesprekExisting.isPresent()) {
		return Response.status(Status.NOT_FOUND).build();			
	};
	
	if (!gesprekService.delete(id)) {
		return Response.status(Status.NOT_FOUND).build();
	} else {	
		return Response.ok().build();
	}
}
	
	// Deze sectie reageert op een get request 
	// ontvangt geen input
	// returned de verzorgers in JSON format.
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response listMessages(@PathParam("id") Long id) {
		Optional<Gesprek> gesprekExisting = gesprekService.findById(id);
		if(gesprekExisting.isPresent()) {
			Gesprek gesprek = gesprekExisting.get();
			System.out.println(gesprek.getBerichten().get(0).getInhoud());
			return Response.ok(gesprek).build();
		}
		return Response.status(Status.NOT_FOUND).build();
	}
	
public Response addGebruiker(Long gesprekid,Long userid) {
	System.out.println("hoi");
	Optional<Gesprek> gesprekExisting = gesprekService.findById(gesprekid);
	if(gesprekExisting.isPresent()) {
		Gesprek gesprek = gesprekExisting.get();
		Optional<Gebruiker> gebruikerExisting = gebruikerService.findById(userid);
		
		if(gebruikerExisting.isPresent()) {
			Gebruiker gebruiker = gebruikerExisting.get();
			gebruiker.getGesprekken().add(gesprek);
		//	gesprek.getGebruikers().add(gebruiker);
			gebruikerService.save(gebruiker);
			Gesprek result = gesprekService.save(gesprek);
			return Response.accepted(result.getId()).build();
		}
		
	}
	return Response.status(Status.NOT_FOUND).build();
}

}

