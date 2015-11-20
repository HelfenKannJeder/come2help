package de.helfenkannjeder.come2help.server.matchers;

import de.helfenkannjeder.come2help.server.domain.Address;
import de.helfenkannjeder.come2help.server.rest.dto.AddressDto;
import de.helfenkannjeder.come2help.server.rest.dto.VolunteerDto;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.equalTo;

public class VolunteerDtoMatcher extends TypeSafeDiagnosingMatcher<VolunteerDto> {

    private Matcher<? super Long> id = Matchers.anything();

    private Matcher<? super String> email = Matchers.anything();

    private Matcher<? super String> givenName = Matchers.anything();

    private Matcher<? super String> surname = Matchers.anything();

    private Matcher<? super Address> address = Matchers.anything();

    private Matcher<? super String> phone = Matchers.anything();

    private Matcher<? super Boolean> adult = Matchers.anything();

    public static VolunteerDtoMatcher matchesVolunteer() {
        return new VolunteerDtoMatcher();
    }

    public static VolunteerDtoMatcher matchesVolunteer(VolunteerDto volunteerDto) {
        return new VolunteerDtoMatcher().withId(volunteerDto.getId())
                .withEmail(volunteerDto.getEmail())
                .withGivenName(volunteerDto.getGivenName())
                .withSurname(volunteerDto.getSurname())
                .withAddress(volunteerDto.getAddress())
                .withPhone(volunteerDto.getPhone())
                .withAdult(volunteerDto.isAdult());
    }

    public VolunteerDtoMatcher withId(Long id) {
        this.id = equalTo(id);
        return this;
    }

    public VolunteerDtoMatcher withEmail(String email) {
        this.email = equalTo(email);
        return this;
    }

    public VolunteerDtoMatcher withGivenName(String givenName) {
        this.givenName = equalTo(givenName);
        return this;
    }

    public VolunteerDtoMatcher withSurname(String surname) {
        this.surname = equalTo(surname);
        return this;
    }

    public VolunteerDtoMatcher withAddress(AddressDto address) {
        this.address = is(address);
        return this;
    }

    public VolunteerDtoMatcher withPhone(String phone) {
        this.phone = equalTo(phone);
        return this;
    }

    public VolunteerDtoMatcher withAdult(Boolean isAdult) {
        this.adult = equalTo(isAdult);
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
        if (!adult.matches(item.isAdult())) {
            mismatchDescription.appendText(" with adult=").appendValue(item.isAdult());
            matches = false;
        }
        return matches;
    }

    @Override
    public void describeTo(Description description) {
        description
                .appendText(", with id=").appendDescriptionOf(id)
                .appendText(", with email=").appendDescriptionOf(email)
                .appendText(", with givenName=").appendDescriptionOf(givenName)
                .appendText(", with surname=").appendDescriptionOf(surname)
                .appendText(", with address=").appendDescriptionOf(address)
                .appendText(", with phone=").appendDescriptionOf(phone)
                .appendText(", with adult=").appendDescriptionOf(adult);
    }
}
