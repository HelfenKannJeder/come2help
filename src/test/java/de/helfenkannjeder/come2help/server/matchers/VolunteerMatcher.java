package de.helfenkannjeder.come2help.server.matchers;

import de.helfenkannjeder.come2help.server.domain.User;
import de.helfenkannjeder.come2help.server.domain.Volunteer;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import static org.hamcrest.Matchers.equalTo;

public class VolunteerMatcher extends TypeSafeDiagnosingMatcher<Volunteer> {

    private Matcher<? super Long> id = Matchers.anything();

    private Matcher<? super User> user = Matchers.anything();

    //TODO: match abilities

    public static VolunteerMatcher matchesVolunteer() {
        return new VolunteerMatcher();
    }

    public static VolunteerMatcher matchesVolunteer(Volunteer volunteer) {
        return new VolunteerMatcher().withId(volunteer.getId())
                .withUser(volunteer.getUser());
    }

    public VolunteerMatcher withId(Long id) {
        this.id = equalTo(id);
        return this;
    }

    public VolunteerMatcher withUser(User user) {
        this.user = UserMatcher.matchesUser(user);
        return this;
    }

    @Override
    protected boolean matchesSafely(Volunteer item, final Description mismatchDescription) {
        boolean matches = true;
        mismatchDescription.appendText("was Volunteer");

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
