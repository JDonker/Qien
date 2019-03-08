package com.qien.controller;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import com.qien.Gesprek;


@Component
public interface gesprekRepository extends CrudRepository<Gesprek, Long> { 
}
