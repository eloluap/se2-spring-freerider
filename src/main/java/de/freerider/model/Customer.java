package de.freerider.model;

public class Customer {

    enum Status {New, InRegistration, Active, Suspended, Deleted}

    private String id;
    private String lastName;
    private String firstName;
    private String contact;
    private Status status;

    public Customer(String lastName, String firstName, String contact) {
        this.id = null;
        this.lastName = lastName;
        this.firstName = firstName;
        this.contact = contact;
        this.status = Status.New;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getContact() {
        return this.contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Status getStatus() {
        return this.status;
    }

    public void setId(Status status) {
        this.status = status;
    }
}