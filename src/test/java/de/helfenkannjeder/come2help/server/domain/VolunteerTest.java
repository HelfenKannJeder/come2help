package de.helfenkannjeder.come2help.server.domain;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class VolunteerTest {

    @Mock
    private User user1;

    @Test
    public void testUpdate() {
        final Long testId1 = 2L;
        final List<Ability> testAbilities1 = Collections.singletonList(new Ability(1L, null, null, null, true, true));

        Volunteer volunteer1 = new Volunteer().setId(testId1).setAbilities(testAbilities1).setUser(user1);

        final Long testId2 = 42L;
        final List<Ability> testAbilities2 = Collections.singletonList(new Ability(5L, null, null, null, true, true));

        Volunteer volunteer2 = new Volunteer().setId(testId2).setAbilities(testAbilities2).setUser(user1);

        assertNotEquals(volunteer1, volunteer2);

        volunteer1.update(volunteer2);

        assertEquals(testId1, volunteer1.getId());
        assertEquals(testId2, volunteer2.getId());

        volunteer2.setId(testId1);
        assertEquals(volunteer2, volunteer1);

        verify(user1).update(user1);
    }
}
