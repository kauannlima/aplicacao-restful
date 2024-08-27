package br.com.klima.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
 
import br.com.klima.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {}