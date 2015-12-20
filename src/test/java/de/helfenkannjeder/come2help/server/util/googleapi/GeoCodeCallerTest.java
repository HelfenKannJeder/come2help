package de.helfenkannjeder.come2help.server.util.googleapi;

import de.helfenkannjeder.come2help.server.domain.Address;
import de.helfenkannjeder.come2help.server.domain.Coordinate;
import org.junit.Assert;
import org.junit.Test;

//TODO how about this test while coding without internet? We might have to mock something
public class GeoCodeCallerTest {

    @Test
    public void testGetLatAndLgnForFullAddress() {
        Address address = new Address();
        address.setZipCode("76135");
        address.setStreet("Kaiserstraße");
        address.setCity("Karlsruhe");
        address.setStreetNumber("1");

        address.updateCoordinates();
        Coordinate coordinate = address.getCoordinate();
        Assert.assertEquals(49.00869609999999, coordinate.getLatitude(), 0);
        Assert.assertEquals(8.4163198, coordinate.getLongitude(), 0);
    }

    @Test
    public void testGetLatAndLgnForWithoutCity() {
        Address address = new Address();
        address.setZipCode("76135");
        address.setStreet("Kaiserstraße");
        address.setStreetNumber("1");

        address.updateCoordinates();
        Coordinate coordinate = address.getCoordinate();
        Assert.assertEquals(49.00869609999999, coordinate.getLatitude(), 0);
        Assert.assertEquals(8.4163198, coordinate.getLongitude(), 0);
    }

    @Test
    public void testGetLatAndLgnForWithoutZip() {
        Address address = new Address();
        address.setCity("Karlsruhe");
        address.setStreet("Kaiserstraße");
        address.setStreetNumber("1");

        address.updateCoordinates();
        Coordinate coordinate = address.getCoordinate();
        Assert.assertEquals(49.00869609999999, coordinate.getLatitude(), 0);
        Assert.assertEquals(8.4163198, coordinate.getLongitude(), 0);
    }

    @Test
    public void testGetLatAndLgnForWithoutStreet() {
        Address address = new Address();
        address.setCity("Karlsruhe");

        address.updateCoordinates();
        Coordinate coordinate = address.getCoordinate();
        Assert.assertEquals(49, coordinate.getLatitude(), 0.1);
        Assert.assertEquals(8.4, coordinate.getLongitude(), 0.1);
    }
}
