//package com.demo.libraryProject.annotations;
//
//
//import com.demo.libraryProject.entities.Book;
//import org.springframework.validation.Errors;
//import org.springframework.validation.Validator;
//
//public class BookNameValidator implements Validator {
//
//
//    private static final String REQUIRED = "required";
//
//    @Override
//    public void validate(Object obj, Errors errors) {
//        Book book = (Book) obj;
//        String title = book.getTitle();
//
//        if (title==null) {
//            errors.rejectValue("title", REQUIRED, REQUIRED);
//        }
//    }
//
//    @Override
//    public boolean supports(Class<?> clazz) {
//        return Book.class.isAssignableFrom(clazz);
//    }
//}
