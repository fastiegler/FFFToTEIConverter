package newConverter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class converter {

	private static String D_0 = "[";
	private static String D_1 = "]";
	private static String D_2 = "}";
	private static int count = 1;
	private static ArrayList<String> delList;
	private static File configDel;
	private static fileHelper fileHelpConfigDel;
	private static ArrayList<String> repList0;
	private static ArrayList<String> repList1;
	private static ArrayList<String> repList2;
	private static File configRep0;
	private static File configRep1;
	private static File configRep2;
	private static fileHelper fileHelpConfigRep;
	private static fileHelper filehelperIn;
	private static fileHelper filehelperOut;
	private static gui.gui window;
	private static File input = new File(System.getProperty("user.dir") + "\\input");
	private static File output = new File(System.getProperty("user.dir") + "\\xml.out");

	public static void main(String[] args) {
		configDel = new File(System.getProperty("user.dir") + "\\D.cfg");
		configRep0 = new File(System.getProperty("user.dir") + "\\R0.cfg");
		configRep1 = new File(System.getProperty("user.dir") + "\\R1.cfg");
		configRep2 = new File(System.getProperty("user.dir") + "\\R2.cfg");
		reloadConfigs();
		window = new gui.gui();
		openFiles();
	}

	private static void openFiles() {
		try {
			if (filehelperIn != null) {
				filehelperIn.close();
			}
			filehelperIn = new fileHelper(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			if (filehelperOut != null) {
				filehelperOut.close();
			}
			filehelperOut = new fileHelper(output);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void btnTest() {
		reset();
		reloadConfigs();
		replaceEverything();
		System.out.println("Fertig");
	}

	private static void reloadConfigs() {
		delList = new ArrayList<>();
		loadDelConfig();
		repList0 = new ArrayList<>();
		repList1 = new ArrayList<>();
		repList2 = new ArrayList<>();
		loadRepConfig();
	}

	public static void reset() {
		try {
			filehelperIn.resetReader();
			filehelperOut.emtyFile();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void addLine() {
		filehelperIn.appendToFile("\n" + "test");
	}

	private static void loadDelConfig() {
		try {
			if (fileHelpConfigDel != null) {
				fileHelpConfigDel.close();
			}
			fileHelpConfigDel = new fileHelper(configDel);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String s = "";
		while ((s = fileHelpConfigDel.getNextLine()) != null) {
			delList.add(s);
		}
	}

	private static void loadRepConfig() {
		try {
			if (fileHelpConfigRep != null) {
				fileHelpConfigRep.close();
			}
			fileHelpConfigRep = new fileHelper(configRep0);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String s = "";
		while ((s = fileHelpConfigRep.getNextLine()) != null) {
			repList0.add(s);
		}
		try {
			fileHelpConfigRep.close();
			fileHelpConfigRep = new fileHelper(configRep1);
		} catch (IOException e) {
			e.printStackTrace();
		}
		while ((s = fileHelpConfigRep.getNextLine()) != null) {
			repList1.add(s);
		}
		try {
			fileHelpConfigRep.close();
			fileHelpConfigRep = new fileHelper(configRep2);
		} catch (IOException e) {
			e.printStackTrace();
		}
		while ((s = fileHelpConfigRep.getNextLine()) != null) {
			repList2.add(s);
		}
	}

	private static void replaceEverything() {
		openFiles();
		String s = "";
		while (!(s = filehelperIn.getNextLines(count)).equals("null")) {
			for (String string : delList) {
				s = s.replaceAll(string, "");
			}
			for (int i = 0; i < repList0.size(); i++) {
				String rep0 = repList0.get(i);
				String rep1 = repList1.get(i);
				String rep2 = repList2.get(i);
				while (s.matches(rep0)) {
					s = refactorString(s, rep1, rep2);
				}
			}
			if (!s.equals("")) {
				s += "\n";
			}
			filehelperOut.appendToFile(s);
		}
	}

	// replaces @target in @input with @replacement
	private static String replaceS(String input, String target, String replacement) {
		int countInput = 0, countTarget = 0;
		while (countInput < input.length()) {
			while (countInput < input.length() && input.charAt(countInput) != target.charAt(countTarget)) {
				countInput++;
			}
			if (countInput == input.length()) {
				break;
			}
			int start = countInput;
			while (countTarget < target.length() && countInput < input.length()
					&& input.charAt(countInput) == target.charAt(countTarget)) {
				countInput++;
				countTarget++;
			}
			if (countTarget == target.length()) {
				input = input.substring(0, start) + replacement + input.substring(countInput);
				return input;
			}
			countTarget = 0;
		}
		return input;
	}

	// returns string in @input between @leftString and @rightString
	private static String getBetween(String input, String leftString, String rightString) {
		if (rightString.equals("")) {
			return input.substring(input.indexOf(leftString) + leftString.length());
		}
		else if(leftString.equals("")) {
			return input.substring(0,input.indexOf(rightString));
		}
		return input.substring(input.indexOf(leftString) + leftString.length(), input.indexOf(rightString));
	}

	// replaces first occurrence of targetPart in inputString with replacementPart
	private static String refactorString(String inputString, String targetPart, String replacementPart) {
		int pos1 = -1, pos2 = -1, pos3 = -1;
		String p1 = "", p2 = "", p3 = "";
		if (targetPart.contains(D_0)) {
			pos1 = targetPart.indexOf(D_0);
		}
		if (targetPart.contains(D_1)) {
			pos2 = targetPart.indexOf(D_1);
		}
		if (targetPart.contains(D_2)) {
			pos3 = targetPart.indexOf(D_2);
		}
		int[] arr = { pos1, pos2, pos3 };
		Arrays.sort(arr);
		if (pos1 != -1 && pos2 != -1 && pos3 != -1) {
			int i = 0;
			for (int j = 0; j < arr.length; j++) {
				if (arr[j] == pos1)
					i = j;
			}
			if (i == 0) {
				p1 = getBetween(inputString, targetPart.substring(0, pos1), targetPart.substring(pos1 + 1, arr[1]));
			} else if (i == 1) {
				p1 = getBetween(inputString, targetPart.substring(arr[i - 1] + 1, pos1),
						targetPart.substring(pos1 + 1, arr[2]));
			} else if (i == 2) {
				p1 = getBetween(inputString, targetPart.substring(arr[i - 1] + 1, pos1),
						targetPart.substring(pos1 + 1));
			}
			i = 0;
			for (int j = 0; j < arr.length; j++) {
				if (arr[j] == pos2)
					i = j;
			}
			if (i == 0) {
				p2 = getBetween(inputString, targetPart.substring(0, pos2), targetPart.substring(pos2 + 1, arr[1]));
			} else if (i == 1) {
				p2 = getBetween(inputString, targetPart.substring(arr[i - 1] + 1, pos2),
						targetPart.substring(pos2 + 1, arr[2]));
			} else if (i == 2) {
				p2 = getBetween(inputString, targetPart.substring(arr[i - 1] + 1, pos2),
						targetPart.substring(pos2 + 1));
			}
			i = 0;
			for (int j = 0; j < arr.length; j++) {
				if (arr[j] == pos3)
					i = j;
			}
			if (i == 0) {
				p3 = getBetween(inputString, targetPart.substring(0, pos3), targetPart.substring(pos3 + 1, arr[1]));
			} else if (i == 1) {
				p3 = getBetween(inputString, targetPart.substring(arr[i - 1] + 1, pos3),
						targetPart.substring(pos3 + 1, arr[2]));
			} else if (i == 2) {
				p3 = getBetween(inputString, targetPart.substring(arr[i - 1] + 1, pos3),
						targetPart.substring(pos3 + 1));
			}
		} else if (pos1 != -1 && pos2 != -1) {
			if (pos1 < pos2) {
				p1 = getBetween(inputString, targetPart.substring(0, pos1), targetPart.substring(pos1 + 1, pos2));
				p2 = getBetween(inputString, targetPart.substring(pos1 + 1, pos2), targetPart.substring(pos2 + 1));
			} else {
				p2 = getBetween(inputString, targetPart.substring(0, pos2), targetPart.substring(pos2 + 1, pos1));
				p1 = getBetween(inputString, targetPart.substring(pos2 + 1, pos1), targetPart.substring(pos1 + 1));
			}
		} else if (pos1 != -1 && pos3 != -1) {
			if (pos1 < pos2) {
				p1 = getBetween(inputString, targetPart.substring(0, pos1), targetPart.substring(pos1 + 1, pos3));
				p3 = getBetween(inputString, targetPart.substring(pos1 + 1, pos3), targetPart.substring(pos3 + 1));
			} else {
				p3 = getBetween(inputString, targetPart.substring(0, pos3), targetPart.substring(pos3 + 1, pos1));
				p1 = getBetween(inputString, targetPart.substring(pos3 + 1, pos1), targetPart.substring(pos1 + 1));
			}
		} else if (pos3 != -1 && pos2 != -1) {
			if (pos1 < pos2) {
				p3 = getBetween(inputString, targetPart.substring(0, pos3), targetPart.substring(pos3 + 1, pos2));
				p2 = getBetween(inputString, targetPart.substring(pos3 + 1, pos2), targetPart.substring(pos2 + 1));
			} else {
				p2 = getBetween(inputString, targetPart.substring(0, pos2), targetPart.substring(pos2 + 1, pos3));
				p3 = getBetween(inputString, targetPart.substring(pos2 + 1, pos3), targetPart.substring(pos3 + 1));
			}
		} else if (pos1 != -1) {
			p1 = getBetween(inputString, targetPart.substring(0, pos1), targetPart.substring(pos1 + 1));
		} else if (pos2 != -1) {
			p2 = getBetween(inputString, targetPart.substring(0, pos2), targetPart.substring(pos2 + 1));
		} else if (pos3 != -1) {
			p3 = getBetween(inputString, targetPart.substring(0, pos3), targetPart.substring(pos3 + 1));
		}
		if (replacementPart.contains(D_0) && targetPart.contains(D_0)) {
			replacementPart = replacementPart.substring(0, replacementPart.indexOf(D_0)) + p1
					+ replacementPart.substring(replacementPart.indexOf(D_0) + 1);
			targetPart = targetPart.substring(0, targetPart.indexOf(D_0)) + p1
					+ targetPart.substring(targetPart.indexOf(D_0) + 1);
		}
		if (replacementPart.contains(D_1) && targetPart.contains(D_1)) {
			replacementPart = replacementPart.substring(0, replacementPart.indexOf(D_1)) + p2
					+ replacementPart.substring(replacementPart.indexOf(D_1) + 1);
			targetPart = targetPart.substring(0, targetPart.indexOf(D_1)) + p2
					+ targetPart.substring(targetPart.indexOf(D_1) + 1);
		}
		if (replacementPart.contains(D_2) && targetPart.contains(D_2)) {
			replacementPart = replacementPart.substring(0, replacementPart.indexOf(D_2)) + p3
					+ replacementPart.substring(replacementPart.indexOf(D_2) + 1);
			targetPart = targetPart.substring(0, targetPart.indexOf(D_2)) + p3
					+ targetPart.substring(targetPart.indexOf(D_2) + 1);
		}
		while (targetPart.contains(D_0)) {
			targetPart = targetPart.replace(D_0, p1);
		}
		while (targetPart.contains(D_1)) {
			targetPart = targetPart.replace(D_1, p2);
		}
		while (targetPart.contains(D_2)) {
			targetPart = targetPart.replace(D_2, p3);
		}
		while (replacementPart.contains(D_0)) {
			replacementPart = replacementPart.replace(D_0, p1);
		}
		while (replacementPart.contains(D_1)) {
			replacementPart = replacementPart.replace(D_1, p2);
		}
		while (replacementPart.contains(D_2)) {
			replacementPart = replacementPart.replace(D_2, p3);
		}

		return replaceS(inputString, targetPart, replacementPart);
	}

	public static void addToDelConfig(String s) {
		if (fileHelpConfigDel == null) {
			try {
				fileHelpConfigDel.close();
				fileHelpConfigDel = new fileHelper(configDel);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		fileHelpConfigDel.appendToFile("\n" + s);
	}

	public static void addToRepConfig(String replace, String with) {
		try {
			fileHelpConfigRep.close();
			fileHelpConfigRep = new fileHelper(configRep0);
			String tmp = replace;
			while (tmp.contains(D_0)) {
				tmp = tmp.replace(D_0, ".*");
			}
			while (tmp.contains(D_1)) {
				tmp = tmp.replace(D_1, ".*");
			}
			while (tmp.contains(D_2)) {
				tmp = tmp.replace(D_2, ".*");
			}
			tmp = ".*" + tmp + ".*";
			fileHelpConfigRep.appendToFile("\n" + tmp);
			fileHelpConfigRep.close();
			fileHelpConfigRep = new fileHelper(configRep1);
			fileHelpConfigRep.appendToFile("\n" + replace);
			fileHelpConfigRep.close();
			fileHelpConfigRep = new fileHelper(configRep2);
			fileHelpConfigRep.appendToFile("\n" + with);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void replaceDs(String s, String ss, String sss) {
		try {
			if (!s.equals(D_0)) {
				fileHelpConfigRep.close();
				fileHelpConfigRep = new fileHelper(configRep0);
				fileHelpConfigRep.replaceDs(D_0, s);
				fileHelpConfigRep.close();
				fileHelpConfigRep = new fileHelper(configRep1);
				fileHelpConfigRep.replaceDs(D_0, s);
				fileHelpConfigRep.close();
				fileHelpConfigRep = new fileHelper(configRep2);
				fileHelpConfigRep.replaceDs(D_0, s);
				D_0 = s;
			}
			if (!ss.equals(D_1)) {
				fileHelpConfigRep.close();
				fileHelpConfigRep = new fileHelper(configRep0);
				fileHelpConfigRep.replaceDs(D_1, ss);
				fileHelpConfigRep = new fileHelper(configRep1);
				fileHelpConfigRep.replaceDs(D_1, ss);
				fileHelpConfigRep = new fileHelper(configRep2);
				fileHelpConfigRep.replaceDs(D_1, ss);
				D_1 = ss;
			}
			if (!sss.equals(D_2)) {
				fileHelpConfigRep.close();
				fileHelpConfigRep = new fileHelper(configRep0);
				fileHelpConfigRep.replaceDs(D_2, sss);
				fileHelpConfigRep = new fileHelper(configRep1);
				fileHelpConfigRep.replaceDs(D_2, sss);
				fileHelpConfigRep = new fileHelper(configRep2);
				fileHelpConfigRep.replaceDs(D_2, sss);
				D_2 = sss;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void enableGUI() {
		window.enableGUI();
	}

	public static String setPathsFiles(List<File> files, String s) {
		for (File f : files) {
			if (f.getName().contains(".FFF")) {
				input = new File(f.getAbsolutePath());
				s += f.getName() + "\t";
			} else if (f.getName().contains(".xml")) {
				output = new File(f.getAbsolutePath());
				s += f.getName() + "\t";
			}
		}
		return s;
	}

}
