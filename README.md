# HealthSumAIQAFramework
QA Automation Framework — AI Summary Validation & Critical Alert Verification
Overview
This project automates end-to-end checks for:
-->AI-generated summaries — content completeness.
-->Critical alerts — presence.
It’s built with Java, Maven, TestNG, and Allure Report. Code lives in the main branch, and CI runs via Jenkins.

What it does:
When medical record is Pasted it generates medical summary and
Verifies critical alerts (e.g., Severe Hypertension Risk, cardiac arrest) are raised for qualifying cases and not raised when they shouldn’t be.
Produces HTML test reports of test execution summary.

Tech stack
Language: Java (21.0.8)
Build: Maven
Test runner: TestNG
Reports: Allure (HTML)
IDE: Eclipse
CI: Jenkins (pipeline)
Source control: Git (main branch)
