package de.helfenkannjeder.come2help.server.util;

import de.helfenkannjeder.come2help.server.domain.Address;


public class DistanceCalculator {

    /**
     * calculates the distance between two adresses based on latitude and longitude
     *
     * @param address1
     * @param address2
     * @return
     */
    public static double getDistanceFor(Address address1, Address address2) {
        double lat1 = address1.getLat();
        double lng1 = address1.getLng();
        double lat2 = address2.getLat();
        double lng2 = address2.getLng();

        //formula based on http://www.movable-type.co.uk/scripts/latlong.html
        int R = 6371000; // metres
        double latitude1 = Math.toRadians(lat1);
        double latitude2 = Math.toRadians(lat2);
        double diffLatitude = Math.toRadians(lat2-lat1);
        double diffLongitude = Math.toRadians((lng2-lng1));

        double a = Math.sin(diffLatitude/2) * Math.sin(diffLatitude/2) + Math.cos(latitude1) * Math.cos(latitude2) * Math.sin(diffLongitude / 2) * Math.sin(diffLongitude/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        double d = R * c;
        return d;
    }
}
