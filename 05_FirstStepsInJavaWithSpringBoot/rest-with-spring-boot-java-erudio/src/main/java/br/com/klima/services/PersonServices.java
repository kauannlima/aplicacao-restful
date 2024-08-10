package br.com.klima.services;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.klima.exceptions.ResourceNotFoundException;
import br.com.klima.mapper.DozerMapper;
import br.com.klima.mapper.custom.PersonMapper;
import br.com.klima.model.Person;
import br.com.klima.repository.PersonRepository;
import br.com.klima.vo.v1.PersonVO;
import br.com.klima.vo.v2.PersonVOV2;

@Service
public class PersonServices {

	private Logger logger = Logger.getLogger(PersonServices.class.getName());

	@Autowired
	PersonRepository repository;
	
	@Autowired
	PersonMapper mapper;

	public List<PersonVO> findAll() {

		return DozerMapper.parseListObjects(repository.findAll(), PersonVO.class);
	}

	public PersonVO create(PersonVO person) {
		logger.info("Creating one person!");

		var entity = DozerMapper.parseObject(person, Person.class);
		var vo = DozerMapper.parseObject(repository.save(entity), PersonVO.class);
		return vo;

	}
	
	public PersonVOV2 createV2(PersonVOV2 person) {
		logger.info("Creating one person with V2!");
		var entity = mapper.convertVoToEntity(person);
		var vo = mapper.convertEntityToVo(repository.save(entity));
		return vo;
	}

	public PersonVO update(PersonVO person) {
		logger.info("Updating one person!");

		var entity = repository.findById(person.getId())
				.orElseThrow(() -> new ResourceNotFoundException("No receords found for this ID: " + person.getId()));

		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setAddress(person.getAddress());
		entity.setGender(person.getGender());

		var vo = DozerMapper.parseObject(repository.save(entity), PersonVO.class);
		return vo;
	}

	public void delete(Long id) {
		logger.info("Deleting one person!");

		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No receords found for this ID: " + id));

		repository.delete(entity);

	}

	public PersonVO findById(Long id) {
		logger.info("Find one person!");

		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No receords found for this ID: " + id));

		return DozerMapper.parseObject(repository.save(entity), PersonVO.class);
	}



}