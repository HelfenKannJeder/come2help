Feature: Crud operations for an ability category

  Scenario: Create an ability category
    Given any valid ability category
    When the ability category is created
    Then the response contains the created ability category

  Scenario: Reject create of an ability category with invalid data
    Given any invalid ability category
    When the ability category create request is send
    Then the returned status code for the ability category is HTTP 400 Bad Request

  Scenario: Create an ability category with existing id
    Given an existing ability category
    When the ability category id is re-used
    Then the returned status code for the ability category is HTTP 400 Bad Request

  Scenario: Update an existing ability category
    Given an existing ability category
    And the user changes a property of the ability category
    When the ability category is updated
    Then the updated ability category is returned

  Scenario: Update a non existing ability category
    Given a non existing ability category
    When the ability category is updated
    Then the returned status code for the ability category is HTTP 404 Not Found

  Scenario: Reject update of a ability category with invalid data
    Given an existing ability category
    And the user modifies the ability category with invalid data
    When the ability category is updated
    Then the returned status code for the ability category is HTTP 400 Bad Request

  Scenario: Delete a non existing ability category
    Given a non existing ability category
    When the ability category is deleted
    Then the returned status code for the ability category is HTTP 404 Not Found

  Scenario: Delete an existing ability category
    Given an existing ability category
    When the ability category is deleted
    Then the returned status code for the ability category is HTTP 204 No Content