package healthSumAI.TestCases;

import org.testng.annotations.Test;

import healthSumAI.baseTest.BaseTest;
import pageObject.AiSummarizePage;

public class AiSummarize extends BaseTest{
	@Test(dependsOnGroups="landing.open")
	public void summarize() {
		AiSummarizePage AiSum= new AiSummarizePage(driver);
		AiSum.clickAISummarizer();
		AiSum.clickPasteNoteSum();
		AiSum.clickPatient();
	}
	}

