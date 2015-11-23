Feature: Crud operations for an volunteer

  Scenario: Create a volunteer
    Given any valid volunteer
    When the volunteer is created
    Then the response contains the created volunteer

  Scenario: Reject create of a volunteer with invalid data
    Given any invalid volunteer
    When the volunteer create request is send
    Then the returned status code is HTTP 400 Bad Request

  Scenario: Create a volunteer with existing id
    Given an existing volunteer
    When the volunteer id is re-used
    Then the returned status code is HTTP 400 Bad Request

  Scenario: Update an existing volunteer
    Given an existing volunteer
    And the user changes a property of the volunteer
    When the volunteer is updated
    Then the updated volunteer is returned

  Scenario: Update a non existing volunteer
    Given a non existing volunteer
    When the volunteer is updated
    Then the returned status code is HTTP 404 Not Found

  Scenario: Reject update of a volunteer with invalid data
    Given an existing volunteer
    And the user modifies the volunteer with invalid data
    When the volunteer is updated
    Then the returned status code is HTTP 400 Bad Request

  Scenario: Delete a non existing volunteer
    Given a non existing volunteer
    When the volunteer is deleted
    Then the returned status code is HTTP 404 Not Found