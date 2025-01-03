Feature: Lambda Test Playwright
  @simpleFormDemo
  Scenario: User interacts with the Simple Form Demo page
    Given I open the "lambda Test" page
    When User clicks "Simple Form Demo"
    When I enter a message in the input field
    And I click the "Show Message" button
    Then the message displayed should match the entered message
  @dragSlider95
  Scenario: Drag the slider to the value 95 using fine adjustments
    Given I open the "lambda Test" page
    When I click on "Drag & Drop Sliders" under "Progress Bars & Sliders"
    And I drag the slider to value 95
    Then I should see the slider value as "95"
  @submitForm
  Scenario: Submit the form with missing and valid information
    Given I open the "lambda Test" page
    When I click on "Input Form Submit" under "Input Forms" and click
    And I click on Submit without filling in any information
    Then I should see "Please fill in the fields" error message
    When I fill in the Name, Email, and other fields
    And I select "United States" from the Country dropdown
    And I click on Submit
    Then I should see the success message "Thanks for contacting us, we will get back to you shortly."

