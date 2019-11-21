package tests;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class UnionFindSet_TestRunner {
	public static void main(String [] args) {
        Result result = JUnitCore.runClasses(UnionFindSet_Test.class);
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }  // for

        System.out.println(result.wasSuccessful());
    }  // main
}
