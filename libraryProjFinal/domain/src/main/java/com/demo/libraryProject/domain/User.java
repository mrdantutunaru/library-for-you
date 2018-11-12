package com.demo.libraryProject.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private int id;

    @Email(message = "*Incorrect format for email")
    @NotBlank(message = "*Can't be blank")
    @Column(name = "email")
    private String email;

    @NotBlank(message = "*Can't be blank")
    @Column(name = "password")
    @Length(min = 6, message = "*Your password must have at least 6 characters")
    private String password;

    @NotBlank(message = "*Can't be blank")
    @Column(name = "name")
    private String name;

    @NotBlank(message = "*Can't be blank")
    @Column(name = "last_name")
    private String lastName;

    @Column(name = "active")
    private int active;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "id_user"), inverseJoinColumns = @JoinColumn(name = "id_role"))
    private Set<Role> roles;

}


