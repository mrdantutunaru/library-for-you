package com.demo.libraryProject.repository;


import com.demo.libraryProject.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    List<Book> findAllByTitle(String title);

    Optional<Book> findByIdBook(int id);


}
