package de.helfenkannjeder.come2help.server.matchers;

import de.helfenkannjeder.come2help.server.domain.Ability;
import de.helfenkannjeder.come2help.server.rest.dto.AbilityDto;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import static org.hamcrest.Matchers.equalTo;

public class AbilityDtoMatcher extends TypeSafeDiagnosingMatcher<AbilityDto> {

    private Matcher<? super Long> id = Matchers.anything();

    private Matcher<? super String> name = Matchers.anything();

    private Matcher<? super String> description = Matchers.anything();

    private Matcher<? super AbilityDto> parentAbility = Matchers.anything();

    private Matcher<? super Boolean> isSelectable = Matchers.anything();

    private Matcher<? super Boolean> isCategory = Matchers.anything();

    public static AbilityDtoMatcher matchesAbility() {
        return new AbilityDtoMatcher();
    }

    public static AbilityDtoMatcher matchesAbility(AbilityDto abilityDto) {
        return new AbilityDtoMatcher().withId(abilityDto.getId())
                .withName(abilityDto.getName())
                .withDescription(abilityDto.getDescription())
                .withParentAbility(abilityDto.getParentAbility())
                .withIsSelectable(abilityDto.isSelectable())
                .withIsCategory(abilityDto.isCategory());
    }

    public AbilityDtoMatcher withId(Long id) {
        this.id = equalTo(id);
        return this;
    }

    public AbilityDtoMatcher withName(String name) {
        this.name = equalTo(name);
        return this;
    }

    public AbilityDtoMatcher withDescription(String description) {
        this.description = equalTo(description);
        return this;
    }

    public AbilityDtoMatcher withParentAbility(AbilityDto parentAbility) {
        this.parentAbility = equalTo(parentAbility);
        return this;
    }

    public AbilityDtoMatcher withIsSelectable(boolean isSelectable) {
        this.isSelectable = equalTo(isSelectable);
        return this;
    }


    public AbilityDtoMatcher withIsCategory(boolean isCategory) {
        this.isCategory = equalTo(isCategory);
        return this;
    }

    @Override
    protected boolean matchesSafely(AbilityDto item, final Description mismatchDescription) {
        boolean matches = true;
        mismatchDescription.appendText("was AbilityDto");

        if (!id.matches(item.getId())) {
            mismatchDescription.appendText(" with id=").appendValue(item.getId());
            matches = false;
        }
        if (!name.matches(item.getName())) {
            mismatchDescription.appendText(" with name=").appendValue(item.getName());
            matches = false;
        }
        if (!description.matches(item.getDescription())) {
            mismatchDescription.appendText(" with description=").appendValue(item.getDescription());
            matches = false;
        }
        return matches;
    }

    @Override
    public void describeTo(Description descriptionHam) {
        descriptionHam
                .appendText(", with id=").appendDescriptionOf(id)
                .appendText(", with name=").appendDescriptionOf(name)
                .appendText(", with description=").appendDescriptionOf(description)
                .appendText(", with parentAbility=").appendDescriptionOf(parentAbility)
                .appendText(", with isSelectable=").appendDescriptionOf(isSelectable)
                .appendText(", with isCategory=").appendDescriptionOf(isCategory);
    }
}
