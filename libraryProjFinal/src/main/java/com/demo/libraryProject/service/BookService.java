//package com.demo.libraryProject.service;
//
//import com.demo.libraryProject.entities.Book;
//import com.demo.libraryProject.repository.BookRepository;
//import com.demo.libraryProject.repository.RoleRepository;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Service;
//import javax.transaction.Transactional;
//import java.util.List;
//
//@Slf4j
//@Service
//public class BookService {
//
//    private BookRepository bookRepository;
//    private RoleRepository roleRepository;
//    private BCryptPasswordEncoder bCryptPasswordEncoder;
//
//    @Autowired
//    public BookService(BookRepository bookRepository,
//                       RoleRepository roleRepository,
//                       BCryptPasswordEncoder bCryptPasswordEncoder) {
//        this.bookRepository = bookRepository;
//        this.roleRepository = roleRepository;
//        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
//    }
//
//    @Transactional
//    public Book saveBook(Book book) {
//        log.info("A book was saved");
//        return bookRepository.save(book);
//    }
//
//    public List<Book> getAllBooks(){
//        log.info("All books are retrieved");
//        return bookRepository.findAll();
//    }
//
//    public Book findOne(int id){
//        return bookRepository.findOne(id);
//    }
//
//    @Transactional
//    public void deleteById(int id){
//        log.info("A book was deleted");
//        bookRepository.delete(id);
//    }
//
//    public List<Book> listBooksByTitle(String title) {
//        return bookRepository.findAllByTitle(title);
//    }
//}
