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
	private File dict;
	private ArrayList<String> dict_content;
	private File target;
	private ArrayList<String> target_content;
	private boolean sorted;

	public Checker(String dict_path, String target_path) {
		this.dict = new File(dict_path);
		this.dict_content = null;
		this.target = new File(target_path);
		this.target_content = null;
		this.sorted = false;
	}

	private ArrayList<String> scan(File f, String separator) {
		ArrayList<String> arr = new ArrayList<String>();

		try {
			FileReader f_reader = new FileReader(f);
			BufferedReader reader = new BufferedReader(f_reader);
			String line = null;

			while ((line = reader.readLine()) != null)
				if (separator != "\n")
					for (String elem : line.split(separator)) arr.add(elem.replaceAll("[^a-zA-Z]", ""));
				else arr.add(line);

      reader.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		return arr;
	}

	private ArrayList<String> cloneArrayList(ArrayList<String> arr) {
		ArrayList<String> cloned_arr = new ArrayList<String>(arr.size());
		for (String elem : arr) cloned_arr.add(new String(elem));
		return cloned_arr;
	}

	public ArrayList<String> getDictionary() {
		return this.cloneArrayList(this.dict_content);
	}

	public ArrayList<String> getTarget() {
		return this.cloneArrayList(this.target_content);
	}

	public void sort() {
		if (this.dict_content == null) this.dict_content = this.scan(this.dict, "\n");
		Collections.sort(this.dict_content, new LengthComparator());
		this.sorted = true;
	}

	public ArrayList<ArrayList<String>> check(boolean printing_enabled) {
		if (this.dict_content == null) this.dict_content = this.scan(this.dict, "\n");
		this.target_content = this.scan(this.target, " ");

		ArrayList<ArrayList<String>> r = new ArrayList<ArrayList<String>>();
		ArrayList<String> ed_arr = new ArrayList<String>();
		int min = Integer.MAX_VALUE;
		boolean skip_iter = false;

		for (int i = 0; i < this.target_content.size(); i++) {
			String target_str = this.target_content.get(i);
			ed_arr = new ArrayList<String>();
			min = Integer.MAX_VALUE;
			skip_iter = false;

			if (printing_enabled) System.out.println("[*] Checking " + target_str + " ...");

			for (int j = 0; j < this.dict_content.size() && !skip_iter; j++) {
				String dict_str = this.dict_content.get(j);

				if (Math.abs(target_str.length() - dict_str.length()) <= min) {
					int ed = EditDistance.editDistanceDyn(target_str.toLowerCase(), dict_str.toLowerCase());
					if (ed == 0) {
						min = 0;
						ed_arr = new ArrayList<String>();
						ed_arr.add(dict_str);
						skip_iter = true;
					} else if (ed == min) {
            ed_arr.add(dict_str);
					} else if (ed < min) {
						ed_arr = new ArrayList<String>();
						ed_arr.add(dict_str);
						min = ed;
					}
				} else if (this.sorted) {
          skip_iter = true;
        }
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
