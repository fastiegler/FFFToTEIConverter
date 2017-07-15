import java.util.ArrayList;
import java.util.Arrays;

public class refactorXML {

	private static FileReaderWriter file;
	private static ArrayList<String> delL;

	public static void main(String[] args) {
		System.out.println(g("#nananana lalallala# \\hohoohoh|", "#[ ]# \\}|", "<subst><del>[ ] </del> <add>}</add></subst>"));
		initDelList();
		file = new FileReaderWriter("in.FFF", "out.xml");
		while (replaceLine());
	}

	private static void initDelList() {
		delL = new ArrayList<>();
		delL.add("<RD:\"EBENE \\d*\">");
		delL.add("</?FD:\"T-PAGINA\">");
		delL.add("<EL>");
		delL.add("<JL:Sprung,\"DS-Druckfahnen/?\\d*\">");
		delL.add("<OB:FO:\".*\\.bmp\",\\d*\\.\\d*,\\d*\\.\\d*>");
		delL.add("<TB>");
		delL.add("<FC:\\d*,\\d*,\\d*>");
		delL.add("<JL:\"SPRUNG alternativ\",\"KK-Drf \\d*\">");
		delL.add("<RD>");
		delL.add("<BD\\+><FT:Webdings,SR,SY><PT:10><JL:\".*\">i<BD><FT><PT>");
		delL.add("<GR:\"Band \\d*\">");
		delL.add("<CS:ZEILENZAHL>\\d*</CS>");
	}

	private static boolean replaceLine() {
		String line = file.getNextLine();
		if (line == null) {
			return false;
		}
		String updatedLine = line;
		for (String string : delL) {
			updatedLine = updatedLine.replaceAll(string, "");
		}
//
		if (!updatedLine.equals("")) {
			updatedLine += "\n";
		}
		file.appendToOutput(updatedLine);
		return true;
	}
	private static String replaceS(String input, String target, String replacement) {
		int countInput=0,countTarget=0;
		while (countInput<input.length()) {
			while (countInput<input.length()&&input.charAt(countInput) != target.charAt(countTarget)) {
				countInput++;
			}
			if (countInput==input.length()) {
				break;
			}
			int start = countInput;
			while (countTarget < target.length() &&countInput<input.length()&& input.charAt(countInput) == target.charAt(countTarget)) {
					countInput++;
					countTarget++;				
			}
			if (countTarget == target.length()) {
				input = input.substring(0, start) + replacement + input.substring(countInput);
				return input;
			}
			countTarget=0;
		}
		return input;
	}
	private static String getBetween(String x,String o,String z){
		return x.substring(x.indexOf(o)+o.length(),x.indexOf(z));
	}
	private static String g(String x,String o,String z){
		int pos1=-1,pos2=-1,pos3=-1;
		String p1="",p2="",p3="";
		if (o.contains("[")) {
			pos1=o.indexOf("[");
		}
		if (o.contains("]")) {
			pos2=o.indexOf("]");
		}
		if (o.contains("}")) {
			pos3=o.indexOf("}");
		}
		int[] arr= {pos1,pos2,pos3};
		Arrays.sort(arr);		
		if (pos1!=-1&&pos2!=-1&&pos3!=-1) {
			int i=0;
			for (int j=0;j<arr.length;j++) {
				if(arr[j]==pos1)
					i=j;
			}
			if (i==0) {
				p1=getBetween(x, o.substring(0, pos1), o.substring(pos1+1, arr[1]));
			}
			else if (i==1) {
				p1=getBetween(x, o.substring(arr[i-1]+1, pos1), o.substring(pos1+1, arr[2]));
			}else if (i==2) {
				p1=getBetween(x, o.substring(arr[i-1]+1, pos1), o.substring(pos1+1));
			}
			i=0;
			for (int j=0;j<arr.length;j++) {
				if(arr[j]==pos2)
					i=j;
			}
			if (i==0) {
				p2=getBetween(x, o.substring(0, pos2), o.substring(pos2+1, arr[1]));
			}
			else if (i==1) {
				p2=getBetween(x, o.substring(arr[i-1]+1, pos2), o.substring(pos2+1, arr[2]));
			}else if (i==2) {
				p2=getBetween(x, o.substring(arr[i-1]+1, pos2), o.substring(pos2+1));
			}
			i=0;
			for (int j=0;j<arr.length;j++) {
				if(arr[j]==pos3)
					i=j;
			}
			if (i==0) {
				p3=getBetween(x, o.substring(0, pos3), o.substring(pos3+1, arr[1]));
			}
			else if (i==1) {
				p3=getBetween(x, o.substring(arr[i-1]+1, pos3), o.substring(pos3+1, arr[2]));
			}else if (i==2) {
				p3=getBetween(x, o.substring(arr[i-1]+1, pos3), o.substring(pos3+1));
			}
		}
		else if(pos1!=-1&&pos2!=-1) {
			if (pos1<pos2) {
				p1=getBetween(x, o.substring(0, pos1), o.substring(pos1+1,pos2));
				p2=getBetween(x, o.substring(pos1+1, pos2), o.substring(pos2+1));
			}
			else {
				p2=getBetween(x, o.substring(0, pos2), o.substring(pos2+1,pos1));
				p1=getBetween(x, o.substring(pos2+1, pos1), o.substring(pos1+1));
			}
		}
		else if(pos1!=-1&&pos3!=-1) {
			if (pos1<pos2) {
			p1=getBetween(x, o.substring(0, pos1), o.substring(pos1+1,pos3));
			p3=getBetween(x, o.substring(pos1+1, pos3), o.substring(pos3+1));
		}
		else {
			p3=getBetween(x, o.substring(0, pos3), o.substring(pos3+1,pos1));
			p1=getBetween(x, o.substring(pos3+1, pos1), o.substring(pos1+1));
		}
		}
		else if(pos3!=-1&&pos2!=-1) {
			if (pos1<pos2) {
				p3=getBetween(x, o.substring(0, pos3), o.substring(pos3+1,pos2));
				p2=getBetween(x, o.substring(pos3+1, pos2), o.substring(pos2+1));
			}
			else {
				p2=getBetween(x, o.substring(0, pos2), o.substring(pos2+1,pos3));
				p3=getBetween(x, o.substring(pos2+1, pos3), o.substring(pos3+1));
			}
		}
		else if(pos1!=-1) {
			p1=getBetween(x, o.substring(0, pos1), o.substring(pos1+1));
		}
		else if(pos2!=-1) {
			p2=getBetween(x, o.substring(0, pos2), o.substring(pos2+1));
		}
		else if(pos3!=-1) {
			p3=getBetween(x, o.substring(0, pos3), o.substring(pos3+1));
		}
		if (z.contains("[")&&o.contains("[")) {
			z=z.substring(0, z.indexOf("["))+p1+z.substring(z.indexOf("[")+1);
			o=o.substring(0, o.indexOf("["))+p1+o.substring(o.indexOf("[")+1);
		}
		if (z.contains("]")&&o.contains("]")) {
			z=z.substring(0, z.indexOf("]"))+p2+z.substring(z.indexOf("]")+1);
			o=o.substring(0, o.indexOf("]"))+p2+o.substring(o.indexOf("]")+1);
		}
		if (z.contains("}")&&o.contains("}")) {
			z=z.substring(0, z.indexOf("}"))+p3+z.substring(z.indexOf("}")+1);
			o=o.substring(0, o.indexOf("}"))+p3+o.substring(o.indexOf("}")+1);
		}
		return replaceS(x, o, z);
	}

}
