package tests;

import java.util.Collections;
import java.util.ArrayList;
import java.util.Map;

import exercise2.Checker;
import exercise2.LengthComparator;

public class Checker_Test {
	public static void usage() {
		System.out.println("Usage:");
		System.out.println("\t$ java Checker_test [dictionary path] [target path]");
	}

	public static void main(String[] args) {
		if (args.length == 2) {
			Checker checker = new Checker(args[0], args[1]);
			checker.sort();
			ArrayList<ArrayList<String>> r = checker.check(false);
			ArrayList<String> target = checker.getTarget();
			int max = Collections.max(target, new LengthComparator()).length() + 1;

			System.out.println();
			System.out.println("[*] Printing array ...");
			for (int i = 0; i < r.size(); i++)
				System.out.println("\t" + target.get(i) + new String(new char[max - target.get(i).length()]).replace("\0", " ") + "==>\t" + r.get(i).toString());
		} else {
			System.err.println("[*] Missing arguments!\n");
			usage();
		}
	}
}
