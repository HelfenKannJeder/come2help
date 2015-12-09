package de.helfenkannjeder.come2help.server.domain;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AbilityTest {

    @Test
    public void testUpdate() {
        final Long testId1 = 2L;
        final String testName1 = "Test Name 1";
        final String testDescription1 = "Test Description 1";
        final Ability parentAbility1 = new Ability(1L, "parent", "desc", null, true, true);
        final boolean isSelectable1 = true;
        final boolean isCategory1 = false;
        Ability ability1 = new Ability(testId1, testName1, testDescription1, parentAbility1, isSelectable1, isCategory1);
        final long testId2 = 40L;
        final String testName2 = "Test Name 2";
        final String testDescription2 = "Test Description 2";
        final Ability parentAbility2 = new Ability(2L, "parent2", "desc2", null, true, true);
        final boolean isSelectable2 = true;
        final boolean isCategory2 = false;
        Ability ability2 = new Ability(testId2, testName2, testDescription2, parentAbility2, isSelectable2, isCategory2);

        assertAbility(ability1, testId1, testName1, testDescription1, parentAbility1, isSelectable1, isCategory1);
        assertAbility(ability2, testId2, testName2, testDescription2, parentAbility2, isSelectable2, isCategory2);

        ability1.update(ability2);
        assertAbility(ability1, testId1, testName2, testDescription2, parentAbility2, isSelectable2, isCategory2);
        assertAbility(ability2, testId2, testName2, testDescription2, parentAbility2, isSelectable2, isCategory2);
    }

    @Test(expected = RuntimeException.class)
    public void testAddChildrenIfAbilityIsNotACategory() {
        Ability ability = new Ability();
        ability.setCategory(false);
        List<Ability> childrenAbilities = Collections.emptyList();
        childrenAbilities.add(new Ability());
        ability.setChildAbilities(childrenAbilities);
    }

    @Test(expected = RuntimeException.class)
    public void testUnsetCategoryWhenThereAreChildren() {
        Ability ability = new Ability();
        ability.setCategory(true);
        List<Ability> childrenAbilities = Collections.emptyList();
        childrenAbilities.add(new Ability());
        ability.setChildAbilities(childrenAbilities);
        ability.setCategory(false);
    }



    private static void assertAbility(Ability ability, final Long id, final String name, final String description, final Ability parentAbility, final boolean isSelectable, final boolean isCategory) {
        assertEquals(id, ability.getId());
        assertEquals(name, ability.getName());
        assertEquals(description, ability.getDescription());
        assertEquals(parentAbility, ability.getParentAbility());
        assertEquals(isSelectable, ability.isSelectable());
        assertEquals(isCategory, ability.isCategory());
    }
}
