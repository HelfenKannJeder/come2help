package de.helfenkannjeder.come2help.server.domain;

import de.helfenkannjeder.come2help.server.util.googleapi.GeoCodeCaller;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.validation.constraints.NotNull;

@Embeddable
public class Address {

    private String street;

    private String streetNumber;

    @NotNull
    private String zipCode;

    private String city;

    @Embedded
    private Coordinate coordinate;

    public Address() {
    }

    /**
     *
     * @param zipCode
     * @param city
     * @param street
     * @param streetNumber
     */
    public Address(String zipCode, String city, String street, String streetNumber) {
        this.zipCode = zipCode;
        this.city = city;
        this.street = street;
        this.streetNumber = streetNumber;
    }

    public void update(Address o) {
        this.zipCode = o.zipCode;
        this.city = o.city;
        this.street = o.street;
        this.streetNumber = o.streetNumber;
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

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public void updateCoordinates() {
        this.coordinate = GeoCodeCaller.calculateCoordinateForAddress(this);
    }
}
