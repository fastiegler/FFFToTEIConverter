package converter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

public class FileReaderWriter {
	private BufferedReader br;
	private File in;
	private File out;

	public FileReaderWriter() {
		in = new File(System.getProperty("user.dir")+"\\XML_TEI\\in.FFF");
		try {
			br = new BufferedReader(new FileReader(in));
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		}
		out = new File(System.getProperty("user.dir")+"\\XML_TEI\\out.xml");
		if (!out.exists()) {
			try {
				out.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else{
			out.delete();
			try {
				out.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public FileReaderWriter(String pathIn, String pathOut) {
		in = new File(pathIn);
		try {
			br = new BufferedReader(new FileReader(in));
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		}
		out = new File(pathOut);
		if (!out.exists()) {
			try {
				out.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else{
			out.delete();
			try {
				out.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public FileReaderWriter(File finleIn, File finleOut) {
		in = finleIn;
		try {
			br = new BufferedReader(new FileReader(in));
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		}
		out = finleOut;
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

	public boolean appendToOutput(String s) {
		try {
			Files.write(out.toPath(), s.getBytes(), StandardOpenOption.APPEND);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
}
