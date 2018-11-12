//package com.demo.libraryProject.entities;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import lombok.*;
//import org.hibernate.annotations.OnDelete;
//import org.hibernate.annotations.OnDeleteAction;
//import javax.persistence.*;
//
//@Entity
//@Table(name="loans")
//@Data
//@ToString
//@AllArgsConstructor
//@NoArgsConstructor
//public class Loans {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int idLoan;
//
//    @Column(name = "loan_date")
//    private String loanDate;
//
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name="id_user", nullable = false)
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    @JsonIgnore
//    private User user;
//
//}
