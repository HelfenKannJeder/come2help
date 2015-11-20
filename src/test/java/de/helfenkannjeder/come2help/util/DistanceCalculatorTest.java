package de.helfenkannjeder.come2help.util;

import de.helfenkannjeder.come2help.server.domain.Coordinate;
import de.helfenkannjeder.come2help.server.util.DistanceCalculator;
import org.junit.Assert;
import org.junit.Test;

public class DistanceCalculatorTest {

    @Test
    public void testLanLngDistance() {
        Coordinate stuttgart = new Coordinate();
        stuttgart.setLatitude(48.7758459);
        stuttgart.setLongitude(9.1829321);

        Coordinate karlsruhe = new Coordinate();
        karlsruhe.setLatitude(49.0068901);
        karlsruhe.setLongitude(8.4036527);

        double distanceInKm = DistanceCalculator.getDistanceFor(stuttgart, karlsruhe) / 1000;
        Assert.assertEquals(62.5, distanceInKm, 0.1);
    }
}
