package de.helfenkannjeder.come2help.server.rest.dto;

import de.helfenkannjeder.come2help.server.domain.Address;

public class AddressDto {

    private String zipCode;
    private String city;
    private String street;
    private String streetNumber;

    public AddressDto() {
    }

    public AddressDto(String zipCode, String city, String street, String streetNumber) {
        this.zipCode = zipCode;
        this.city = city;
        this.street = street;
        this.streetNumber = streetNumber;
    }

    public static AddressDto createFullDto(Address address) {
        return new AddressDto(address.getZipCode(), address.getCity(), address.getStreet(), address.getStreetNumber());
    }

    public static Address createAddress(AddressDto dto) {
        return new Address(dto.zipCode, dto.city, dto.street, dto.streetNumber);
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }
}
