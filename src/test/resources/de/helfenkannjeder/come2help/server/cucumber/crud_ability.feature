Feature: Crud operations for an ability

  Scenario: Create an ability
    Given any valid ability
    When the ability is created
    Then the response contains the created ability

  Scenario: Reject create of an ability with invalid data
    Given any invalid ability
    When the ability create request is send
    Then the returned status code for the ability is HTTP 400 Bad Request

  Scenario: Create an ability with existing id
    Given an existing ability
    When the ability id is re-used
    Then the returned status code for the ability is HTTP 400 Bad Request

  Scenario: Update an existing ability
    Given an existing ability
    And the user changes a property of the ability
    When the ability is updated
    Then the updated ability is returned

  Scenario: Update a non existing ability
    Given a non existing ability
    When the ability is updated
    Then the returned status code for the ability is HTTP 404 Not Found

  Scenario: Reject update of a ability with invalid data
    Given an existing ability
    And the user modifies the ability with invalid data
    When the ability is updated
    Then the returned status code for the ability is HTTP 400 Bad Request

  Scenario: Delete a non existing ability
    Given a non existing ability
    When the ability is deleted
    Then the returned status code for the ability is HTTP 404 Not Found

  Scenario: Delete an existing ability
    Given an existing ability
    When the ability is deleted
    Then the returned status code for the ability is HTTP 204 No Content