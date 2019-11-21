package tests;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class EditDistance_TestRunner {
	public static void main(String [] args) {
		if (args.length == 0) {
			System.out.print("Recursive: ");
			run(JUnitCore.runClasses(EditDistance_Test.class));
			System.out.print("Dynamic: ");
			run(JUnitCore.runClasses(EditDistanceDyn_Test.class));
		} else if (args[0].equalsIgnoreCase("rec")) run(JUnitCore.runClasses(EditDistance_Test.class));
		else if (args[0].equalsIgnoreCase("dyn")) run(JUnitCore.runClasses(EditDistanceDyn_Test.class));
    }

	public static void run(Result result) {
		for (Failure failure : result.getFailures())
			System.out.println(failure.toString());

		System.out.println(result.wasSuccessful());
	}
}
