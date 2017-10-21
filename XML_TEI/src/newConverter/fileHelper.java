package newConverter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.security.InvalidParameterException;

public class fileHelper {
	private BufferedReader br;
	private File f;

	public fileHelper(String path) throws IOException {
		this.f = new File(path);
		if (!this.f.exists()) {
			this.f.createNewFile();
		}
		this.open();
	}

	public fileHelper(File f) throws IOException {
		this.f = f;
		if (!f.exists()) {
			f.createNewFile();
		}
		this.open();
	}

	public String getNextLine() {
		String s = null;
		try {
			s = this.br.readLine();
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
		this.close();
		this.open();
	}

	public void emtyFile() throws IOException {
		this.close();
		this.f.delete();
		this.f.createNewFile();
		this.open();
	}

	public boolean appendToFile(String s) {
		this.close();
		try {
			Files.write(this.f.toPath(), s.getBytes(), StandardOpenOption.APPEND);
			this.open();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.open();
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
		if (this.br != null) {
			try {
				this.br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public void open() {
		close();
		try {
			this.br = new BufferedReader(new InputStreamReader(new FileInputStream(f), "UTF-8"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}
