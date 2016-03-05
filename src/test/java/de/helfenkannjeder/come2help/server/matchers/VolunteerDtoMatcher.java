package de.helfenkannjeder.come2help.server.matchers;

import de.helfenkannjeder.come2help.server.rest.dto.UserDto;
import de.helfenkannjeder.come2help.server.rest.dto.VolunteerDto;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import static org.hamcrest.Matchers.equalTo;

public class VolunteerDtoMatcher extends TypeSafeDiagnosingMatcher<VolunteerDto> {

    private Matcher<? super Long> id = Matchers.anything();

    private Matcher<? super UserDto> user = Matchers.anything();

    //TODO: match abilities

    public static VolunteerDtoMatcher matchesVolunteerDto() {
        return new VolunteerDtoMatcher();
    }

    public static VolunteerDtoMatcher matchesVolunteerDto(VolunteerDto volunteerDto) {
        return new VolunteerDtoMatcher().withId(volunteerDto.getId())
                .withUser(volunteerDto.getUser());
    }

    public VolunteerDtoMatcher withId(Long id) {
        this.id = equalTo(id);
        return this;
    }

    public VolunteerDtoMatcher withUser(UserDto user) {
        this.user = UserDtoMatcher.matchesUser(user);
        return this;
    }

    @Override
    protected boolean matchesSafely(VolunteerDto item, final Description mismatchDescription) {
        boolean matches = true;
        mismatchDescription.appendText("was VolunteerDto");

        if (!id.matches(item.getId())) {
            mismatchDescription.appendText(" with id=").appendValue(item.getId());
            matches = false;
        }
        if (!user.matches(item.getUser())) {
            mismatchDescription.appendText(" with user=").appendValue(item.getUser());
            matches = false;
        }
        return matches;
    }

    @Override
    public void describeTo(Description description) {
        description
                .appendText(", with id=").appendDescriptionOf(id)
                .appendText(", with address=").appendDescriptionOf(user);
    }
}
