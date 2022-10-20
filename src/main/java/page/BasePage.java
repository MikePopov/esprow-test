package page;

import com.assertthat.selenium_shutterbug.core.Shutterbug;

import static config.ConfigurationManager.configuration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

/**
 * This class defines the basic functionalities of a POM class.
 *
 */
public class BasePage {
  private WebDriver driver;

  public void initialize(final WebDriver webdriver) {
    this.driver = webdriver;

    PageFactory.initElements(driver, this);
  }

  public WebDriver getDriver() {
    return driver;
  }

  public void captureScreenshot(String fileName) {
    Shutterbug.shootPage(driver)
        .withName(fileName)
        .save(configuration().baseReportPath() + configuration().baseScreenshotPath());
  }
}
