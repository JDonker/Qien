package com.qien;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;


@Entity
public class Gesprek {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long Id;					// unieke identifier
	@OneToMany(fetch = FetchType.EAGER)
	private List<Bericht> berichten; 	// berichten in gesprek
	@OneToMany(fetch = FetchType.EAGER)
	private Set<Gebruiker> gebruikers; // gebruikers in gesprek
	private String naam;
	private long gebruikerID;
	
	Gesprek(){
		berichten = new ArrayList<Bericht>();
		gebruikers = new LinkedHashSet<Gebruiker>();	
	}
	
	public long getGebruikerID() {
		return gebruikerID;
	}
	public void setGebruikerID(long gebruikerID) {
		this.gebruikerID = gebruikerID;
	}
	public long getId() {
		return Id;
	}
	public void setId(long id) {
		Id = id;
	}
	public List<Bericht> getBerichten() {
		return berichten;
	}
	public void setBerichten(List<Bericht> berichten) {
		this.berichten = berichten;
	}
	public String getNaam() {
		return naam;
	}
	public void setNaam(String naam) {
		this.naam = naam;
	}
	
	public Set<Gebruiker> getGebruikers() {
		return gebruikers;
	}

	public void setGebruikers(Set<Gebruiker> gebruikers) {
		this.gebruikers = gebruikers;
	}
}
