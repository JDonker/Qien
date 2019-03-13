package com.qien.api;

import com.qien.Bericht;
import com.qien.Gebruiker;
import com.qien.Gesprek;
import com.qien.controller.BerichtenService;
import com.qien.controller.GebruikerService;
import com.qien.controller.GesprekService;
import com.qien.exception.UserNotFoundException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
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
		Optional<Gesprek> gesprekExisting = gesprekService.findBynaam(gesprek.getNaam());
		if(gesprekExisting.isPresent())
			return Response.status(Status.NOT_ACCEPTABLE).build();
		// voeg start datum aan bericht toe
		Bericht eerste = new Bericht();
		eerste.setInhoud("Gesprek gestart op " + LocalDateTime.now().toString());
		eerste.setVerzenderID(gesprek.getGebruikerID());
		eerste.setDatum( LocalDateTime.now());
		Bericht deze  = berichtenService.save(eerste);
		Gesprek result = gesprekService.save(gesprek);
		System.out.println(result.getId());
		result.getBerichten().add(deze);
		result = gesprekService.save(result);
		return  addGebruiker(result.getId(),gesprek.getGebruikerID());
	}
	
	
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}/{user}")
	public Response putGebruiker(@PathParam("id") Long gesprekid, @PathParam("user") Long userid ){
		return addGebruiker(gesprekid,userid);
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
	
	@GET
	@Path("/berichten")
	@Produces(MediaType.APPLICATION_JSON)
	public Response krijgBerichten() {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		System.out.println(securityContext.getAuthentication().getName() + " vraagt berichten" );
		try {
			Gebruiker gebruiker = gebruikerService.findByNaam(securityContext.getAuthentication().getName());
			long gesprekid = gebruiker.getHuidigGesprek();
			if(gesprekid>0) {
				if(gebruiker.isPersoonlijkGesprek()) {
					return getPersoonlijkGesprek(gebruiker.getId(),gesprekid);
				} else {
					return listMessages(gesprekid);
				}
			}
		} catch(UserNotFoundException e) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.status(Status.NOT_FOUND).build();
	}
	
	// Zoek groepsgesprekken op
	
	private Response listMessages(Long id) {
		Optional<Gesprek> gesprekExisting = gesprekService.findById(id);
		if(gesprekExisting.isPresent()) {
			Gesprek gesprek = gesprekExisting.get();
			return Response.ok(gesprek).build();
		}
		return Response.status(Status.NOT_FOUND).build();
	}
	
	// zoek persoonlijke berichten op
	private Response getPersoonlijkGesprek(Long gebruikerid,Long ontvangerid ){
		System.out.println("" + gebruikerid + ontvangerid);
		
		Optional<Gebruiker> gebruikerExisting = gebruikerService.findById(gebruikerid);
		if(gebruikerExisting.isPresent()) {
			Gebruiker dezegebruiker = gebruikerExisting.get();
			Iterable<Bericht> berichten = dezegebruiker.getBerichten();
			List<Bericht> filter = new ArrayList<Bericht>();
			for(Bericht bericht : berichten) {
				if(bericht.getOntvangerID()==ontvangerid|| bericht.getVerzenderID()==ontvangerid )
				filter.add(bericht);
			}
			return Response.ok(filter).build();
		}
		return Response.status(Status.NOT_FOUND).build();
	}
	
	
	
	
public Response addGebruiker(Long gesprekid,Long userid) {
	Optional<Gesprek> gesprekExisting = gesprekService.findById(gesprekid);
	
	if(gesprekExisting.isPresent()) {
		Gesprek gesprek = gesprekExisting.get();
		Optional<Gebruiker> gebruikerExisting = gebruikerService.findById(userid);
		if(gebruikerExisting.isPresent()) {
			Gebruiker gebruiker = gebruikerExisting.get();
			for(Gesprek deze:gebruiker.getGesprekken() ) {
				// check of gebruiker al in het gesprek zit
				if (deze.getId()==gesprek.getId())
					return Response.status(Status.NOT_ACCEPTABLE).build();
			}
			gebruiker.getGesprekken().add(gesprek);
			gesprek.getGebruikers().add(gebruiker);
			gebruikerService.save(gebruiker);
			Gesprek result = gesprekService.save(gesprek);
			return Response.accepted(result.getId()).build();
		}
	}
	return Response.status(Status.NOT_FOUND).build();
}

}

