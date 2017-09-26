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
		this.f=f;
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
		String s = "";
		for (int i = 0; i < num-1; i++) {
			String news=this.getNextLine()+"\n";
			if (!news.equals("null\n")) {
				s+=news;
			}else {
				break;
			}			
		}
		s+=this.getNextLine();
		return s;
	}
	
	public void resetReader() throws FileNotFoundException {
		br = new BufferedReader(new FileReader(f));
	}
	
	public void emtyFile() throws IOException {
		f.delete();
		f.createNewFile();
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
}
