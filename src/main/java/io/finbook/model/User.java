package io.finbook.model;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity(value = "users", noClassnameStored = true)
public class User {

    @Id
    private ObjectId id;
    private String name;
    private String surname;
    private String cif;
    private String email;
    private String phoneNumber;

    public User() {
    }

    public User(ObjectId id, String name, String surname, String cif, String email, String phoneNumber) {
        super();
        this.name = name;
        this.surname = surname;
        this.cif = cif;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
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

    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
