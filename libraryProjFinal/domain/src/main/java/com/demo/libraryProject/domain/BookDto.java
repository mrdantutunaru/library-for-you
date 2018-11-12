package com.demo.libraryProject.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BookDto {
    @ApiModelProperty(notes = "The unique id of book.")
    private int idBook;

    @ApiModelProperty(notes = "The title of the book.")
    private String title;

    @ApiModelProperty(notes = "The description of the book.")
    private String description;

    @ApiModelProperty(notes = "The gender of the book.")
    private String gender;

    @ApiModelProperty(notes = "The isbn of the book.")
    private int isbn;

    @ApiModelProperty(notes = "The year of the book.")
    private int year;
}

