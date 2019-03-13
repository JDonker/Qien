package com.qien.controller;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qien.Gesprek;

@Service
@Transactional
public class GesprekService {
	
	@Autowired
	private gesprekRepository gesprekkenRepository;
	
	// maakt een iterable lijst van alle verzorgers
	public Iterable<Gesprek>  findAll() {
		Iterable<Gesprek> gesprekken = gesprekkenRepository.findAll();
		return gesprekken;
	}
	
	// hier wordt een nieuw Gesprek toegevoegd aan de repository
	public Gesprek save(Gesprek gesprek){
		Gesprek gesprek2 = gesprekkenRepository.save(gesprek);
		return gesprek2;
	}
	
	public void delete(Gesprek gesprek){
		gesprekkenRepository.delete(gesprek); 
	}
	
	public Optional<Gesprek> findById(long id) {
		return gesprekkenRepository.findById(id);
	}
	
	public Optional<Gesprek> findBynaam(String naam) {
		System.out.println(naam);
		return gesprekkenRepository.findByNaam(naam);
	}
	
	
	public boolean delete(long id) {
		if (id<=0) {
			return false;
		}
		if (!gesprekkenRepository.findById(id).isPresent()) {
			return false;
		};
		gesprekkenRepository.deleteById(id);
		return true;
	}

}
