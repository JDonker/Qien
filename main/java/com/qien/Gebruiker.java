package com.qien;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;


@Entity
public class Gebruiker {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long Id;					// unieke identifier
	private String naam; 				// Het verzonden berricht
	private String wachtwoord; 			// Persoon
	@ManyToMany
	@JsonIgnore
	private List<Gesprek> gesprekken;	// Aan welke gesprekken neemt hij mee
	
	public Gebruiker(){
		gesprekken = new ArrayList<Gesprek>();	
	}
	
	public long getId() {
		return Id;
	}
	public void setId(long id) {
		Id = id;
	}
	public String getNaam() {
		return naam;
	}
	public void setNaam(String naam) {
		this.naam = naam;
	}
	public String getWachtwoord() {
		return wachtwoord;
	}
	public void setWachtwoord(String wachtwoord) {
		this.wachtwoord = wachtwoord;
	}
	public List<Gesprek> getGesprekken() {
		return gesprekken;
	}
	public void setGesprekken(List<Gesprek> gesprekken) {
		this.gesprekken = gesprekken;
	}

	

}
