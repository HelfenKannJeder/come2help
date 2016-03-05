package de.helfenkannjeder.come2help.server.matchers;

import de.helfenkannjeder.come2help.server.rest.dto.AddressDto;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import static org.hamcrest.Matchers.equalTo;

public class AddressDtoMatcher extends TypeSafeDiagnosingMatcher<AddressDto> {

    private Matcher<? super String> zipCode = Matchers.anything();

    private Matcher<? super String> city = Matchers.anything();

    private Matcher<? super String> street = Matchers.anything();

    private Matcher<? super String> streetNumber = Matchers.anything();

    public static AddressDtoMatcher matchesAddressDto() {
        return new AddressDtoMatcher();
    }

    public static AddressDtoMatcher matchesAddressDto(AddressDto addressDto) {
        return new AddressDtoMatcher().withZipCode(addressDto.getZipCode())
                .withCity(addressDto.getCity())
                .withStreet(addressDto.getStreet())
                .withStreetNumber(addressDto.getStreetNumber());
    }

    public AddressDtoMatcher withZipCode(String zipCode) {
        this.zipCode = equalTo(zipCode);
        return this;
    }

    public AddressDtoMatcher withCity(String city) {
        this.city = equalTo(city);
        return this;
    }

    public AddressDtoMatcher withStreet(String street) {
        this.street = equalTo(street);
        return this;
    }

    public AddressDtoMatcher withStreetNumber(String streetNumber) {
        this.streetNumber = equalTo(streetNumber);
        return this;
    }

    @Override
    protected boolean matchesSafely(AddressDto item, final Description mismatchDescription) {
        boolean matches = true;
        mismatchDescription.appendText("was AddressDto");

        if (!zipCode.matches(item.getZipCode())) {
            mismatchDescription.appendText(" with zipCode=").appendValue(item.getZipCode());
            matches = false;
        }
        if (!city.matches(item.getCity())) {
            mismatchDescription.appendText(" with city=").appendValue(item.getCity());
            matches = false;
        }
        if (!street.matches(item.getStreet())) {
            mismatchDescription.appendText(" with street=").appendValue(item.getStreet());
            matches = false;
        }
        if (!streetNumber.matches(item.getStreetNumber())) {
            mismatchDescription.appendText(" with streetNumber=").appendValue(item.getStreetNumber());
            matches = false;
        }
        return matches;
    }

    @Override
    public void describeTo(Description description) {
        description
                .appendText(", with zipCode=").appendDescriptionOf(zipCode)
                .appendText(", with city=").appendDescriptionOf(city)
                .appendText(", with street=").appendDescriptionOf(street)
                .appendText(", with streetNumber=").appendDescriptionOf(streetNumber);
    }
}
