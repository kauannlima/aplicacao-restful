package br.com.klima.services;


import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Service;

import br.com.klima.controllers.PersonController;
import br.com.klima.data.vo.v1.PersonVO;
import br.com.klima.exceptions.ResourceNotFoundException;
import br.com.klima.mapper.DozerMapper;
import br.com.klima.repositories.UserRepository;

@Service
public class UserServices implements UserDetailsService{
	
	private Logger logger = Logger.getLogger(UserServices.class.getName());
	
	@Autowired
	UserRepository repository;

	public UserServices() {
		super();
	}

	public UserServices (UserRepository repository) {
		this.repository = repository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.info("Finding one user by name "+ username +"!");
		var user = repository.findByUsername(username);
		if (user != null) {
			return user;
		}else {
			throw new UsernameNotFoundException("Username "+ username +" not found");
		}
	}
}