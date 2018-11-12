package com.esempla.libraryclient.domain;

import lombok.Data;

@Data
public class Book {

    private int idBook;

    private String title;

    private String description;

    private String gender;

    private int isbn;

    private int year;
}

