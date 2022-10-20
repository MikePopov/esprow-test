package driver;

import static config.ConfigurationManager.configuration;

import org.openqa.selenium.WebDriver;

/**
 * This class provides a singleton instance of WebDriver.
 *
 */
public final class DriverManager {
  private static WebDriver driver = null;

  private DriverManager() {
  }

  /**
   * @return an instance of browser driver implementation
   */
  public static WebDriver createDriver() {
    if (driver == null) {
      driver = BrowserFactory.valueOf(configuration().browser().toUpperCase()).initializeDriver();
    }

    return driver;
  }
}
