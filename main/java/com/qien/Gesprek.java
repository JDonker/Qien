package com.qien;

import java.util.ArrayList;
import java.util.List;

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
	//private List<Gebruiker> gebruikers; // gebruikers in gesprek
	private String naam;
	private long gebruikerID;
	
	Gesprek(){
		berichten = new ArrayList<Bericht>();
	}
	
//	public List<Gebruiker> getGebruikers() {
//		return gebruikers;
//	}
//	public void setGebruikers(List<Gebruiker> gebruikers) {
//		this.gebruikers = gebruikers;
//	}
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
}
