import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class countExpressions {

	static String[] a = { "AL", "AP", "AS", "AU", "BC", "BD", "BH", "BK", "BO", "BP", "BR", "BT", "CA", "CD", "CE",
			"CH", "CH", "CM", "CN", "CN", "CR", "CS", "DA", "DC", "DP", "DI", "DL", "DO", "DS", "DV", "EH", "EL", "EN",
			"FC", "FD", "FI", "FL", "FO", "FT", "FX", "GA", "GD", "GF", "GI", "GM", "GP", "GQ", "GR", "GT", "HC", "HD",
			"HE", "HG", "HI", "HR", "HS", "HZ", "ID", "IN", "IT", "JD", "JL", "JU", "KN", "KN", "KT", "KT", "LF", "LF",
			"LH", "LS", "LT", "LV", "LW", "MD", "ML", "MR", "NM", "NT", "NO", "OB", "OL", "OU", "PB", "PC", "PL", "PN",
			"PP", "PS", "PT", "PW", "PX", "QL", "RD", "RH", "RO", "RT", "RT", "RT", "SB", "SC", "SD", "SE", "SH", "SN",
			"SO", "SP", "SR", "SS", "SY", "TA", "TB", "TO", "TS", "UL", "UN", "UX", "VA", "VG", "VT", "WP", "WW", "ZM",
			"CL", "DF", "DQ", "DT", "FE", "FP", "HL", "IR", "IX", "LE", "LI", "LK", "LN", "NO", "OD", "PA", "PD", "PF",
			"PR", "QT", "RC", "RE", "RM", "RP", "ST", "SU", "SW", "TE", "TF", "TM", "TP", "TT", "TX", "VI" };

	public static void main(String[] args) {
		readFile();
		int count = a.length;
		for (int i = 0; i < a.length; i++) {
			if (a[i] != "ооо") {
				count--;
			}
		}
		System.out.println("" + count);
	}

	public static void readFile() {
		BufferedReader br = null;
		String s = "";
		try {
			br = new BufferedReader(new FileReader(new File("a.txt")));
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		}
		try {
			s = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		while (s != null) {
			for (int i = 0; i < a.length; i++) {
				if (s.contains("<" + a[i])) {
					System.out.println(a[i]);
					String t = a[i];
					for (int j = 0; j < a.length; j++) {
						if (a[j].equals(t)) {
							a[j] = "ооо";
						}

					}
					a[i] = "ооо";
				}
			}
			try {
				s = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
