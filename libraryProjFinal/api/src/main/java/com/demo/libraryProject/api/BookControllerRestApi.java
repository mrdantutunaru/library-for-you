package com.demo.libraryProject.api;

import com.demo.libraryProject.domain.Book;
import com.demo.libraryProject.domain.BookDto;
import com.demo.libraryProject.repository.BookRepository;
import com.demo.libraryProject.utils.NoSuchBookException;
import com.demo.libraryProject.utils.NoSuchBookToDeleteException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.annotation.security.RolesAllowed;
import java.net.URI;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;


@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
@Api(value = "library", description = "Operations that are part of BookRestApi Controller")
public class BookControllerRestApi {


    private final ModelMapper modelMapper;

    private final BookRepository bookRepository;

    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("")
    @ResponseBody
    @ApiOperation(value = "View a list of available books.", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list."),
            @ApiResponse(code = 401, message = "You are not authorized."),
            @ApiResponse(code = 403, message = "The resource is forbidden."),
            @ApiResponse(code = 404, message = "The resource is not found.")
    })
    public List<BookDto> getAllBooks(){

        return bookRepository.findAll()
                .stream().map(book -> convertToDto(book))
                .collect(Collectors.toList());
    }

    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("/{id}")
    @ApiOperation(value = "Get specific book by id.", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list."),
            @ApiResponse(code = 401, message = "You are not authorized."),
            @ApiResponse(code = 403, message = "The resource is forbidden."),
            @ApiResponse(code = 404, message = "The resource is not found."),
            @ApiResponse(code = 500, message = "Internal server error. The resource is not found.")
    })
    @ResponseBody
    public BookDto getBook(@PathVariable int id)
    {
        Book book = bookRepository.findOne(id);
        if (book==null)
            throw new NoSuchBookException();
        return  convertToDto(book);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete book from the library.", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list."),
            @ApiResponse(code = 401, message = "You are not authorized."),
            @ApiResponse(code = 403, message = "The resource is forbidden."),
            @ApiResponse(code = 404, message = "The resource is not found.")
    })
    public void deleteBook(@PathVariable int id) {
        Book book = bookRepository.findOne(id);
        if (book==null)
            throw new NoSuchBookToDeleteException();
        BookDto bookDto = convertToDto(book);
        bookRepository.delete(id);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("")
//    public ResponseEntity<Object> createStudent(@RequestBody Book book) {
//        Book savedBook = bookRepository.save(book);
//
//        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
//                .buildAndExpand(savedBook.getIdBook()).toUri();
//
//        return ResponseEntity.created(location).build();
//
//    }

    @ApiOperation(value = "Add a book to the library.", response = Iterable.class)
    public BookDto addBook(@RequestBody BookDto bookDto) throws Exception{
        Book book = convertToEntity(bookDto);
        Book bookCreated = bookRepository.save(book);
        return convertToDto(bookCreated);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/{id}")
    @ApiOperation(value = "Update a specific book from the library.", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list."),
            @ApiResponse(code = 401, message = "You are not authorized."),
            @ApiResponse(code = 403, message = "The resource is forbidden."),
            @ApiResponse(code = 404, message = "The resource is not found.")
    })
    @ResponseBody
    public void updateBook(@RequestBody BookDto bookDto, @PathVariable int id) throws ParseException {

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
        Book book = convertToEntity(bookDto);
        bookRepository.save(book);

    }

    private BookDto convertToDto(Book book) {

        BookDto bookDto = modelMapper.map(book, BookDto.class);
        return bookDto;
    }
    private Book convertToEntity(BookDto personDto) throws ParseException {

        Book book = modelMapper.map(personDto, Book.class);
        return book;
    }

}
