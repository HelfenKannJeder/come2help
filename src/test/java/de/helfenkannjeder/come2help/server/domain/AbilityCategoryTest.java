package de.helfenkannjeder.come2help.server.domain;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AbilityCategoryTest {

    @Test
    public void testUpdate() {

        final Long testIdParent = 33L;
        final String testNameParent = "Parent Name";
        AbilityCategory parentAbilityCategory1 = new AbilityCategory(testIdParent, testNameParent, null);

        final Long testId1 = 2L;
        final String testName1 = "Test Name 1";
        AbilityCategory abilityCategory1 = new AbilityCategory(testId1, testName1, parentAbilityCategory1);

        final Long testIdParent2 = 233L;
        final String testNameParent2 = "Parent Name 2";
        AbilityCategory parentAbilityCategory2 = new AbilityCategory(testIdParent2, testNameParent2, null);

        final long testId2 = 40L;
        final String testName2 = "Test Name 2";
        AbilityCategory abilityCategory2 = new AbilityCategory(testId2, testName2, parentAbilityCategory2);

        assertAbilityCategory(abilityCategory1, testId1, testName1, parentAbilityCategory1);
        assertAbilityCategory(abilityCategory2, testId2, testName2, parentAbilityCategory2);

        abilityCategory1.update(abilityCategory2);
        assertAbilityCategory(abilityCategory1, testId1, testName2, parentAbilityCategory2);
        assertAbilityCategory(abilityCategory2, testId2, testName2, parentAbilityCategory2);
    }

    private static void assertAbilityCategory(AbilityCategory abilityCategory, final Long id, final String name, final AbilityCategory parentAbilityCategory) {
        assertEquals(id, abilityCategory.getId());
        assertEquals(name, abilityCategory.getName());
        assertEquals(parentAbilityCategory, abilityCategory.getParentAbilityCategory());
    }
}
