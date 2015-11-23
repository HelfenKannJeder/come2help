package de.helfenkannjeder.come2help.server.util.googleapi;

import de.helfenkannjeder.come2help.server.domain.Address;
import de.helfenkannjeder.come2help.server.domain.Coordinate;
import org.junit.Assert;
import org.junit.Test;

public class GeoCodeCallerTest {

    @Test //TODO how about this test while coding without internet? We might have to mock something
    public void testGetLatAndLgnForAddress() {
        Address address = new Address();
        address.setZipCode("76135");
        address.setStreet("Kaiserstraße");
        address.setStreetNumber("1");

        address.updateCoordinates();
        Coordinate coordinate = address.getCoordinate();
        System.out.println(coordinate.getLatitude() + " " + coordinate.getLongitude());
        Assert.assertEquals(49.00869609999999, coordinate.getLatitude(), 0);
        Assert.assertEquals(8.4163198, coordinate.getLongitude(), 0);
    }
}
