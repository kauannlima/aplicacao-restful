package br.com.klima.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.klima.model.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {}