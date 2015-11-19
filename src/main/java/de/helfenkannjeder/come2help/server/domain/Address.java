package de.helfenkannjeder.come2help.server.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Address extends AbstractVersionedAuditable {

    @Id
    @GeneratedValue
    private Long id;

    private String street;

    private String streetNumber;

    @NotNull
    private String zipCode;

    private String city;

    @NotNull
    private double lat;

    @NotNull
    private double lng;

    public Address() {
    }

    /**
     *
     * @param id
     * @param zipCode
     * @param city
     * @param street
     * @param streetNumber
     */
    public Address(Long id, String zipCode, String city, String street, String streetNumber) {
        this.id = id;
        this.zipCode = zipCode;
        this.city = city;
        this.street = street;
        this.streetNumber = streetNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
