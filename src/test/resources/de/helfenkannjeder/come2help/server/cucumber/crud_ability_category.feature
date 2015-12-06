Feature: Crud operations for an abilitycategory

  Scenario: Create an abilitycategory
    Given any valid abilitycategory
    When the abilitycategory is created
    Then the response contains the created abilitycategory

  Scenario: Reject create of an abilitycategory with invalid data
    Given any invalid abilitycategory
    When the abilitycategory create request is send
    Then the returned status code for the abilitycategory is HTTP 400 Bad Request

  Scenario: Create an abilitycategory with existing id
    Given an existing abilitycategory
    When the abilitycategory id is re-used
    Then the returned status code for the abilitycategory is HTTP 400 Bad Request

  Scenario: Update an existing abilitycategory
    Given an existing abilitycategory
    And the user changes a property of the abilitycategory
    When the abilitycategory is updated
    Then the updated abilitycategory is returned

  Scenario: Update a non existing abilitycategory
    Given a non existing abilitycategory
    When the abilitycategory is updated
    Then the returned status code for the abilitycategory is HTTP 404 Not Found

  Scenario: Reject update of a abilitycategory with invalid data
    Given an existing abilitycategory
    And the user modifies the abilitycategory with invalid data
    When the abilitycategory is updated
    Then the returned status code for the abilitycategory is HTTP 400 Bad Request

  Scenario: Delete a non existing abilitycategory
    Given a non existing abilitycategory
    When the abilitycategory is deleted
    Then the returned status code for the abilitycategory is HTTP 404 Not Found

  Scenario: Delete an existing abilitycategory
    Given an existing abilitycategory
    When the abilitycategory is deleted
    Then the returned status code for the abilitycategory is HTTP 204 No Content