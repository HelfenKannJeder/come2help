package de.helfenkannjeder.come2help.server.domain;

public class Volunteer {

    private Long id;
    private String email;
    private String givenName;
    private String surname;
    private Address address;
    private String phone;
    private boolean adult;

    public Volunteer(Long id, String email, String givenName, String surname, Address address, String phone, boolean adult) {
        this.id = id;
        this.email = email;
        this.givenName = givenName;
        this.surname = surname;
        this.address = address;
        this.phone = phone;
        this.adult = adult;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean isAdult) {
        this.adult = isAdult;
    }
}
