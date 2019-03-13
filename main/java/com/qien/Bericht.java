package com.qien;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;


import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;


@Entity
public class Bericht implements Comparable<Bericht> {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long Id;			// unieke identifier
	private String inhoud; 		// Het verzonden berricht
	private String afzender; 		// Het verzonden berricht
	public String getAfzender() {
		return afzender;
	}

	public void setAfzender(String afzender) {
		this.afzender = afzender;
	}
	private long VerzenderID; 	// Persoon
	private long ontvangerID; 	// Gesprek
	private LocalDateTime datum;// Datum
	
	public LocalDateTime getDatum() {
		return datum;
	}
	
	public void setDatum(LocalDateTime datum) {
		this.datum = datum;
	}
	private int status;			// status van het bericht (moet nog implementeren)
	
	
	public long getId() {
		return Id;
	}
	public void setId(long id) {
		Id = id;
	}
	public String getInhoud() {
		return inhoud;
	}
	public void setInhoud(String inhoud) {
		this.inhoud = inhoud;
	}
	public long getVerzenderID() {
		return VerzenderID;
	}
	public void setVerzenderID(long verzenderID) {
		VerzenderID = verzenderID;
	}
	public long getOntvangerID() {
		return ontvangerID;
	}
	public void setOntvangerID(long ontvangerID) {
		this.ontvangerID = ontvangerID;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	@Override
	public int compareTo(Bericht anderBericht) {
		if (this.getDatum().isAfter(anderBericht.getDatum())) 
			return 1;
		if (this.getDatum().isAfter(anderBericht.getDatum()))
			return -1;
		return 0;
	}
	
	


}


