package de.helfenkannjeder.come2help.util.googleapi;

import de.helfenkannjeder.come2help.server.domain.Address;
import de.helfenkannjeder.come2help.server.util.googleapi.GeoCodeCaller;
import org.junit.Assert;
import org.junit.Test;


public class GeoCodeCallerTest {

    @Test //TODO how about this test while coding without internet? We might have to mock something
    public void testGetLatAndLgnForAddress() {
        Address address = new Address();
        address.setZip("76135");
        address.setStreet("Kaiserstraße");
        address.setStreetNumber("1");

        address = GeoCodeCaller.getLatAndLgnForAddress(address);
        System.out.println(address.getLat() + " " + address.getLng());
        Assert.assertEquals(49.00869609999999, address.getLat(), 0);
        Assert.assertEquals(8.4163198, address.getLng(), 0);
    }
}
