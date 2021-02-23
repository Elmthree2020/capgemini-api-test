Feature: Get networks for City

  As a user I want to verify that the city Frankfurt is in Germany and that we can return
  the city’s corresponding latitude and longitude.

  Scenario: City is Frankfurt
    Given I make a get request to '/v2/networks'
    Then the status code is 200
    And network for city "Frankfurt" should have "DE" as "country"
    And network for city "Frankfurt" should have 8.66375 as "longitude"
    And network for city "Frankfurt" should have 50.1072 as "latitude"