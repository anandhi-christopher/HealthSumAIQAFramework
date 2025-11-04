package pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import pageObjectBaseComponents.BasePageComponents;

public class LandingPage extends BasePageComponents {

  public LandingPage(WebDriver driver) {
    super(driver);
  }
  @FindBy(css = "nav[aria-label='Main'] a[href*='contact'], nav[aria-label='Main'] a[href='#contact']")
private WebElement contact;
  @FindBy(css="div[class='container cta'] div div a[class='butn btn-outl']")
	WebElement seeADemoBtn;
 

  public void clickContact() throws InterruptedException {
    waitForElementToAppear(contact);
    contact.click();
    
  }
  public void clickSeeADemo() {
  waitForElementSeeADemoButton(driver,seeADemoBtn,7);
// seeADemoBtn.click();
}}
