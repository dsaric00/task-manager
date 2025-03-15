package com.example.two.taskmanager.models;


import jakarta.persistence.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table (name ="user")
public class User {

    @Id
    @GeneratedValue
    private long id;

    @Size(min = 2, message = "Polje ime mora biti izmeđi 2 i 20 znakova.")
    @NotBlank(message = "Polje ime je obavezno")
    String ime;

    @Size(min = 2, message = "Polje prezime mora biti izmeđi 2 i 20 znakova.")
    @NotBlank(message = "Polje prezime je obavezno")
    String prezime;

    @NotBlank(message = "Polje email je obavezno")
    @Email(message = "Email adresa nora biti ispravnog formata")
    String email;

    @NotBlank(message = "Molimo unesite lozinku")
    String password;

    @NotBlank(message = "Molimo ponovite lozinku")
    @Transient
    String passwordConfirm;
}
