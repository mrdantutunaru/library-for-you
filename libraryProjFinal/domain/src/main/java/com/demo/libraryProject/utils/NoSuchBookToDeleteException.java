package com.demo.libraryProject.utils;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such Book to delete") //404
public class NoSuchBookToDeleteException extends RuntimeException {

}
