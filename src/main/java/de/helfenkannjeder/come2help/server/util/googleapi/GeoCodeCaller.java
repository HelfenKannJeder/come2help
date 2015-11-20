package de.helfenkannjeder.come2help.server.util.googleapi;

import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.GeocoderRequestBuilder;
import com.google.code.geocoder.model.GeocoderRequest;
import com.google.code.geocoder.model.GeocoderResult;
import com.google.code.geocoder.model.LatLng;
import de.helfenkannjeder.come2help.server.domain.Address;
import java.io.IOException;
import java.util.List;

public class GeoCodeCaller {

    /**
     * calls the google geocode api and enrich the given address with latitude
     * and longitude information
     *
     * @param address
     * @return
     */
    public static Address enrichAddressWithLatitudeAndLongitude(Address address) {
        String requestAddress = address.getZipCode();
        if (address.getStreet() != null) {
            requestAddress += "," + address.getStreet();
            if (address.getStreetNumber() != null) {
                requestAddress += "," + address.getStreetNumber();
            }
        }
        requestAddress += ", Deutschland";
        System.out.println(requestAddress);
        try {
            final Geocoder geocoder = new Geocoder();
            GeocoderRequest geocoderRequest = new GeocoderRequestBuilder().setAddress(requestAddress).setLanguage("de").getGeocoderRequest();
            List<GeocoderResult> geocoderResults = geocoder.geocode(geocoderRequest).getResults();
            if (geocoderResults.isEmpty()) {
                throw new RuntimeException("Google geocode could not find any results for: " + requestAddress); //TODO for testing purposes an exception is fine, how about in production?
            }
            //use first result, hopefully it is the best
            LatLng location = geocoderResults.get(0).getGeometry().getLocation();
            address.setLatitude(location.getLat().doubleValue());
            address.setLongitude(location.getLng().doubleValue());
        } catch (IOException e) {
            throw new RuntimeException("Could not gather google geocode api for parameter: " + requestAddress); //TODO for testing purposes an exception is fine, how about in production?
        }
        return address;
    }

}
