package br.com.klima.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import br.com.klima.services.PersonServices;
import br.com.klima.vo.v1.PersonVO;
import br.com.klima.vo.v2.PersonVOV2;

@RestController
@RequestMapping("/person")
public class PersonController {
	
		
	@Autowired
		private PersonServices service;
	//	private PersonServices service = new PersonServices();
		
		@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
		public PersonVO findById(@PathVariable(value = "id") Long id){

			return service.findById(id);
		}
		
		@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
		public List<PersonVO> findAll() {
			return service.findAll();
		}
		
		@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
		public PersonVO create(@RequestBody PersonVO person) {

			return service.create(person);
		}
		
		@PostMapping(value = "/v2",produces = MediaType.APPLICATION_JSON_VALUE)
		public PersonVOV2 createV2(@RequestBody PersonVOV2 person) {

			return service.createV2(person);
		}
				
		@PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
		public PersonVO update(@RequestBody PersonVO person) {

			return service.update(person);
		}
		
		@DeleteMapping(value = "/{id}")
		public ResponseEntity<?> delete(@PathVariable(value = "id") Long id){
		service.delete(id);
		return ResponseEntity.noContent().build();
		}
		
	


}
