import java.io.*;
import java.util.*;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *  This is a JUnit test class which verifies you have the correct
 *  output for your BananaCoconut program.
 *  
 *  @author K. Raven Russell
 */
public class BananaCoconutTest {
	/**
	 *  These store the output from the program (instead of allowing
	 *  them to go to stdout and stderr).
	 */
	private static ByteArrayOutputStream localOut, localErr;
	
	/**
	 *  These keep track of stdout and stderr so we can put them back
	 *  when we're done comparing the outputs.
	 */
	private static PrintStream sysOut, sysErr;
	
	/**
	 *  This is the expected error message.
	 */
	private static String errMsg = "One or more numbers required as a command line argument.\nExample Usage: java BananaCoconut [number] [number] [...]";
	
	/**
	 *  This method tracks stdout and stderr for later use.
	 */
	@BeforeClass
	public static void setup() {
		sysOut = System.out;
		sysErr = System.err;
	}

	/**
	 *  Before every test is run, reset the streams to capture stdout/stderr.
	 */
	@Before
	public void setupStreams() {
		localOut = new ByteArrayOutputStream();
		localErr = new ByteArrayOutputStream();
		System.setOut(new PrintStream(localOut));
		System.setErr(new PrintStream(localErr));
	}

	/**
	 *  After every test, restore stdout/stderr.
	 */
	@After
	public void cleanUpStreams() {
		System.setOut(null);
		System.setErr(null);
		System.setOut(sysOut);
		System.setErr(sysErr);
	}
	
	/**
	 *  This is a template for all the tests. It takes the expected output
	 *  for stdout and stderr and the command line arguments to call, then
	 *  compares it to the actual (student generated) output.
	 *  
	 *  @param expectedOut the output we expect to see on stdout
	 *  @param expectedErr the output we expect to see on stderr
	 *  @param mainArgs the string array required by the main program
	 */
	public void runTest(String expectedOut, String expectedErr, String[] mainArgs) {
		//get expected output lines
		String[] expectedOutput = expectedOut.split("\n");
		String[] expectedErrors = expectedErr.split("\n");
		
		//get student output lines, remove any/all windows character returns
		//for consistancy
		BananaCoconut.main(mainArgs);
		String[] studentOutput = localOut.toString().replaceAll("\\r","").split("\n");
		String[] studentErrors = localErr.toString().replaceAll("\\r","").split("\n");
		
		//compare
		assertOutputMatch(expectedOutput, studentOutput);
		assertOutputMatch(expectedErrors, studentErrors);
	}
	
	/**
	 *  This helper method compares each line of the output (for
	 *  multi-line outputs) and gives more helpful feedback then
	 *  just saying they did not match.
	 *  
	 *  @param expected the lines we expect to see
	 *  @param student the lines the student code generated
	 */
	public void assertOutputMatch(String[] expected, String[] student) {
		if(expected == null || student == null || expected.length != student.length) {
			if(student == null || student.length == 0) {
				fail("No student output. Expected: \"" + String.join("\n", expected) + "\"");
			}
			else if(expected.length == 0) {
				fail("No expected output. Got: \"" + String.join("\n", student) + "\"");
			}
			else{
				fail("Extra line or missing line. " + getExpectedGotMsg(String.join("\n", expected), String.join("\n", student)));
			}
		}
		
		for(int i = 0; i < expected.length; i++) {
			//check that the two lines match
			assertEquals("Line " + (i+1) + " incorrect.\n" + getExpectedGotMsg(expected[i], student[i]),
				expected[i],student[i]);
		}
	}
	
	private String getExpectedGotMsg(String expected, String got) {
		return "Expected:\""
				+ expected
				+ "\"\nGot:     \""
				+ got
				+ "\"  ";
	}
	
	/**
	 *  Tests for the output for the numbers 1-5.
	 */
	@Test(timeout = 2000)
	public void test1() {
		String expectedOut = "1 2 banana 4 5";
		String expectedErr = "";
		String[] mainArgs = {"1", "2", "3", "4", "5"};
		runTest(expectedOut, expectedErr, mainArgs);
	}
	
	/**
	 *  Tests for the output for the numbers 3, 7, 21, 7, 3.
	 */
	@Test(timeout = 2000)
	public void test2() {
		String expectedOut = "banana coconut banana-coconut coconut banana";
		String expectedErr = "";
		String[] mainArgs = {"3", "7", "21", "7", "3"};
		runTest(expectedOut, expectedErr, mainArgs);
	}
	
	/**
	 *  Tests for the output for the numbers 1-25.
	 */
	@Test(timeout = 2000)
	public void test3() {
		String expectedOut = "1 2 banana 4 5 banana coconut 8 banana 10 11 banana 13 coconut banana 16 17 banana 19 20 banana-coconut 22 23 banana 25";
		String expectedErr = "";
		String[] mainArgs = new String[25];
		for(int i = 0; i < mainArgs.length; i++) {
			mainArgs[i] = "" + (i+1);
		}
		runTest(expectedOut, expectedErr, mainArgs);
	}
	
	/**
	 *  Tests output when low integers are given
	 */
	@Test(timeout = 5000)
	public void test4() {
		String expectedOut = "puttputt banana coconut puttputt";
		String expectedErr = "";
		String[] mainArgs = {"0", "3", "7", "0"};
		runTest(expectedOut, expectedErr, mainArgs);
	}
	
	/**
	 *  Tests for the output when no arguments are given.
	 */
	@Test(timeout = 2000)
	public void test5() {
		String expectedOut = "";
		String expectedErr = errMsg;
		String[] mainArgs = {};
		runTest(expectedOut, expectedErr, mainArgs);
	}
	
	/**
	 *  Tests for the output when an invalid number is given.
	 */
	@Test(timeout = 5000)
	public void test6() {
		String expectedOut = "";
		String expectedErr = errMsg;
		String[] mainArgs = {"apple"};
		runTest(expectedOut, expectedErr, mainArgs);
	}
	
	/**
	 *  Tests for the output when valid and invalid arguments are given.
	 */
	@Test(timeout = 5000)
	public void test7() {
		String expectedOut = "";
		String expectedErr = errMsg;
		String[] mainArgs = {"2","apple"};
		runTest(expectedOut, expectedErr, mainArgs);
	}
	
	/**
	 *  Tests for the output for the numbers 0, 0, 21, 0, 2, 0, 3, 0, 4, 0, 5, 0, 6, 0, 7, 0, 21, 0.
	 */
	@Test(timeout = 5000)
	public void test8() {
		String expectedOut = "puttputt puttputt banana-coconut puttputt 2 puttputt banana puttputt 4 puttputt 5 puttputt banana puttputt coconut puttputt banana-coconut puttputt";
		String expectedErr = "";
		String[] mainArgs = new String[18];
		for(int i = 0; i < 8; i++) {
			mainArgs[i*2] = "" + i;
			mainArgs[(i*2)+1] = "0";
		}
		mainArgs[2] = mainArgs[16] = "21";
		mainArgs[17] = "0";
		
		runTest(expectedOut, expectedErr, mainArgs);
	}
	
	/**
	 *  Main method that runs the tests.
	 *  @param args command line arguments (not used)
	 */
	public static void main(String[] args) {
        org.junit.runner.JUnitCore.main("BananaCoconutTest");
    }
}
