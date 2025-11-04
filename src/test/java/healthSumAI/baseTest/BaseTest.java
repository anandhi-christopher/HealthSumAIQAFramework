package healthSumAI.baseTest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class BaseTest {
  protected static WebDriver driver;

  @BeforeSuite(alwaysRun = true)
  public void initializeDriver() {
	    if (driver == null) {

    driver = new ChromeDriver(); // Selenium Manager resolves chromedriver
    
    driver.manage().window().maximize();
  }
  }
  
//  @AfterSuite(alwaysRun = true)
//  public void tearDownDriver() {
//    if (driver != null) {
//      driver.quit();
//      driver=null;
//    }
//  }
//  @AfterSuite
//  public void openHtmlReport() {
//	  //allure
//	  try {
//		  //serve the allure report
//		  ProcessBuilder builder = new ProcessBuilder("C://Users//anand//Downloads//allure-2.35.1//allure-2.35.1//bin//allure","serve","allure-results");
//		  builder.inheritIO();
//		  Process process =builder.start();
//		  process.waitFor();
//		  //The 'allure serve' command automatically opens the report in a browser.
//		  System.out.println("Allure report served successfully");
//	  }catch(IOException | InterruptedException e) {
//		  e.printStackTrace();
//		  System.out.println("Failed to serve Allure report");
//	  }
	  
  

    @AfterSuite(alwaysRun = true)
    public void openHtmlReportServe() {
      try {
        // Resolve results dir: -Dallure.results.directory wins; else try target/allure-results, then allure-results
        File resultsDir = resolveResultsDir();
        if (resultsDir == null) {
          System.err.println("[Allure] No results found in: target/allure-results, allure-results. CWD=" + new File(".").getAbsolutePath());
          return;
        }

        // Resolve allure executable: -Dallure.exe="C:\\...\\allure.bat" (Windows) or just "allure" if on PATH
        String allure = System.getProperty("allure.exe", "allure");

        boolean isWindows = System.getProperty("os.name").toLowerCase().contains("win");
        ProcessBuilder pb = isWindows
                // IMPORTANT: use start "" "path\to\allure.bat" serve "path\to\results"
                ? new ProcessBuilder("cmd", "/c", "start", "", quoteIfNeeded(allure), "serve", resultsDir.getAbsolutePath())
                : new ProcessBuilder(allure, "serve", resultsDir.getAbsolutePath());

        pb.inheritIO();
        Process p = pb.start();
        if (!isWindows) {
          // On non-Windows, serve blocks; Windows detached with 'start'
          p.waitFor();
        }
        System.out.println("[Allure] Serve started and Report generated succussfully");
      } catch (Exception e) {
        System.err.println("[Allure] Failed to serve report: " + e);
      }
    }

    private static File resolveResultsDir() {
      String prop = System.getProperty("allure.results.directory");
      if (prop != null && !prop.isBlank()) {
        File f = new File(prop);
        if (f.exists() && hasFiles(f)) return f;
      }
      Path target = Paths.get("target", "allure-results");
      if (target.toFile().exists() && hasFiles(target.toFile())) return target.toFile();

      Path root = Paths.get("allure-results");
      if (root.toFile().exists() && hasFiles(root.toFile())) return root.toFile();

      return null;
    }

    private static boolean hasFiles(File dir) {
      String[] list = dir.list();
      return list != null && list.length > 0;
    }

    // For Windows 'start', when allure path has spaces; if it's already bare "allure", just return it
    private static String quoteIfNeeded(String cmd) {
      if (cmd.contains(" ") && !(cmd.startsWith("\"") && cmd.endsWith("\""))) {
        return "\"" + cmd + "\"";
      }
      return cmd;
    }
  

	  
  
}
