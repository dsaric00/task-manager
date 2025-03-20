package com.example.two.taskmanager.models;


import jakarta.persistence.*;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table (name ="user")
public class User {

    @Id
    @GeneratedValue
    private long id;

    @Size(min = 2, message = "Polje ime mora biti izmeđi 2 i 20 znakova.")
    @NotBlank(message = "Polje ime je obavezno")
    String name;

    @Size(min = 2, message = "Polje prezime mora biti izmeđi 2 i 20 znakova.")
    @NotBlank(message = "Polje prezime je obavezno")
    String surname;

    @NotBlank(message = "Polje email je obavezno")
    @Email(message = "Email adresa nora biti ispravnog formata")
    String email;

    @NotBlank(message = "Molimo unesite lozinku")
    String password;

    @NotBlank(message = "Molimo ponovite lozinku")
    @Transient
    String passwordConfirm;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    Set<Role> roles = new HashSet<>();

    //Constructor
    public User(Long id, String name, String surname, String email, String password, String passwordConfirm) {
        this.id=id;
        this.name =name;
        this.surname=surname;
        this.email=email;
        this.password=password;
        this.passwordConfirm=passwordConfirm;
        roles.add(Role.USER);
    }

    public User() {

    }

    //Getter and Setter
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    //Method for checking password
    @AssertTrue(message = "Lozinke se moraju podudarati")
    public boolean isPasswordEqual(){
        try{
            return this.password.equals(this.passwordConfirm);
        }catch(Exception e){
            return  false;
        }
    }

}
