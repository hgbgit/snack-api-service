@Ingredients
Feature: List ingredients
  Users want to list possible ingredients to compose a snack

  Scenario: Retrieve a list of ingredients without fails
    Given this are the ingredients currently in the database:
      | id                                   | name      | value |
      | aca1f386-5d11-4fb5-9dc2-2009e6085931 | hamburger | 3.0   |
      | aca2f386-5d11-4fb5-9dc2-2009e6082222 | cheese    | 2.5   |
      | aca3f386-5d11-4fb5-9dc2-2009e6083333 | lettuce   | 0.8   |
    When this user requests ingredients
    Then the system will return 200 status code
    And the database will be called to list ingredients
    And the service will reply this list of ingredients: "[{\"id\":\"aca1f386-5d11-4fb5-9dc2-2009e6085931\",\"nome\":\"hamburger\",\"valor\":3.0},{\"id\":\"aca2f386-5d11-4fb5-9dc2-2009e6082222\",\"nome\":\"cheese\",\"valor\":2.5},{\"id\":\"aca3f386-5d11-4fb5-9dc2-2009e6083333\",\"nome\":\"lettuce\",\"valor\":0.8}]"

  Scenario: Try to retrieve a list of ingredients with database offline
    Given the database is offline
    When this user requests ingredients
    Then the system will return 500 status code