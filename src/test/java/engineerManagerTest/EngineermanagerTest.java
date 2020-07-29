package engineerManagerTest;

import org.testng.annotations.Test;
import com.ExtentReportTest;
import com.utility.DbOperation;


public class EngineermanagerTest extends ExtentReportTest {

	DbOperation dbo = new DbOperation();

	@Test(priority = 0)
	public void updateRoletoEngineerManagerTest() {
		System.out.println("Test Engineer manager");
	}

}
