@Ingredients
Feature: Find ingredients by id
  Users want to get details about one ingredient

  Scenario: Retrieve a ingredient without fails
    Given this are the ingredients currently in the database:
      | id                                   | name      | value |
      | aca1f386-5d11-4fb5-9dc2-2009e6085931 | hamburger | 3.0   |
      | aca2f386-5d11-4fb5-9dc2-2009e6082222 | cheese    | 2.5   |
      | aca3f386-5d11-4fb5-9dc2-2009e6083333 | lettuce   | 0.8   |
      | aca3f386-5d11-4fb5-9dc2-2009e6084444 | bacon     | 2.0   |
    When this user request ingredient: "aca1f386-5d11-4fb5-9dc2-2009e6085931"
    And the database is called to find ingredient id: "aca1f386-5d11-4fb5-9dc2-2009e6085931"
    Then the system will return 200 status code
    And the service will reply this ingredient: "{\"id\":\"aca1f386-5d11-4fb5-9dc2-2009e6085931\",\"nome\":\"hamburger\",\"valor\":3.0}"

  Scenario: Retrieve a ingredient that not exists
    Given this are the ingredients currently in the database:
      | id                                   | name      | value |
      | aca1f386-5d11-4fb5-9dc2-2009e6085931 | hamburger | 3.0   |
      | aca2f386-5d11-4fb5-9dc2-2009e6082222 | cheese    | 2.5   |
      | aca3f386-5d11-4fb5-9dc2-2009e6083333 | lettuce   | 0.8   |
      | aca3f386-5d11-4fb5-9dc2-2009e6084444 | bacon     | 2.0   |
    When this user request ingredient: "aca3f386-5d11-4fb5-9dc2-2009e608000"
    Then the system will return 404 status code
    And the database is called to find ingredient id: "aca3f386-5d11-4fb5-9dc2-2009e608000"

  Scenario: Try to retrieve a ingredient with database offline
    Given the database is offline
    When this user request ingredient: "aca1f386-5d11-4fb5-9dc2-2009e6085931"
    Then the system will return 500 status code