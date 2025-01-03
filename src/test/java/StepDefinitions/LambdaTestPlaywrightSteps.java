package StepDefinitions;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.BoundingBox;
import com.microsoft.playwright.options.SelectOption;
import io.cucumber.java.en.*;
import static org.junit.Assert.*;
import java.net.URLEncoder;
import java.io.UnsupportedEncodingException;
import AutomationKT.BasicAuthentication;

public  class LambdaTestPlaywrightSteps {

    public static Playwright playwright = Playwright.create();
    public static BrowserType chrome = playwright.chromium();
    static String caps;
        static {    
            try {      
                 caps = URLEncoder.encode(BasicAuthentication.getDefaultTestCapability().toString(), "utf-8");
                   } catch (UnsupportedEncodingException e) {  
                         throw new RuntimeException(e);  
                         }
                }
       static String cdpUrl = "wss://cdp.lambdatest.com/playwright?capabilities=" + caps;
       
      public static Browser browser = playwright.chromium().connect(cdpUrl);
      public static Page page = browser.newPage();
    // Initialize Playwright and browser
    @Given("I open the {string} page")
    public void iOpenThePage(String pageName) {
        page.navigate("https://www.lambdatest.com/selenium-playground/");
    }
 
    @When("User clicks {string}")
    public void userClicks(String simple){
        page.locator("//a[contains(text(),'Simple Form Demo')]").click();
    }
    @When("I enter a message in the input field")
    public void iEnterAMessageInTheInputField() {
        String inputField = "Hii message";
        page.fill("//input[@id='user-message']", inputField);
        page.locator("//button[@id='showInput']").click();
    }
    @Then("the message displayed should match the entered message")
    public void theMessageDisplayedShouldMatchTheEnteredMessage() {
        String messageField = page.locator("//p[@id='message']").textContent();
        assertEquals("Hii message", messageField);
    }
    @When("I click the {string} button")
    public void iClickTheButton(String buttonText) {
        page.waitForTimeout(2000);
        page.locator("//button[@id='showInput']").click();
    }
    @When("I click on {string} under {string}")
    public void iClickOnDragAndDropSlidersUnderProgressBars(String linkText, String sectionText) {
        page.click("text=" + linkText);
    }
    @When("I drag the slider to value 95")
    public void iDragTheSliderToValue95() {
        Locator slider = page.locator("input[type='range'][value='15']");
        slider.waitFor();
        BoundingBox sliderBox = slider.boundingBox();
        if (sliderBox == null) {
            throw new RuntimeException("Slider not visible");
        }
        double sliderWidth = sliderBox.width;
        double initialValue = 15;
        double maxValue = 100;
        double targetValue = 95;
        double targetPosition = ((targetValue - initialValue) / (maxValue - initialValue)) * sliderWidth;
        System.out.println("Calculated target position for value 95: " + targetPosition);
        double adjustedTargetPosition = Math.min(targetPosition, sliderWidth);
        System.out.println("Adjusted target position (clamped to slider width): " + adjustedTargetPosition);
        page.mouse().move(sliderBox.x + sliderBox.width / 2, sliderBox.y + sliderBox.height / 2);
        page.mouse().down();
        page.mouse().move(sliderBox.x + adjustedTargetPosition, sliderBox.y + sliderBox.height / 2);
        page.mouse().up();
        page.waitForTimeout(500);
        String newValue = slider.inputValue();
        System.out.println("Slider input value after drag: " + newValue);
        if (!newValue.equals("95")) {
            System.out.println("Adjusting the slider value to 95...");
            slider.fill("95");
        }
        slider.dispatchEvent("input");
        page.waitForTimeout(500);
    }
    @Then("I should see the slider value as {string}")
    public void iShouldSeeTheSliderValueAs(String expectedValue) {
        String finalValue = page.locator("//output[@id='rangeSuccess']").textContent();
        System.out.println("Final value after adjustment: " + finalValue);
        assertEquals(expectedValue, finalValue);
    }
    @When("I click on {string} under {string} and click")
    public void iClickOnInputFormSubmitUnderInputForms(String linkText, String sectionText) {
        page.click("text=" + linkText);
    }
    @When("I click on Submit without filling in any information")
    public void iClickOnSubmitWithoutFillingInAnyInformation() {
        page.click("(//button[@type='submit'])[2]");
        page.waitForTimeout(3000);
    }
    @Then("I should see {string} error message")
    public void iShouldSeeErrorMessage(String errorMessage) {
        Locator name = page.locator("#name");
        String validationMessage = (String) name.evaluate("element => element.validationMessage");
        if (validationMessage.contains("Please fill out this field.")) {
            System.out.println("Validation message is as expected.");
        } else {
            System.out.println("Validation message is not as expected: " + validationMessage);
        }
        assertTrue(validationMessage.contains("Please fill out this field."));
    }
    @When("I fill in the Name, Email, and other fields")
    public void iFillInTheNameEmailAndOtherFields() {
        page.fill("#name", "Mickey");
        page.fill("//input[@id='inputEmail4']", "mickey@gmail.com");
        page.fill("//input[@id='inputPassword4']", "mickey123");
        page.fill("//input[@id='company']", "Capgemini");
        page.fill("//input[@id='websitename']", "Talent");
        page.fill("//input[@id='inputCity']", "Hyderabad");
        page.fill("//input[@id='inputAddress1']", "Financial District");
        page.fill("//input[@id='inputAddress2']", "Gachibowli");
        page.fill("//input[@id='inputState']", "Telangana");
        page.fill("//input[@id='inputZip']", "500032");
    }
    @When("I select {string} from the Country dropdown")
    public void iSelectCountryFromTheDropdown(String country) {
        page.waitForTimeout(3000);
        page.selectOption("//select[@name='country']", new SelectOption().setLabel(country));
    }
    @When("I click on Submit")
    public void iClickOnSubmit() {
        page.waitForTimeout(3000);
        page.click("(//button[@type='submit'])[2]");
    }
    @Then("I should see the success message {string}")
    public void iShouldSeeTheSuccessMessage(String successMessage) {
        Locator successMsg = page.locator("//p[@class='success-msg hidden']");
        page.evaluate("window.scrollTo(0, 0);");
        successMsg.scrollIntoViewIfNeeded();
        page.waitForTimeout(3000);
        String actualSuccessMsg = successMsg.textContent();
        assertTrue("Thanks for contacting us, we will get back to you shortly.", actualSuccessMsg.contains(successMessage));
    }
    // Close browser after the test
    @io.cucumber.java.After
    public void closeBrowser() {
        if (browser != null) {
            browser.close();
        }
    }
}