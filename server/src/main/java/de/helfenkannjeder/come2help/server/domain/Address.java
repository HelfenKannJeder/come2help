package de.helfenkannjeder.come2help.server.domain;

import java.util.Objects;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

import de.helfenkannjeder.come2help.server.util.googleapi.GeoCodeCaller;

@Embeddable
public class Address {

    private String street;

    private String streetNumber;

    private String zipCode;

    private String city;

    @Embedded
    private Coordinate coordinate;

    public Address() {
    }

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

    public Address setStreet(String street) {
        this.street = street;
        return this;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public Address setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
        return this;
    }

    public String getZipCode() {
        return zipCode;
    }

    public Address setZipCode(String zipCode) {
        this.zipCode = zipCode;
        return this;
    }

    public String getCity() {
        return city;
    }

    public Address setCity(String city) {
        this.city = city;
        return this;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public Address setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
        return this;
    }

    public void updateCoordinates() {
        this.coordinate = GeoCodeCaller.calculateCoordinateForAddress(this);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.street);
        hash = 59 * hash + Objects.hashCode(this.streetNumber);
        hash = 59 * hash + Objects.hashCode(this.zipCode);
        hash = 59 * hash + Objects.hashCode(this.city);
        hash = 59 * hash + Objects.hashCode(this.coordinate);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Address other = (Address) obj;
        if (!Objects.equals(this.street, other.street)) {
            return false;
        }
        if (!Objects.equals(this.streetNumber, other.streetNumber)) {
            return false;
        }
        if (!Objects.equals(this.zipCode, other.zipCode)) {
            return false;
        }
        if (!Objects.equals(this.city, other.city)) {
            return false;
        }
        if (!Objects.equals(this.coordinate, other.coordinate)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Address{" + "street=" + street + ", streetNumber=" + streetNumber + ", zipCode=" + zipCode + ", city=" + city + ", coordinate=" + coordinate + '}';
    }
}
