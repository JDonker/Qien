package com.qien.config;

import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;
import com.qien.api.BerichtenEndpoint;
import com.qien.api.GebruikerEndpoint;
import com.qien.api.GebruikerGesprekkenEndpoint;
import com.qien.api.GesprekEndpoint;


@Component
@ApplicationPath("/api")
public class JerseyConfig extends ResourceConfig {
	public JerseyConfig(){
		register(BerichtenEndpoint.class);
		register(GesprekEndpoint.class);
		register(GebruikerEndpoint.class);
		register(GebruikerGesprekkenEndpoint.class);
	}
}
