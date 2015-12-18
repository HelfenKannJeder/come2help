package de.helfenkannjeder.come2help.server.matchers;

import de.helfenkannjeder.come2help.server.domain.Address;
import de.helfenkannjeder.come2help.server.domain.User;
import de.helfenkannjeder.come2help.server.domain.Volunteer;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import static org.hamcrest.Matchers.equalTo;

public class UserMatcher extends TypeSafeDiagnosingMatcher<User> {

    private Matcher<? super String> id = Matchers.anything();

    private Matcher<? super String> authProvider = Matchers.anything();

    private Matcher<? super String> externalId = Matchers.anything();

    private Matcher<? super String> email = Matchers.anything();

    private Matcher<? super String> givenName = Matchers.anything();

    private Matcher<? super String> surname = Matchers.anything();

    private Matcher<? super String> phone = Matchers.anything();

    private Matcher<? super Address> address = Matchers.anything();

    private Matcher<? super Volunteer> volunteer = Matchers.anything();

    public static UserMatcher matchesUser() {
        return new UserMatcher();
    }

    public static UserMatcher matchesUser(User user) {
        return new UserMatcher().withEmail(user.getEmail())
                .withGivenName(user.getGivenName())
                .withSurname(user.getSurname())
                .withAddress(user.getAddress())
                .withPhone(user.getPhone());
    }

    public UserMatcher withEmail(String email) {
        this.email = equalTo(email);
        return this;
    }

    public UserMatcher withGivenName(String givenName) {
        this.givenName = equalTo(givenName);
        return this;
    }

    public UserMatcher withSurname(String surname) {
        this.surname = equalTo(surname);
        return this;
    }

    public UserMatcher withAddress(Address address) {
        this.address = AddressMatcher.matchesAddress(address);
        return this;
    }

    public UserMatcher withPhone(String phone) {
        this.phone = equalTo(phone);
        return this;
    }

    @Override
    protected boolean matchesSafely(User item, final Description mismatchDescription) {
        boolean matches = true;
        mismatchDescription.appendText("was UserDto");

        if (!email.matches(item.getEmail())) {
            mismatchDescription.appendText(" with email=").appendValue(item.getEmail());
            matches = false;
        }
        if (!givenName.matches(item.getGivenName())) {
            mismatchDescription.appendText(" with givenName=").appendValue(item.getGivenName());
            matches = false;
        }
        if (!surname.matches(item.getSurname())) {
            mismatchDescription.appendText(" with surname=").appendValue(item.getSurname());
            matches = false;
        }
        if (!address.matches(item.getAddress())) {
            mismatchDescription.appendText(" with address=").appendValue(item.getAddress());
            matches = false;
        }
        if (!phone.matches(item.getPhone())) {
            mismatchDescription.appendText(" with phone=").appendValue(item.getPhone());
            matches = false;
        }
        return matches;
    }

    @Override
    public void describeTo(Description description) {
        description
                .appendText(", with email=").appendDescriptionOf(email)
                .appendText(", with givenName=").appendDescriptionOf(givenName)
                .appendText(", with surname=").appendDescriptionOf(surname)
                .appendText(", with address=").appendDescriptionOf(address)
                .appendText(", with phone=").appendDescriptionOf(phone);
    }
}
