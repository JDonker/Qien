package com.qien.api;

import com.qien.Bericht;
import com.qien.Gebruiker;
import com.qien.Gesprek;
import com.qien.controller.BerichtenService;
import com.qien.controller.GebruikerService;
import com.qien.controller.GesprekService;
import com.qien.controller.gesprekRepository;

import java.time.LocalDateTime;
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
import org.springframework.stereotype.Component;




// adress in de api
@Path("Bericht")
@Component
public class BerichtenEndpoint {

	@Autowired
	BerichtenService berichtService;
	
	@Autowired
	GesprekService gesprekService;
	
	@Autowired
	GebruikerService gebruikerService;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	
	// Deze sectie reageert op een get request 
	// ontvangt geen input
	// returned de verzorgers in JSON format.
	
	public Response listGroep(){
		Iterable <Bericht> berichten = berichtService.findAll();
		return Response.ok(berichten).build();

	}
	
	
	
	// Groepsgesprekken
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response postBericht(Bericht bericht){
		System.out.println(bericht.getInhoud());
		bericht.setDatum(LocalDateTime.now());
		Bericht result = berichtService.save(bericht);
		Optional<Gesprek> gesprek = gesprekService.findById(result.getOntvangerID());
		if(gesprek.isPresent()) {
			Gesprek tijdelijk = gesprek.get();
			tijdelijk.getBerichten().add(result);
			gesprekService.save(tijdelijk);
		}
		
		return Response.accepted(result.getId()).build();	
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	
	// Persoonlijke gesprekken
	@Path("{id}")
	public Response postGebruikerBericht(@PathParam("id") Long id, Bericht bericht){
		System.out.println(bericht.getInhoud());
		
		// get gebruikers die bericht versturen en ontvangen 
		Optional<Gebruiker> ontvanger = gebruikerService.findById(bericht.getOntvangerID());
		Optional<Gebruiker> verzender = gebruikerService.findById(id);
		bericht.setDatum(LocalDateTime.now());
		Bericht result = berichtService.save(bericht);
		if(ontvanger.isPresent() && verzender.isPresent()) {
			
			Gebruiker ontvangerD = ontvanger.get();
			Gebruiker verzenderD = verzender.get();
			System.out.println(bericht.getInhoud()+ 2);
			ontvangerD.getBerichten().add(result);
			gebruikerService.save(ontvangerD);
			verzenderD.getBerichten().add(result);
			gebruikerService.save(verzenderD);
		}
		return Response.accepted(result.getId()).build();	
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
public Response deleteVerzorger(@PathParam("id") Long id) {
	System.out.println("hallo hier ben ik");
	Optional<Bericht> berichtExisting = berichtService.findById(id);

	if (!berichtExisting.isPresent()) {
		return Response.status(Status.NOT_FOUND).build();			
	};
	
	if (!berichtService.delete(id)) {
		return Response.status(Status.NOT_FOUND).build();
	} else {	
		return Response.ok().build();
	}

}



}

