package healthSumAI.TestCases;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import healthSumAI.baseTest.BaseTest;
import pageObject.PatientsPage;

public class Patients extends BaseTest{
	@Test(dependsOnGroups="landing.open")

	public void VerifyPatient() {
	PatientsPage pp= new PatientsPage(driver);
	pp.selectPatientByName("John Doe");
	 String tsOnly =pp.printAlertsAndTimestamp();
	// 1) Assert timestamp is present
	  //String updatedAt = pp.getSummaryUpdatedAt();
	 Assert.assertFalse(tsOnly.isEmpty(), "Updated timestamp should not be empty.");

	  // 2) Critical Alerts: assert only if available
	 List<WebElement> alerts = pp.getCriticalAlertsOrEmpty();
	  if (!alerts.isEmpty()) {
	    // At least one alert and all have non-empty text
	    Assert.assertTrue(alerts.size() > 0, "Expected at least one Critical Alert when section is present.");
	    Assert.assertTrue(
	        alerts.stream()
	              .map(el -> {
	                String t = el.getText();
	                return t == null ? "" : t.trim();
	              })
	              .allMatch(t -> !t.isEmpty()),
	        "All Critical Alerts should have non-empty text."
	    );
	  }

}
}