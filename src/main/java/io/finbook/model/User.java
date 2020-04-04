package io.finbook.model;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity(value = "users", noClassnameStored = true)
public class User {

    @Id
    private ObjectId id;
    private String fullName;
    private String idNumber;
    private String email;
    private String phoneNumber;

    public User() {
    }

    public User(String fullName, String idNumber, String email, String phoneNumber) {
        super();
        this.fullName = fullName;
        this.idNumber = idNumber;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
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
