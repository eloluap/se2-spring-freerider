package de.freerider.datamodel;

public class Customer {

    enum Status {
        New, InRegistration, Active, Suspended, Deleted
    }

    private String id;
    private String lastName;
    private String firstName;
    private String contact;
    private Status status;

    public Customer() {
        this.id = null;
        this.lastName = "";
        this.firstName = "";
        this.contact = "";
        this.status = Status.New;
    }

    public Customer(String firstName, String lastName, String contact) {
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
        if (id == null || this.id == null) {
            this.id = id;
        }
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        if (lastName == null) {
            lastName = "";
        }
        this.lastName = lastName;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        if (firstName == null) {
            firstName = "";
        }
        this.firstName = firstName;
    }

    public String getContact() {
        return this.contact;
    }

    public void setContact(String contact) {
        if (contact == null) {
            contact = "";
        }
        this.contact = contact;
    }

    public Status getStatus() {
        return this.status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}