# Automation Framework

## Overview
This framework is designed to automate API and UI tests using Java and TestNG.
It provides a simple and flexible way to execute all tests or specific test groups using Maven.

## Technologies
- Java
- TestNG
- Selenium WebDriver (UI)
- Rest Assured (API)
- Maven
- Extent Reports

## Test Groups
Tests are organized using TestNG groups:
- api → API tests
- ui → UI tests

Example:
@Test(groups = "api")
@Test(groups = "ui")

## Reports
After execution, the Extent Report is generated at:
reports/TestExecutionReport.html

The report includes test status, execution logs, API request/response logs, and error details.

## Logging
Use the Report utility to add logs to the report:
Report.step("Test step description");
Report.pass("Validation passed");
Report.fail("Validation failed");

## Notes
- Always use TestNG groups (api, ui) to organize tests.
- Run tests using Maven to ensure listeners and reports work correctly.
- The framework is scalable and ready to support future test expansion.
