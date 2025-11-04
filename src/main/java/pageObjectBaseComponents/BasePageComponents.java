package pageObjectBaseComponents;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePageComponents {
  protected WebDriver driver;
  protected WebDriverWait wait;

  // Optional no-arg constructor 
  protected BasePageComponents() { }

  // Pass the driver in; initialize PageFactory and Wait once.
  public BasePageComponents(WebDriver driver) {
    this.driver = driver;
    PageFactory.initElements(driver, this);
    this.wait = new WebDriverWait(driver, Duration.ofSeconds(7));
  }

  protected void waitForElementToAppear(WebElement contact) {
    wait.until(ExpectedConditions.visibilityOf(contact));
  }
  
  protected void waitForListOfElementToAppear(List<WebElement> patientRows) {
	    wait.until(ExpectedConditions.visibilityOf((WebElement) patientRows));
	  }
  
  protected void waitForElementSeeADemoButton(WebDriver driver,WebElement seeADemoBtn,long seconds) {
	 	 		  wait.until(ExpectedConditions.elementToBeClickable(seeADemoBtn));
		  try {
			  seeADemoBtn.click();
		  } catch (ElementClickInterceptedException e) {
		    ((JavascriptExecutor)driver).executeScript("arguments[0].click();", seeADemoBtn);
		  
		}
  wait.until(ExpectedConditions.or(
	        ExpectedConditions.urlContains("vercel.app"),
	        ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(.,'Demo') or contains(.,'Request')]"))
	    ));
}
  private Alert waitForAlert(WebDriver driver, long seconds) {
	    return new WebDriverWait(driver, Duration.ofSeconds(seconds))
	            .until(ExpectedConditions.alertIsPresent());
	}

	// Accept an alert
	public void acceptAlert(WebDriver driver, long timeoutSec) {
	    Alert a = waitForAlert(driver, timeoutSec);
	    a.accept();
	}
	// --- ADDITIONS: generic wait helpers (safe to reuse across all pages) ---

	/** Default explicit-wait timeout (seconds) for helper methods below. */
	protected long defaultTimeoutSec = 10;

	/** Optionally override default wait timeout at runtime. */
	public void setDefaultTimeoutSec(long seconds) { this.defaultTimeoutSec = Math.max(1, seconds); }

	/** Wait for element to be visible (By). */
	protected WebElement waitVisible(By by) {
	  return new WebDriverWait(driver, Duration.ofSeconds(defaultTimeoutSec))
	      .until(ExpectedConditions.visibilityOfElementLocated(by));
	}

	/** Wait for element to be visible (WebElement). */
	protected WebElement waitVisible(WebElement el) {
	  return new WebDriverWait(driver, Duration.ofSeconds(defaultTimeoutSec))
	      .until(ExpectedConditions.visibilityOf(el));
	}

	/** Wait for all elements to be visible (By). */
	protected List<WebElement> waitAllVisible(By by) {
	  return new WebDriverWait(driver, Duration.ofSeconds(defaultTimeoutSec))
	      .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
	}

	/** Wait for all elements to be visible (List<WebElement>). */
	protected List<WebElement> waitAllVisible(List<WebElement> els) {
	  return new WebDriverWait(driver, Duration.ofSeconds(defaultTimeoutSec))
	      .until(ExpectedConditions.visibilityOfAllElements(els));
	}

	/** Wait for element to be clickable (By). */
	protected WebElement waitClickable(By by) {
	  return new WebDriverWait(driver, Duration.ofSeconds(defaultTimeoutSec))
	      .until(ExpectedConditions.elementToBeClickable(by));
	}

	/** Wait for element to be clickable (WebElement). */
	protected WebElement waitClickable(WebElement el) {
	  return new WebDriverWait(driver, Duration.ofSeconds(defaultTimeoutSec))
	      .until(ExpectedConditions.elementToBeClickable(el));
	}

	/** Wait for URL to contain a substring. */
	protected boolean waitUrlContains(String fragment) {
	  return new WebDriverWait(driver, Duration.ofSeconds(defaultTimeoutSec))
	      .until(ExpectedConditions.urlContains(fragment));
	}

	/** Scroll element into view (center). */
	protected void scrollIntoView(WebElement el) {
	  if (el == null) return;
	  ((JavascriptExecutor) driver)
	      .executeScript("arguments[0].scrollIntoView({block:'center', inline:'nearest'});", el);
	}

	/** JS click fallback. */
	protected void jsClick(WebElement el) {
	  if (el == null) return;
	  ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
	}

	/** Normalize whitespace. */
	protected String normalize(String s) {
	  if (s == null) return "";
	  return s.replaceAll("\\s+", " ").trim();
	}

	/** Safe getText + normalize. */
	protected String textOf(WebElement el) {
	  if (el == null) return "";
	  try { return normalize(el.getText()); } catch (Exception e) { return ""; }
	}

	/** Quote string for XPath literal, supporting single quotes. */
	protected String xPathLiteral(String s) {
	  if (s == null) return "''";
	  if (!s.contains("'")) return "'" + s + "'";
	  return "concat('" + s.replace("'", "',\"'\",'") + "')";
	}

	/** Build CI contains() predicate for XPath. */
	protected String containsCI(String nodeExpr, String needleLower) {
	  return "contains(translate(normalize-space(" + nodeExpr + "), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'), "
	      + xPathLiteral(needleLower == null ? "" : needleLower) + ")";
	}

}
