package de.helfenkannjeder.come2help.server.rest.dto;

public class UserDto {

    private String givenName;
    private String surname;
    private String email;
    private Boolean adult;

    public UserDto() {
    }

    public UserDto(String givenName, String surname, String email, Boolean adult) {
        this.givenName = givenName;
        this.surname = surname;
        this.email = email;
        this.adult = adult;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getAdult() {
        return adult;
    }

    public void setAdult(Boolean adult) {
        this.adult = adult;
    }

}
