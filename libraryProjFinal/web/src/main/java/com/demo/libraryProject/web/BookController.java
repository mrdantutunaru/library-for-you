package com.demo.libraryProject.web;


import com.demo.libraryProject.domain.Book;
import com.demo.libraryProject.service.BookService;
import com.demo.libraryProject.utils.NoSuchBookException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.security.RolesAllowed;

@Controller
public class BookController {
    @Autowired
    private BookService bookService;

    public BookController (BookService bookService) {

        this.bookService=bookService;
    };

    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    @RequestMapping(path = "/")
    public String index() {
        return "index";
    }


    @Secured("ROLE_ADMIN")
    @RequestMapping(path = "/books/add-book", method = RequestMethod.GET)
    public String homePage(Model model) {
        model.addAttribute("book", new Book());
        return "edit";
    }

    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    @RequestMapping(path = "/books", method = RequestMethod.POST)
    public String addPageBook(@ModelAttribute Book book, Model model,BindingResult result) {
//        BookNameValidator bookNameValidator = new BookNameValidator();
//        bookNameValidator.validate(book,result);
//        if (result.hasErrors()){
//            return "edit";
//        }
 //       else {
            bookService.saveBook(book);
            model.addAttribute("books", bookService.getAllBooks());
            return "books";
       // }
    }

    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    @RequestMapping(path = "/books", method = RequestMethod.GET)
    public String getAllBooks(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "books";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(path = "/books/edit/{id}", method = RequestMethod.GET)
    public String editProduct(Model model, @PathVariable(value = "id") int id) {
        Book book = bookService.findOne(id);
        if(book==null) throw new NoSuchBookException();
        model.addAttribute("book", book);
        return "edit";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(path = "/books/delete/{id}", method = RequestMethod.GET)
    public String deleteBook(Model model, @PathVariable(required = true, name = "id") int id) {
        bookService.deleteById(id);
        model.addAttribute("books", bookService.getAllBooks());
        return "books";
    }

}
