package pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageObjectBaseComponents.BasePageComponents;

public class AiSummarizePage extends BasePageComponents {
	
	public  AiSummarizePage(WebDriver driver) {
		super(driver);
			}
@FindBy(xpath="//a[normalize-space()='AI Summarize']")
WebElement aiSummarizebtn;
@FindBy(css="textarea[placeholder='Paste clinical notes here...']")
WebElement PasteNote;
@FindBy(xpath="//button[normalize-space()='Summarize']")
WebElement summarize;
@FindBy(xpath="//button[normalize-space()='Update']")
WebElement update;
@FindBy(xpath="//a[normalize-space()='Patients']")
WebElement patientBtn;

public void clickAISummarizer() {
	aiSummarizebtn.click();
}
public void clickPasteNoteSum() {
	waitForElementToAppear(summarize);
	PasteNote.click();
	PasteNote.sendKeys("Patient reports intermittent chest pain for 2 days.ECG ordered; monitor for cardiac changes.");
	summarize.click();
	update.click();
	acceptAlert(driver, 5);
	
}
public void clickPatient() {
	waitForElementToAppear(patientBtn);
	patientBtn.click();
}
}
