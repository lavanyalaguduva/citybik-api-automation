Feature: Get company details from citybik network
  As an user I should be able to get all/specific company details that are part of citybik network

  Background: Configure the service base uri
    Given citybik service is available to get its network details

  Scenario: Get all company details
    When I request the service to give all the company details
    Then I should get details of 645 companies

  Scenario: Get a specific company detail
    When I request the service to give the company details with id "visa-frankfurt"
    Then I should get the following details
      | network.location.country   | DE      |
      | network.location.latitude  | 50.1072 |
      | network.location.longitude | 8.66375 |
    And the response should confirm the defined schema
 
