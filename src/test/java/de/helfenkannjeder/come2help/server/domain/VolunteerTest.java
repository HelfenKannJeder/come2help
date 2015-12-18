package de.helfenkannjeder.come2help.server.domain;

import java.util.Collections;
import java.util.List;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class VolunteerTest {

    @Test
    public void testUpdate() {
        final Long testId1 = 2L;
        final List<Ability> testAbilities1 = Collections.singletonList(new Ability(1L, null, null));

        Volunteer volunteer1 = new Volunteer().setId(testId1).setAbilities(testAbilities1);

        final Long testId2 = 42L;
        final List<Ability> testAbilities2 = Collections.singletonList(new Ability(5L, null, null));

        Volunteer volunteer2 = new Volunteer().setId(testId2).setAbilities(testAbilities2);

        assertNotEquals(volunteer1, volunteer2);

        volunteer1.update(volunteer2);

        assertEquals(testId1, volunteer1.getId());
        assertEquals(testId2, volunteer2.getId());

        volunteer2.setId(testId1);
        assertEquals(volunteer2, volunteer1);
    }
}
