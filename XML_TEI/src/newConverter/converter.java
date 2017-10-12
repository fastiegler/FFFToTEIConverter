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

	public static void btnConvert() {
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
		while ((s = filehelperIn.getNextLines(count))!=null) {
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

	// returns string in @input between @leftString and @rightString
	private static String getBetween(String input, String leftString, String rightString) {
		if (leftString.equals("")&&rightString.equals("")) {
			return "";
		}
		if (!(input.contains(leftString) && input.contains(rightString))) {
			System.out.println(input);
			System.out.println(leftString + "\t" + rightString);
			return null;
		}
		String ret = input;
		if (!leftString.equals(""))
		ret=ret.substring(ret.indexOf(leftString)+leftString.length());
		if (!rightString.equals(""))
		ret=ret.substring(0,ret.indexOf(rightString)+rightString.length()-1);
		if (!leftString.equals(""))
			ret = ret.replace(leftString, "");
		if (!rightString.equals(""))
			ret = ret.replace(rightString, "");
		return ret;
	}

	// replaces first occurrence of targetPart in inputString with replacementPart
	private static String refactorString(String inputString, String targetPart, String replacementPart) {
		int pos0 = -1, pos1 = -1, pos2 = -1;
		String p0 = "", p1 = "", p2 = "";
		boolean b0=false,b1=false,b2=false;
		if (targetPart.contains(D_0)) {
			pos0 = targetPart.indexOf(D_0);
			b0=true;
		}
		else {
			targetPart=targetPart+D_0;
			pos0 = targetPart.indexOf(D_0);
		}
		if (targetPart.contains(D_1)) {
			pos1 = targetPart.indexOf(D_1);
			b1=true;
		}
		else {
			targetPart=targetPart+D_1;
			pos1 = targetPart.indexOf(D_1);
		}
		if (targetPart.contains(D_2)) {
			pos2 = targetPart.indexOf(D_2);
			b2=true;
		}
		else {
			targetPart=targetPart+D_2;
			pos2 = targetPart.indexOf(D_2);
		}
		int[] arr = { pos0, pos1, pos2 };
		Arrays.sort(arr);
		// all 3 variables found
		if (pos0 != -1 && pos1 != -1 && pos2 != -1) {
			int i = 0;
			for (int j = 0; j < arr.length; j++) {
				if (arr[j] == pos0) {
					i = j;
					break;
				}
			}
			// pos0 leftmost
			if (i == 0) {
				p0 = getBetween(inputString, targetPart.substring(0, pos0),
						targetPart.substring(pos0 + D_0.length(), arr[i + 1]));
			}
			// pos0 middle
			else if (i == 1) {
				p0 = getBetween(inputString, targetPart.substring(arr[i - 1] + D_0.length(), pos0),
						targetPart.substring(pos0 + D_0.length(), arr[i + 1]));
			}
			// pos0 rigthmost
			else if (i == 2) {
				int offset = D_1.length();
				if (pos1 < pos2) {
					offset = D_2.length();
				}
				p0 = getBetween(inputString, targetPart.substring(arr[i - 1] + offset, pos0),
						targetPart.substring(pos0 + D_0.length()));
			}
			i = 0;
			for (int j = 0; j < arr.length; j++) {
				if (arr[j] == pos1)
					i = j;
			}
			// pos1 leftmost
			if (i == 0) {
				p1 = getBetween(inputString, targetPart.substring(0, pos1), targetPart.substring(pos1 + D_1.length(), arr[i+1]));
			}
			// pos1 middle
			else if (i == 1) {
				p1 = getBetween(inputString, targetPart.substring(arr[i - 1] + D_1.length(), pos1),
						targetPart.substring(pos1 + D_1.length(), arr[i+1]));
			}
			// pos1 rigthmost
			else if (i == 2) {
				int offset = D_0.length();
				if (pos0 < pos2) {
					offset = D_2.length();
				}
				p1 = getBetween(inputString, targetPart.substring(arr[i - 1] + offset, pos1),
						targetPart.substring(pos1 + D_1.length()));
			}
			i = 0;
			for (int j = 0; j < arr.length; j++) {
				if (arr[j] == pos2)
					i = j;
			}
			// pos2 leftmost
			if (i == 0) {
				p2 = getBetween(inputString, targetPart.substring(0, pos2), targetPart.substring(pos2 + D_2.length(), arr[i+1]));
			} 
			// pos2 middle
			else if (i == 1) {
				p2 = getBetween(inputString, targetPart.substring(arr[i - 1] + D_2.length(), pos2),
						targetPart.substring(pos2 + D_2.length(), arr[i+1]));
			}
			// pos2 rigthmost
			else if (i == 2) {
				int offset = D_0.length();
				if (pos0 < pos1) {
					offset = D_1.length();
				}
				p2 = getBetween(inputString, targetPart.substring(arr[i - 1] + offset, pos2),
						targetPart.substring(pos2 + D_2.length()));
			}
		}
		if(!b0) {
			targetPart = targetPart.replace(D_0, "");
		}
		if(!b1) {
			targetPart = targetPart.replace(D_1, "");
		}
		if(!b2) {
			targetPart = targetPart.replace(D_2, "");
		}
		while (b0&&targetPart.contains(D_0)) {
			targetPart = targetPart.replace(D_0, p0);
		}
		while (b1&&targetPart.contains(D_1)) {
			targetPart = targetPart.replace(D_1, p1);
		}
		while (b2&&targetPart.contains(D_2)) {
			targetPart = targetPart.replace(D_2, p2);
		}
		while (b0&&replacementPart.contains(D_0)) {
			replacementPart = replacementPart.replace(D_0, p0);
		}
		while (b1&&replacementPart.contains(D_1)) {
			replacementPart = replacementPart.replace(D_1, p1);
		}
		while (b2&&replacementPart.contains(D_2)) {
			replacementPart = replacementPart.replace(D_2, p2);
		}
		return inputString.replace(targetPart, replacementPart);
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
				fileHelpConfigRep.close();
				fileHelpConfigRep = new fileHelper(configRep1);
				fileHelpConfigRep.replaceDs(D_1, ss);
				fileHelpConfigRep.close();
				fileHelpConfigRep = new fileHelper(configRep2);
				fileHelpConfigRep.replaceDs(D_1, ss);
				D_1 = ss;
			}
			if (!sss.equals(D_2)) {
				fileHelpConfigRep.close();
				fileHelpConfigRep = new fileHelper(configRep0);
				fileHelpConfigRep.replaceDs(D_2, sss);
				fileHelpConfigRep.close();
				fileHelpConfigRep = new fileHelper(configRep1);
				fileHelpConfigRep.replaceDs(D_2, sss);
				fileHelpConfigRep.close();
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
