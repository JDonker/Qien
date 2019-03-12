package com.qien.api;

import com.qien.Gebruiker;
import com.qien.controller.GebruikerService;

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
@Path("Gebruiker")
@Component
public class GebruikerEndpoint {
	

//    @POST
//    @Path("/add")
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public ResponseEntity addGebruiker(@RequestBody @Valid Gebruiker gebruiker) {
//        return ResponseEntity.ok(GebruikerService.addUser(gebruiker));
//
//    }
//    

	@Autowired
	GebruikerService gebruikerService;
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	
	// Deze sectie reageert op een get request 
	// ontvangt geen input
	// returned de verzorgers in JSON format.
	
	public Response listGroep(){
		Iterable <Gebruiker> gebruikeren = gebruikerService.findAll();
		return Response.ok(gebruikeren).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	
	// Deze sectie reageert op een post request dan wordt een verzorger toegevoegd doormiddel van save
	// Het nieuwe verzorger object komt binnen als een json string
	// Dit nieuwe verzorger object wordt direct toegevoegd aan de verzorger repository via de service
	// Als alles gelukt wordt er een response terug gestuurd van het id van de laatste dag. 
	// Deze response is in plain text. 
	
	public Response postGebruiker(Gebruiker gebruiker){
		Optional<Gebruiker> gebruikerExisting = gebruikerService.findBynaam(gebruiker.getNaam());
		if(gebruikerExisting.isPresent())
			return Response.status(Status.NOT_ACCEPTABLE).build();
		Gebruiker result = gebruikerService.save(gebruiker);
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
	// Deze sectie reageert op een post request dan wordt een verzorger toegevoegd doormiddel van save
	// Het nieuwe verzorger object komt binnen als een json string
	// Dit nieuwe verzorger object wordt direct toegevoegd aan de verzorger repository via de service
	// Als alles gelukt wordt er een response terug gestuurd van het id van de laatste dag. 
	// Deze response is in plain text. 
	
@Path("{naam}")
public Response getGebruiker(@PathParam("naam") String naam) {
	Optional<Gebruiker> gebruikerExisting = gebruikerService.findBynaam(naam);
	if(gebruikerExisting.isPresent()) {
		System.out.println(gebruikerExisting.get().getNaam());
		return Response.ok(gebruikerExisting.get()).build();
	} else {
		return Response.status(Status.NOT_FOUND).build();
	}
}

	
	// Deze sectie reageert op een post request dan wordt een verzorger toegevoegd doormiddel van save
	// Het nieuwe verzorger object komt binnen als een json string
	// Dit nieuwe verzorger object wordt direct toegevoegd aan de verzorger repository via de service
	// Als alles gelukt wordt er een response terug gestuurd van het id van de laatste dag. 
	// Deze response is in plain text. 

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	@Path("{naam}/{wachtwoord}")
	public Response login(@PathParam("naam") String naam,@PathParam("wachtwoord") String wachtwoord) {
		Optional<Gebruiker> gebruikerExisting = gebruikerService.findBynaam(naam);
		if(gebruikerExisting.isPresent()) {
			System.out.println(gebruikerExisting.get().getNaam());
			if (gebruikerExisting.get().getWachtwoord().equals(wachtwoord))
				return Response.accepted(gebruikerExisting.get().getId()).build();
			return Response.status(Status.FOUND).build();
		} else {
			return Response.status(Status.CREATED).build();
		}
	}


}




