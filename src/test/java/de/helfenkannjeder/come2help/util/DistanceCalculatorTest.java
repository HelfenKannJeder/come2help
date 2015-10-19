package de.helfenkannjeder.come2help.util;

import de.helfenkannjeder.come2help.server.domain.Address;
import de.helfenkannjeder.come2help.server.util.DistanceCalculator;
import org.junit.Assert;
import org.junit.Test;

public class DistanceCalculatorTest {

    @Test
    public void testLanLngDistance() {
        Address stuttgart = new Address();
        stuttgart.setLat(48.7758459);
        stuttgart.setLng(9.1829321);

        Address karlsruhe = new Address();
        karlsruhe.setLat(49.0068901);
        karlsruhe.setLng(8.4036527);

        double distanceInKm = DistanceCalculator.getDistanceFor(stuttgart, karlsruhe) / 1000;
        Assert.assertEquals(62.5, distanceInKm, 0.1);
    }
}
