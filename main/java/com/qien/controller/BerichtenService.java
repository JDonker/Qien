package com.qien.controller;


import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.qien.Bericht;
import com.qien.Gesprek;

@Service
@Transactional
public class BerichtenService {
	
	@Autowired
	private berichtRepository berichtenRepository;
	@Autowired
	private gesprekRepository gesprekRepository;
	
	// maakt een iterable lijst van alle verzorgers
	public Iterable<Bericht>  findAll() {
		Iterable<Bericht> berichten = berichtenRepository.findAll();

		return berichten;
	}
	
	
	// hier wordt een nieuw Bericht toegevoegd aan de repository
	public Bericht save(Bericht bericht){
		Bericht bericht1 = berichtenRepository.save(bericht);
		return bericht1;
	}
	
	public void delete(Bericht bericht){
		berichtenRepository.delete(bericht); 
	}
	
	public Optional<Bericht> findById(long id) {
		return berichtenRepository.findById(id);
	}
	
	

	public boolean delete(long id) {
		if (id<=0) {
			return false;
		}
		
		if (!berichtenRepository.findById(id).isPresent()) {
			return false;
		};
		
		berichtenRepository.deleteById(id);
		return true;
	}
	
	
	
	


}
