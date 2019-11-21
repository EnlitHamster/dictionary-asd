package exercise2;

import java.util.Comparator;

public class LengthComparator implements Comparator<String> {
	/**
	 * Length-based comparison between strings
	 *
	 * @param str1 -> first string
	 * @param str2 -> second string
	 *
	 * @return -1 if the first string is shorter, 1 if longer, 0 if equal
	 */
	public int compare(String str1, String str2) {
		if (str1.length() < str2.length()) return -1;
		else if (str1.length() > str2.length())	return 1;
		else return 0;
	}
}
