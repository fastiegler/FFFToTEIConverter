package newConverter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.security.InvalidParameterException;

public class fileHelper {
	private BufferedReader br;
	private File f;

	public fileHelper(String path) throws IOException {
		f = new File(path);
		if (!f.exists()) {
			f.createNewFile();
		}
		br = new BufferedReader(new InputStreamReader(new FileInputStream(f), "UTF-8"));
	}

	public fileHelper(File f) throws IOException {
		this.f = f;
		if (!f.exists()) {
			f.createNewFile();
		}
		br = new BufferedReader(new InputStreamReader(new FileInputStream(f), "UTF-8"));
	}

	public String getNextLine() {
		String s = null;
		try {
			s = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return s;
	}

	public String getNextLines(int num) {
		if (num < 1) {
			throw new InvalidParameterException("number of lines must be > 0");
		}
		String s = "";
		for (int i = 0; i < num - 1; i++) {
			String news = this.getNextLine() + "\n";
			if (!news.equals("null\n")) {
				s += news;
			} else {
				if (!s.equals(""))
					s = s.substring(0, s.length() - 1);
				break;
			}
		}
		String ss = this.getNextLine();
		if (!(ss == null) || s.equals("")) {
			s += ss;
		}
		if (s != null)
			if (s.equals("null"))
				return null;
		return s;
	}

	public void resetReader() throws FileNotFoundException {
		try {
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		br = new BufferedReader(new FileReader(f));
	}

	public void emtyFile() throws IOException {
		br.close();
		f.delete();
		f.createNewFile();
		br = new BufferedReader(new FileReader(f));
	}

	public boolean appendToFile(String s) {
		try {
			Files.write(f.toPath(), s.getBytes(), StandardOpenOption.APPEND);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public void replaceDs(String oldD, String newD) {
		try {
			this.resetReader();
			String newFile = "", oldFile;
			while ((oldFile = this.getNextLine()) != null) {
				while (oldFile.contains(oldD)) {
					oldFile = oldFile.replace(oldD, newD);
				}
				newFile += oldFile + "\n";
			}
			newFile = newFile.substring(0, newFile.length() - 1);
			this.emtyFile();
			this.appendToFile(newFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void close() {
		if (br != null) {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
