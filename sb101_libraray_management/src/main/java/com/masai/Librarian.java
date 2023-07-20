package com.masai;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Librarian {
    private String name;
    @Id
    private String email;
    private String password;

    public Librarian(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public boolean verifyPassword(String inputPassword) {
        return password.equals(inputPassword);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public String toString() {
        return "Librarian [name=" + name + ", email=" + email + ", password=" + password + "]";
    }

    public Object getId() {
        return email; // Return the email as the ID
    }
}
