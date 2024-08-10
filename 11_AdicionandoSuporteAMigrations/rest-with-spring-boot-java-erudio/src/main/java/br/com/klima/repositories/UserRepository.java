package br.com.klima.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.klima.model.Person;
import br.com.klima.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	@Query("SELECT u FROM User WHERE u.userName =:userName")
	User findByUsername(@Param("userName") String userName);
}