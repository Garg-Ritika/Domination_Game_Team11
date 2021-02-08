import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;


public class MainTest extends TestCase {

    public MainTest(String testname){
        super( testname);
    }

    public static Test suite(){
        return  new TestSuite( MainTest.class);
    }

    public void testApp(){
        assertTrue(true);
    }

}