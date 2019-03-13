package com.qien;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotBlank;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;


@Entity
public class Gebruiker {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long Id;					// unieke identifier
	@NotBlank
	private String naam,rol; 				// Het verzonden berricht
	@NotBlank
	@JsonIgnore
	private String wachtwoord;
	@ManyToMany(fetch = FetchType.EAGER)
	@JsonIgnore
	private List<Gesprek> gesprekken;	// Aan welke gesprekken neemt hij mee
	@ManyToMany(fetch = FetchType.EAGER)
	@JsonIgnore
	private Set<Bericht> berichten; 	// berichten in gesprek
	private long huidigGesprek;
	private boolean persoonlijkGesprek;

	public long getHuidigGesprek() {
		return huidigGesprek;
	}

	public void setHuidigGesprek(long huidigGesprek) {
		this.huidigGesprek = huidigGesprek;
	}

	public boolean isPersoonlijkGesprek() {
		return persoonlijkGesprek;
	}

	public void setPersoonlijkGesprek(boolean persoonlijkGesprek) {
		this.persoonlijkGesprek = persoonlijkGesprek;
	}

	public Set<Bericht> getBerichten() {
		return berichten;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public void setBerichten(Set<Bericht> berichten) {
		this.berichten = berichten;
	}
	
	public Gebruiker(){
		gesprekken = new ArrayList<Gesprek>();	
		berichten = new LinkedHashSet<Bericht>();	
	}
	
	public Gebruiker(String username, String wachtwoord, String rol) {
		this();
	    this.naam = username;
	    this.wachtwoord = wachtwoord;
	    this.rol = rol;
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
