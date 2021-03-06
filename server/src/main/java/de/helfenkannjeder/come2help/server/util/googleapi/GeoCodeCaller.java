package de.helfenkannjeder.come2help.server.util.googleapi;

import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.GeocoderRequestBuilder;
import com.google.code.geocoder.model.GeocoderRequest;
import com.google.code.geocoder.model.GeocoderResult;
import com.google.code.geocoder.model.LatLng;
import de.helfenkannjeder.come2help.server.domain.Address;
import de.helfenkannjeder.come2help.server.domain.Coordinate;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class GeoCodeCaller {

    /**
     * calls the google geocode api and enrich the given address with latitude
     * and longitude information
     *
     * It expects a valid german address. There is not check if the address is correct.
     *
     * @param address
     * @return
     */
    public static Coordinate calculateCoordinateForAddress(Address address) {
        String requestAddress = "";
        requestAddress += address.getStreet() != null ? address.getStreet()  + " " : "";
        requestAddress += (address.getStreet() != null && address.getStreetNumber()  != null) ? address.getStreetNumber()  + " " : "";
        requestAddress += address.getZipCode() != null ? address.getZipCode()  + " " : "";
        requestAddress += address.getCity()!= null ? address.getCity()     + " " : "";
        requestAddress += ", Deutschland";

        try {
            final Geocoder geocoder = new Geocoder();
            GeocoderRequest geocoderRequest = new GeocoderRequestBuilder().setAddress(requestAddress).setLanguage("de").getGeocoderRequest();
            List<GeocoderResult> geocoderResults = geocoder.geocode(geocoderRequest).getResults();
            if (geocoderResults.isEmpty()) {
                throw new RuntimeException("Google geocode could not find any results for: " + requestAddress); //TODO for testing purposes an exception is fine, how about in production?
            }
            //use first result, hopefully it is the best
            LatLng location = geocoderResults.get(0).getGeometry().getLocation();

            return new Coordinate(location.getLat().doubleValue(), location.getLng().doubleValue());
        } catch (IOException e) {
            throw new RuntimeException("Could not gather google geocode api for parameter: " + requestAddress); //TODO for testing purposes an exception is fine, how about in production?
        }
    }

}
