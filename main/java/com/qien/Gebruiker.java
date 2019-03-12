package com.qien;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;


@Entity
public class Gebruiker {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long Id;					// unieke identifier
	private String naam; 				// Het verzonden berricht
	public Set<Bericht> getBerichten() {
		return berichten;
	}

	public void setBerichten(Set<Bericht> berichten) {
		this.berichten = berichten;
	}
	private String wachtwoord; 			// Persoon
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JsonIgnore
	private List<Gesprek> gesprekken;	// Aan welke gesprekken neemt hij mee
	
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JsonIgnore
	private Set<Bericht> berichten; 	// berichten in gesprek
	
	public Gebruiker(){
		gesprekken = new ArrayList<Gesprek>();	
		berichten = new LinkedHashSet<Bericht>();	
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
