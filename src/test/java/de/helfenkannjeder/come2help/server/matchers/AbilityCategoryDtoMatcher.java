package de.helfenkannjeder.come2help.server.matchers;

import de.helfenkannjeder.come2help.server.domain.AbilityCategory;
import de.helfenkannjeder.come2help.server.rest.dto.AbilityCategoryDto;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import static org.hamcrest.Matchers.equalTo;

public class AbilityCategoryDtoMatcher extends TypeSafeDiagnosingMatcher<AbilityCategoryDto> {

    private Matcher<? super Long> id = Matchers.anything();

    private Matcher<? super String> name = Matchers.anything();

    private Matcher<? super AbilityCategory> parentAbilityCategory = Matchers.anything();

    public static AbilityCategoryDtoMatcher matchesAbilityCategory() {
        return new AbilityCategoryDtoMatcher();
    }

    public static AbilityCategoryDtoMatcher matchesAbilityCategory(AbilityCategoryDto abilityCategoryDto) {
        return new AbilityCategoryDtoMatcher().withId(abilityCategoryDto.getId())
                .withName(abilityCategoryDto.getName())
                .withParentAbilityCategory(abilityCategoryDto.getParentAbilityCategory());
    }

    public AbilityCategoryDtoMatcher withId(Long id) {
        this.id = equalTo(id);
        return this;
    }

    public AbilityCategoryDtoMatcher withName(String name) {
        this.name = equalTo(name);
        return this;
    }

    public AbilityCategoryDtoMatcher withParentAbilityCategory(AbilityCategory parentAbilityCategory) {
        this.parentAbilityCategory = equalTo(parentAbilityCategory);
        return this;
    }

    @Override
    protected boolean matchesSafely(AbilityCategoryDto item, final Description mismatchDescription) {
        boolean matches = true;
        mismatchDescription.appendText("was AbilityCategoryDto");

        if (!id.matches(item.getId())) {
            mismatchDescription.appendText(" with id=").appendValue(item.getId());
            matches = false;
        }
        if (!name.matches(item.getName())) {
            mismatchDescription.appendText(" with name=").appendValue(item.getName());
            matches = false;
        }
        if (!parentAbilityCategory.matches(item.getParentAbilityCategory())) {
            mismatchDescription.appendText(" with parentAbilityCategory=").appendValue(item.getParentAbilityCategory());
            matches = false;
        }
        return matches;
    }

    @Override
    public void describeTo(Description description) {
        description
                .appendText(", with id=").appendDescriptionOf(id)
                .appendText(", with name=").appendDescriptionOf(name)
                .appendText(", with parentAbilityCategory=").appendDescriptionOf(parentAbilityCategory);
    }
}
