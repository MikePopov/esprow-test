package login;

import static util.DataProviderUtil.processCsv;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import data.login.LoginData;
import page.login.LoginPage;
import page.product.ProductsPage;
import util.BaseTest;

import java.lang.reflect.Method;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {
  private LoginPage loginPage;
  private static final String FILE_PATH = "login/login.csv";

  @Override
  public void initialize() {
    loginPage = createInstance(LoginPage.class);
  }

  @AfterMethod
  public void captureScreenshot(ITestResult result) {
    ITestNGMethod method = result.getMethod();

    if (ITestResult.FAILURE == result.getStatus()) {
      loginPage.captureScreenshot(method.getMethodName());
    }
  }

  @DataProvider(name = "loginData")
  public static Object[][] getLoginData(final Method testMethod) {
    String testCaseId = testMethod.getAnnotation(Test.class).testName();

    return processCsv(LoginData.class, FILE_PATH, testCaseId);
  }

  @Test(testName = "TC-1", dataProvider = "loginData")
  public void testCorrectUserNameAndCorrectPassword(final LoginData loginDto) {
    loginPage
        .goTo()
        .enterUsername(loginDto.getUserName())
        .enterPassword(loginDto.getPassword())
        .clickLogin();

    ProductsPage productsPage = createInstance(ProductsPage.class);

    assertThat(productsPage.getTitle()).isEqualTo("PRODUCTS");
  }

  @Test(testName = "TC-2", dataProvider = "loginData")
  public void testIncorrectUserNameAndCorrectPassword(final LoginData loginDto) {
    loginPage
        .goTo()
        .enterUsername(loginDto.getUserName())
        .enterPassword(loginDto.getPassword())
        .clickLogin();

    assertThat(loginPage.getErrorMessage()).isEqualTo(loginDto.getErrorMessage());
  }

  @Test(testName = "TC-3", dataProvider = "loginData")
  public void testCorrectUserNameAndIncorrectPassword(final LoginData loginDto) {
    loginPage
        .goTo()
        .enterUsername(loginDto.getUserName())
        .enterPassword(loginDto.getPassword())
        .clickLogin();

    assertThat(loginPage.getErrorMessage()).isEqualTo(loginDto.getErrorMessage());
  }

  @Test(testName = "TC-4", dataProvider = "loginData")
  public void testIncorrectUserNameAndIncorrectPassword(final LoginData loginDto) {
    loginPage
        .goTo()
        .enterUsername(loginDto.getUserName())
        .enterPassword(loginDto.getPassword())
        .clickLogin();

    assertThat(loginPage.getErrorMessage()).isEqualTo(loginDto.getErrorMessage());
  }

  @Test(testName = "TC-5", dataProvider = "loginData")
  public void testBlankUserName(final LoginData loginDto) {
    loginPage.goTo().enterPassword(loginDto.getPassword()).clickLogin();

    assertThat(loginPage.getErrorMessage()).isEqualTo(loginDto.getErrorMessage());
  }

  @Test(testName = "TC-6", dataProvider = "loginData")
  public void testBlankPassword(final LoginData loginDto) {
    loginPage.goTo().enterUsername(loginDto.getUserName()).clickLogin();

    assertThat(loginPage.getErrorMessage()).isEqualTo(loginDto.getErrorMessage());
  }

  @Test(testName = "TC-7", dataProvider = "loginData")
  public void testLockedOutUser(final LoginData loginDto) {
    loginPage
        .goTo()
        .enterUsername(loginDto.getUserName())
        .enterPassword(loginDto.getPassword())
        .clickLogin();

    assertThat(loginPage.getErrorMessage()).isEqualTo(loginDto.getErrorMessage());
  }
}
