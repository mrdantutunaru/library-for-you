//package com.demo.libraryProject.controller;
//
//import com.demo.libraryProject.Exceptions.NoSuchBookException;
//import com.demo.libraryProject.entities.Book;
//import com.demo.libraryProject.repository.BookRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.annotation.Secured;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
//
//import javax.annotation.security.RolesAllowed;
//import java.net.URI;
//import java.util.List;
//import java.util.Optional;
//
//
//@RestController
//@RequestMapping("/utils/v1/books")
//public class BookControllerRestApi {
//    @Autowired
//    private BookRepository bookRepository;
//
//    public BookControllerRestApi(BookRepository bookRepository){this.bookRepository=bookRepository;}
//
//
//    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
//    @GetMapping("")
//    public List<Book> getAllBooks(){
//        return bookRepository.findAll();
//    }
//
//    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
//    @GetMapping("/{id}")
//    public Book getBook(@PathVariable int id) {
//        Optional<Book> book = bookRepository.findByIdBook(id);
//
//        if (!book.isPresent())
//            throw new NoSuchBookException();
//
//        return book.get();
//    }
//
//    @Secured("ROLE_ADMIN")
//    @DeleteMapping("/{id}")
//    public void deleteBook(@PathVariable int id) {
//        bookRepository.delete(id);
//    }
//
//    @Secured("ROLE_ADMIN")
//    @PostMapping("")
//    public ResponseEntity<Object> createStudent(@RequestBody Book book) {
//        Book savedBook = bookRepository.save(book);
//
//        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
//                .buildAndExpand(savedBook.getIdBook()).toUri();
//
//        return ResponseEntity.created(location).build();
//
//    }
//
//    @Secured("ROLE_ADMIN")
//    @PutMapping("/{id}")
//    public ResponseEntity<Object> updateBook(@RequestBody Book book, @PathVariable int id) {
//
//        Optional<Book> studentOptional = bookRepository.findByIdBook(id);
//
//        if (!studentOptional.isPresent())
//            return ResponseEntity.notFound().build();
//
//        book.setIdBook(id);
//
//        bookRepository.save(book);
//
//        return ResponseEntity.noContent().build();
//    }
//
//}
