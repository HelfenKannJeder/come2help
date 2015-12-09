package de.helfenkannjeder.come2help.server.domain;

import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import org.junit.Test;

public class VolunteerTest {

    @Test
    public void testUpdate() {
        final Long testId1 = 2L;
        final String testEmail1 = "a@b.de";
        final String testGivenName1 = "Hans";
        final String testSurname1 = "Meier";
        final Address testAddress1 = new Address("78393", "Hanshausen", "Meierstreet", "76c");
        final String testPhone1 = "872382394";
        final Boolean testAdult1 = false;
        final List<Ability> testAbilities1 = Arrays.asList(new Ability(1L, null, null, null, true, true));

        Volunteer volunteer1 = new Volunteer(testId1, testEmail1, testGivenName1, testSurname1, testAddress1, testPhone1, testAdult1, testAbilities1);

        final Long testId2 = 42L;
        final String testEmail2 = "c@d.com";
        final String testGivenName2 = "Gustav";
        final String testSurname2 = "Gans";
        final Address testAddress2 = new Address("12323", "Ganshausen", "Gustavstreet", "3a");
        final String testPhone2 = "56756745";
        final Boolean testAdult2 = true;
        final List<Ability> testAbilities2 = Arrays.asList(new Ability(5L, null, null, null, true, true));

        Volunteer volunteer2 = new Volunteer(testId2, testEmail2, testGivenName2, testSurname2, testAddress2, testPhone2, testAdult2, testAbilities2);

        assertNotEquals(volunteer1, volunteer2);

        volunteer1.update(volunteer2);
        assertEquals(testId1, volunteer1.getId());
        assertEquals(testId2, volunteer2.getId());

        volunteer2.setId(testId1);
        assertEquals(volunteer2, volunteer1);
    }

}
