import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Phonebook {
	public static ArrayList<Contact> list = new ArrayList<Contact>();
	final static String NL = System.getProperty("line.separator");

	public static void main(String[] args) {
		String num, first, last;
		String[] contact;

		Scanner input;
		try {
			input = new Scanner(new File("phonebook.txt"));
			while (input.hasNextLine()) {
				contact = input.nextLine().split(" ");
				num = contact[0];
				last = contact[1].substring(0, contact[1].length() - 1);
				first = contact[2];
				Contact person = new Contact(num, last, first);
				list.add(person);
			}
			input.close();
		} catch (Exception error) {
			System.out.println("Error: " + error.getMessage());
		}
		Time();
	}

	public static void Search(String word) {
		try {
			FileWriter output = new FileWriter("Output.txt");
			for (Contact i : list) {
				if (i.getLast().toLowerCase().contains(word.toLowerCase())
						|| i.getFirst().toLowerCase().contains(word.toLowerCase())) {
					output.write(i.getNumber() + " " + i.getLast() + ", " + i.getFirst() + NL);
				}
			}
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<Contact> Selection(ArrayList<Contact> arr) {
		for (int i = 0; i < arr.size() - 1; ++i) {
			int minIndex = i;
			for (int j = i + 1; j < arr.size(); ++j) {
				if (arr.get(j).getLast().compareTo(arr.get(minIndex).getLast()) < 0) {
					minIndex = j;
				}
			}
			Contact temp = arr.get(i);
			arr.set(i, arr.get(minIndex));
			arr.set(minIndex, temp);
		}
		return arr;
	}

	public static ArrayList<Contact> mergeSort(ArrayList<Contact> arr) {
		if (arr.size() == 1) {
			return arr;
		} else {
			int mid = arr.size() / 2;
			ArrayList<Contact> l = new ArrayList<Contact>(mid);
			ArrayList<Contact> r = new ArrayList<Contact>(arr.size() - mid);
			for (int i = 0; i < mid; ++i)
				l.add(arr.get(i));
			for (int i = mid; i < arr.size(); ++i)
				r.add(arr.get(i));
			l = mergeSort(l);
			r = mergeSort(r);
			Merge(l, r, arr);
		}
		return arr;
	}

	public static void Merge(ArrayList<Contact> l, ArrayList<Contact> r, ArrayList<Contact> arr) {
		int lIndex = 0;
		int rIndex = 0;
		for (int i = 0; i < arr.size(); ++i) {
			if (lIndex == l.size()) {

				arr.set(i, r.get(rIndex));
				++rIndex;
			} else {
				if (rIndex == r.size()) {
					arr.set(i, l.get(lIndex));
					++lIndex;
				} else {
					if (l.get(lIndex).getLast().compareTo(r.get(rIndex).getLast()) <= 0) {
						arr.set(i, l.get(lIndex));
						++lIndex;
					} else {
						arr.set(i, r.get(rIndex));
						++rIndex;
					}
				}
			}
		}
	}

	public static boolean Check(ArrayList<Contact> arr) {
		for (int i = 1; i < arr.size(); i++) {
			if (arr.get(i - 1).getLast().compareTo(arr.get(i).getLast()) > 0)
				return false;
		}
		return true;
	}

	public static void Time() {
		long s1 = System.nanoTime();
		ArrayList<Contact> slist = Selection(list);
		double e1 = (System.nanoTime() - s1) / 10000000.0;
		long s2 = System.nanoTime();
		ArrayList<Contact> mlist = mergeSort(list);
		double e2 = (System.nanoTime() - s2) / 10000000.0;

		System.out.println("Selection Time: " + e1);
		System.out.println("Merge Time: " + e2);
	}
}
