package br.com.klima.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.klima.model.Person;

public interface PersonRepository extends JpaRepository<Person, Long>{

}
