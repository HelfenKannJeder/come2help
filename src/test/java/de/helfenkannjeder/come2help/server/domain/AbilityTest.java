package de.helfenkannjeder.come2help.server.domain;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class AbilityTest {

    @Test
    public void testUpdate() {
        final Long testId1 = 2L;
        final String testName1 = "Test Name 1";
        final String testDescription1 = "Test Description 1";
        final AbilityCategory abilityCategory1 = new AbilityCategory(1L, "parent", null);
        Ability ability1 = new Ability(testId1, testName1, testDescription1, abilityCategory1);
        final long testId2 = 40L;
        final String testName2 = "Test Name 2";
        final String testDescription2 = "Test Description 2";
        final AbilityCategory abilityCategory2 = new AbilityCategory(2L, "parent2", null);
        Ability ability2 = new Ability(testId2, testName2, testDescription2, abilityCategory2);

        assertAbility(ability1, testId1, testName1, testDescription1);
        assertAbility(ability2, testId2, testName2, testDescription2);

        ability1.update(ability2);
        assertAbility(ability1, testId1, testName2, testDescription2);
        assertAbility(ability2, testId2, testName2, testDescription2);
    }

    private static void assertAbility(Ability ability, final Long id, final String name, final String description) {
        assertEquals(id, ability.getId());
        assertEquals(name, ability.getName());
        assertEquals(description, ability.getDescription());
    }
}
