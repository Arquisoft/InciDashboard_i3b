Feature: landing page 
  Scenario: client makes call to GET /
    When the client calls /
    Then the client receives status code of 302
    And the client receives the string ""