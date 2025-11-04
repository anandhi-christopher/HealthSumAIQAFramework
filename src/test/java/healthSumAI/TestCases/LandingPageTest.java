package healthSumAI.TestCases;

import org.testng.annotations.Test;
import healthSumAI.baseTest.BaseTest;
import pageObject.LandingPage;

public class LandingPageTest extends BaseTest {

  @Test(groups="landing.open")
  public void verifyLandingPage() throws InterruptedException {
    // Navigate first (safer when pages do heavy lazy init)
    driver.get("https://ritaelia.github.io/healthSumAI");

    LandingPage lp = new LandingPage(driver);
    lp.clickContact();
    lp.clickSeeADemo();
   
   
  }
}
