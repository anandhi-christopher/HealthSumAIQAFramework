package pageObject;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import pageObjectBaseComponents.BasePageComponents;

public class PatientsPage extends BasePageComponents {

	public PatientsPage(WebDriver driver) 
	{ super(driver);
	}

	String needle;

	@FindBy(css="input[placeholder*='search' i][placeholder*='name' i]")
	WebElement searchField;

	@FindBy(xpath="//tbody/tr")
	private List<WebElement> patientRows;

	public void selectPatientByName(String name) {
		if (name == null || name.trim().isEmpty()) {
			throw new IllegalArgumentException("Name cannot be null/empty");
		}
		String needle = name.trim().toLowerCase();

		// 1) Type query (clear first)
		waitClickable(searchField).clear();
		searchField.sendKeys(name);

		// 2) Wait for table to (re)render
		waitAllVisible(patientRows);

		// 3) Find the row that contains the name (case-insensitive)
		String ci = containsCI(".", needle);
		By rowBy = By.xpath("//tbody/tr[" + ci + "]");
		WebElement row = waitVisible(rowBy);

		// 4) Prefer a clickable inside the row; else click the row itself
		List<WebElement> clickables = row.findElements(By.xpath(".//a[" + ci + "] | .//button[" + ci + "]"));
		WebElement target = clickables.isEmpty() ? row : clickables.get(0);

		scrollIntoView(target);
		waitClickable(target).click();
	}

	//Timestamp in "Latest AI Summary" card
	public String printAlertsAndTimestamp() {

		By UPDATED_AT = By.xpath(
				"//div[contains(@class,'shadow') and .//h3[normalize-space()='Latest AI Summary']]" +
						"//span[contains(@class,'text-xs') and contains(normalize-space(.), 'Updated:')]"
				);
		WebElement tsEl = waitVisible(UPDATED_AT);
		String tsOnly = textOf(tsEl).replaceFirst("(?i)^Updated:\\s*", "");
		System.out.println("Updated At: " + tsOnly);
		return tsOnly;

	}

	// Critical Alerts (under the heading)
	public List<WebElement> getCriticalAlertsOrEmpty() {
		By ALERTS_HEADING = By.xpath("//h3[contains(normalize-space(.), 'Critical Alert')]");
		waitVisible(ALERTS_HEADING);

		By ALERTS = By.xpath("//h3[normalize-space(.)='Critical Alerts']" +
				"/following-sibling::*[1]//span[contains(concat(' ', normalize-space(@class), ' '), ' font-medium ')]"
				);

List<WebElement> alerts = driver.findElements(ALERTS);
		if (alerts.isEmpty()) {
			System.out.println("Critical Alerts: <none>");
		} else {
			System.out.println("Critical Alerts:");
			for (WebElement a : alerts) {
				System.out.println(" - " + textOf(a));
			}
		}    
		return alerts;
		
	}
}
