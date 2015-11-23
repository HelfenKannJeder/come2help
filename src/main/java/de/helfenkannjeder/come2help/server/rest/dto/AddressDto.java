package de.helfenkannjeder.come2help.server.rest.dto;

import javax.validation.constraints.NotNull;

import de.helfenkannjeder.come2help.server.domain.Address;

public class AddressDto {

    @NotNull(message = "zipCode.not.null")
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

    public AddressDto setZipCode(String zipCode) {
        this.zipCode = zipCode;
        return this;
    }

    public String getCity() {
        return city;
    }

    public AddressDto setCity(String city) {
        this.city = city;
        return this;
    }

    public String getStreet() {
        return street;
    }

    public AddressDto setStreet(String street) {
        this.street = street;
        return this;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public AddressDto setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
        return this;
    }
}
