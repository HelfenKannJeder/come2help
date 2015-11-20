package de.helfenkannjeder.come2help.server.util;

import de.helfenkannjeder.come2help.server.domain.Coordinate;

public class DistanceCalculator {

    /**
     * Calculates the distance between two adresses based on latitude and
     * longitude.
     *
     * @param coordinate1
     * @param coordinate2
     * @return
     */
    public static double getDistanceFor(Coordinate coordinate1, Coordinate coordinate2) {
        double lat1 = coordinate1.getLatitude();
        double lng1 = coordinate1.getLongitude();
        double lat2 = coordinate2.getLatitude();
        double lng2 = coordinate2.getLongitude();

        //formula based on http://www.movable-type.co.uk/scripts/latlong.html
        int R = 6371000; // metres
        double latitude1 = Math.toRadians(lat1);
        double latitude2 = Math.toRadians(lat2);
        double diffLatitude = Math.toRadians(lat2 - lat1);
        double diffLongitude = Math.toRadians((lng2 - lng1));

        double a = Math.sin(diffLatitude / 2) * Math.sin(diffLatitude / 2) + Math.cos(latitude1) * Math.cos(latitude2) * Math.sin(diffLongitude / 2) * Math.sin(diffLongitude / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        double d = R * c;
        return d;
    }
}
