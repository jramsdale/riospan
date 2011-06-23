package org.rioproject.substrates.riospan;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.rioproject.test.RioTestRunner;
import org.rioproject.test.SetTestManager;
import org.rioproject.test.TestManager;

/**
 * Testing the RioSpanService service using the Rio test framework
 */
@RunWith(RioTestRunner.class)
@Ignore
public class ITRioSpanDeployTest extends ITAbstractRioSpanTest {
	@SetTestManager
	static TestManager testManager;
	HotRodService service;

	@Before
	public void setup() throws Exception {
		Assert.assertNotNull(testManager);
		service = (HotRodService) testManager.waitForService(HotRodService.class);
	}

	@Test
	public void test1() {
		testService(service);
	}
}
