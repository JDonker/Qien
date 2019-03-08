package com.qien.controller;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import com.qien.Bericht;

@Component
public interface berichtRepository extends CrudRepository<Bericht, Long>{


}
