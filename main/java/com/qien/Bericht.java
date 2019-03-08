package com.qien;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;


@Entity
public class Bericht {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long Id;			// unieke identifier
	private String inhoud; 		// Het verzonden berricht
	private long VerzenderID; 	// Persoon
	private long ontvangerID; 	// Gesprek
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


}


