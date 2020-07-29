package framework;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.ExtentReportTest;
import com.pages.Loginpage;

public class Testcases extends ExtentReportTest {

	Loginpage login = new Loginpage();

	@Parameters("browser")
	@Test
	public void Testcase1(String browser) {
		login.doLogin(browser);
	}
}
