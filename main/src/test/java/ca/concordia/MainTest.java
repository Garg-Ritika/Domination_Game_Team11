package ca.concordia;

import ca.concordia.model.BorderTest;
import ca.concordia.model.ContinentTest;
import ca.concordia.model.CountryTest;
import ca.concordia.model.OrderTest;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        BorderTest.class,
        ContinentTest.class,
        CountryTest.class,
        OrderTest.class
})
public class MainTest extends TestCase {

    public MainTest(String testname) {

        super(testname);
    }

    public static Test suite() {

        return new TestSuite(MainTest.class);

    }

    public void testApp() {

        assertTrue(true);
    }

}