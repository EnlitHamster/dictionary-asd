package exercise2;

import java.util.Collections;
import java.util.ArrayList;
import java.util.Map;
import java.util.LinkedHashMap;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class Checker {
	// File pointing to the dictionary
	private File dict;
	// List of the dicionary content
	private ArrayList<String> dict_content;
	// File to check
	private File target;
	// List of the target content
	private ArrayList<String> target_content;
	// Check for the dictionary sorting
	private boolean sorted;

	/**
	 * Creates an instance of the Checker class, initializing the files
	 * used by it and preparing the other variables for the execution
	 *
	 * @param dict_path -> Path to the dictionary
	 * @param target_path -> Path to the file to check
	 */
	public Checker(String dict_path, String target_path) {
		this.dict = new File(dict_path);
		this.dict_content = null;
		this.target = new File(target_path);
		this.target_content = null;
		this.sorted = false;
	}

	/**
	 * Generates a list containing the content of a given file. The
	 * endline is always considered a separtor
	 *
	 * @param f -> File to scan
	 * @param separator -> sequence of characters separating the information
	 * to be extracted
	 *
	 * @return a List containing the file's content
	 */
	private ArrayList<String> scan(File f, String separator) {
		ArrayList<String> arr = new ArrayList<String>();

		try {
			FileReader f_reader = new FileReader(f);
			BufferedReader reader = new BufferedReader(f_reader);
			String line = null;

			while ((line = reader.readLine()) != null)
				if (separator != "\n")
					// If the separator isn't the endline, we have to check if the line
					// contains a number of contents.
					for (String elem : line.split(separator)) arr.add(elem.replaceAll("[^a-zA-Z]", ""));
				else arr.add(line);

      reader.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		return arr;
	}

	/**
	 * Creates a copy of a given ArrayList of strings
	 *
	 * @param arr -> List to be copied
	 *
	 * @return a copy of the list
	 */
	private ArrayList<String> cloneArrayList(ArrayList<String> arr) {
		ArrayList<String> cloned_arr = new ArrayList<String>(arr.size());
		for (String elem : arr) cloned_arr.add(new String(elem));
		return cloned_arr;
	}

	/** GETTERS **/

	public ArrayList<String> getDictionary() {
		return this.cloneArrayList(this.dict_content);
	}

	public ArrayList<String> getTarget() {
		return this.cloneArrayList(this.target_content);
	}

	/**
	 * Sorts the content of the dictionary to improve check's execution. An
	 * ordered list of content allows to use euristics on when to stop cycling
	 * on a given word in the target file. The sorting is length-based
	 */
	public void sort() {
		if (this.dict_content == null) this.dict_content = this.scan(this.dict, "\n");
		Collections.sort(this.dict_content, new LengthComparator());
		this.sorted = true;
	}

	/**
	 * Checks the target file jusing the given dictionary for errors. It uses
	 * the following euristic:
	 * "If the dictionary is sorted, being the difference in length between two
	 * strings the base edit distance, whenever we find a said difference greater
	 * then the minimum edit distance found, we can stop checking the word for
	 * any word in the dictionary subsequent to this will always have a greater
	 * edit distance"
	 *
	 * @param printing_enabled -> Allow printing while running the method
	 *
	 * @return a List of words that have the minimum edit distance compared to
	 * the corresponding word in the target file
	 */
	public ArrayList<ArrayList<String>> check(boolean printing_enabled) {
		// Aquiring the content of the files
		if (this.dict_content == null) this.dict_content = this.scan(this.dict, "\n");
		this.target_content = this.scan(this.target, " ");

		// List of List instance containing the words with minimum edit distance
		ArrayList<ArrayList<String>> r = new ArrayList<ArrayList<String>>();

		for (int i = 0; i < this.target_content.size(); i++) {
			String target_str = this.target_content.get(i);
			ArrayList<String> ed_arr = new ArrayList<String>();
			int min = Integer.MAX_VALUE;
			boolean skip_iter = false;

			if (printing_enabled) System.out.println("[*] Checking " + target_str + " ...");

			for (int j = 0; j < this.dict_content.size() && !skip_iter; j++) {
				String dict_str = this.dict_content.get(j);

				if (Math.abs(target_str.length() - dict_str.length()) <= min) {
					int ed = EditDistance.editDistanceDyn(target_str.toLowerCase(), dict_str.toLowerCase());
					if (ed == 0) {
						// We found the exact word. No more iterations needed
						min = 0;
						ed_arr = new ArrayList<String>();
						ed_arr.add(dict_str);
						skip_iter = true;
					} else if (ed == min) ed_arr.add(dict_str);
					else if (ed < min) {
						// If we find a smaller edit distance, we reset the List of
						// words with minimum edit distance
						ed_arr = new ArrayList<String>();
						ed_arr.add(dict_str);
						min = ed;
					}
				} else if (this.sorted) skip_iter = true;
			}

			r.add(i, ed_arr);

			if (printing_enabled) {
				System.out.println("\tMinimal edit distance: " + min);
				System.out.println("\tWords with minimal edit distance: " + ed_arr.toString());
			}
		}

		return r;
	}
}
