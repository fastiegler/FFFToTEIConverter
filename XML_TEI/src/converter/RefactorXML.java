package converter;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class RefactorXML {

	private FileReaderWriter file;
	private ArrayList<String> delL;

	public RefactorXML(File input, File output) {
		initDelList();
		file = new FileReaderWriter(input, output);
	}
	
	public void startConversion() {
		while (replaceLine());
	}

	private void initDelList() {
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

	private boolean replaceLine() {
		String line = file.getNextLine();
		if (line == null) {
			return false;
		}
		String updatedLine = line;
		for (String string : delL) {
			updatedLine = updatedLine.replaceAll(string, "");
		}
//
		if (updatedLine.matches("                                 \\d*")) {
			updatedLine=refactorString(updatedLine,"                                 [","<fw><seg rend=“zentriert“>[</seg></fw>");
		}
		if (updatedLine.matches("                                \\d*")) {
			updatedLine=refactorString(updatedLine,"                                [","<fw><seg rend=“zentriert“>[</seg></fw>");
			
		}
		if (updatedLine.matches("                               \\d*")) {
			updatedLine=refactorString(updatedLine,"                               [","<fw><seg rend=“zentriert“>[</seg></fw>");
			
		}
		if (updatedLine.matches(".*<JD:\"T-Druckfahnen/.*\">Druckfahnen/.*</FD:\"T-PAGINA\">.*")) {
			updatedLine=refactorString(updatedLine,"<JD:\"T-Druckfahnen/[\">Druckfahnen/]</FD:\"T-PAGINA\">","<pb n=\"[\" corresp=\"#sn1513400]0\" …>");
		}
		if (updatedLine.matches("<FD:\"T-SEITENSIGLE\"><JD:\"S-.*\">.* <BD-> 2<BD><FC></FD:\"T-SEITENSIGLE\">")) {
			updatedLine=refactorString(updatedLine,"<FD:\"T-SEITENSIGLE\"><JD:\"S-[\">] <BD-> 2<BD><FC></FD:\"T-SEITENSIGLE\">","<pb … xml:id=\"F_[\"/>");
		}
		if (updatedLine.matches("<UN+>.*<UN>")) {
			updatedLine=refactorString(updatedLine,"<UN+>[<UN>","<hi rend=\"underline\">[</hi>");
		}
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
		if (z.equals("")) {
			return x.substring(x.indexOf(o)+o.length());
		}
		return x.substring(x.indexOf(o)+o.length(),x.indexOf(z));
	}
	
	//replaces first occurrence of targetPart in inputString with replacementPart
	private static String refactorString(String inputString,String targetPart,String replacementPart){
		int pos1=-1,pos2=-1,pos3=-1;
		String p1="",p2="",p3="";
		if (targetPart.contains("[")) {
			pos1=targetPart.indexOf("[");
		}
		if (targetPart.contains("]")) {
			pos2=targetPart.indexOf("]");
		}
		if (targetPart.contains("}")) {
			pos3=targetPart.indexOf("}");
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
				p1=getBetween(inputString, targetPart.substring(0, pos1), targetPart.substring(pos1+1, arr[1]));
			}
			else if (i==1) {
				p1=getBetween(inputString, targetPart.substring(arr[i-1]+1, pos1), targetPart.substring(pos1+1, arr[2]));
			}else if (i==2) {
				p1=getBetween(inputString, targetPart.substring(arr[i-1]+1, pos1), targetPart.substring(pos1+1));
			}
			i=0;
			for (int j=0;j<arr.length;j++) {
				if(arr[j]==pos2)
					i=j;
			}
			if (i==0) {
				p2=getBetween(inputString, targetPart.substring(0, pos2), targetPart.substring(pos2+1, arr[1]));
			}
			else if (i==1) {
				p2=getBetween(inputString, targetPart.substring(arr[i-1]+1, pos2), targetPart.substring(pos2+1, arr[2]));
			}else if (i==2) {
				p2=getBetween(inputString, targetPart.substring(arr[i-1]+1, pos2), targetPart.substring(pos2+1));
			}
			i=0;
			for (int j=0;j<arr.length;j++) {
				if(arr[j]==pos3)
					i=j;
			}
			if (i==0) {
				p3=getBetween(inputString, targetPart.substring(0, pos3), targetPart.substring(pos3+1, arr[1]));
			}
			else if (i==1) {
				p3=getBetween(inputString, targetPart.substring(arr[i-1]+1, pos3), targetPart.substring(pos3+1, arr[2]));
			}else if (i==2) {
				p3=getBetween(inputString, targetPart.substring(arr[i-1]+1, pos3), targetPart.substring(pos3+1));
			}
		}
		else if(pos1!=-1&&pos2!=-1) {
			if (pos1<pos2) {
				p1=getBetween(inputString, targetPart.substring(0, pos1), targetPart.substring(pos1+1,pos2));
				p2=getBetween(inputString, targetPart.substring(pos1+1, pos2), targetPart.substring(pos2+1));
			}
			else {
				p2=getBetween(inputString, targetPart.substring(0, pos2), targetPart.substring(pos2+1,pos1));
				p1=getBetween(inputString, targetPart.substring(pos2+1, pos1), targetPart.substring(pos1+1));
			}
		}
		else if(pos1!=-1&&pos3!=-1) {
			if (pos1<pos2) {
			p1=getBetween(inputString, targetPart.substring(0, pos1), targetPart.substring(pos1+1,pos3));
			p3=getBetween(inputString, targetPart.substring(pos1+1, pos3), targetPart.substring(pos3+1));
		}
		else {
			p3=getBetween(inputString, targetPart.substring(0, pos3), targetPart.substring(pos3+1,pos1));
			p1=getBetween(inputString, targetPart.substring(pos3+1, pos1), targetPart.substring(pos1+1));
		}
		}
		else if(pos3!=-1&&pos2!=-1) {
			if (pos1<pos2) {
				p3=getBetween(inputString, targetPart.substring(0, pos3), targetPart.substring(pos3+1,pos2));
				p2=getBetween(inputString, targetPart.substring(pos3+1, pos2), targetPart.substring(pos2+1));
			}
			else {
				p2=getBetween(inputString, targetPart.substring(0, pos2), targetPart.substring(pos2+1,pos3));
				p3=getBetween(inputString, targetPart.substring(pos2+1, pos3), targetPart.substring(pos3+1));
			}
		}
		else if(pos1!=-1) {
			p1=getBetween(inputString, targetPart.substring(0, pos1), targetPart.substring(pos1+1));
		}
		else if(pos2!=-1) {
			p2=getBetween(inputString, targetPart.substring(0, pos2), targetPart.substring(pos2+1));
		}
		else if(pos3!=-1) {
			p3=getBetween(inputString, targetPart.substring(0, pos3), targetPart.substring(pos3+1));
		}
		if (replacementPart.contains("[")&&targetPart.contains("[")) {
			replacementPart=replacementPart.substring(0, replacementPart.indexOf("["))+p1+replacementPart.substring(replacementPart.indexOf("[")+1);
			targetPart=targetPart.substring(0, targetPart.indexOf("["))+p1+targetPart.substring(targetPart.indexOf("[")+1);
		}
		if (replacementPart.contains("]")&&targetPart.contains("]")) {
			replacementPart=replacementPart.substring(0, replacementPart.indexOf("]"))+p2+replacementPart.substring(replacementPart.indexOf("]")+1);
			targetPart=targetPart.substring(0, targetPart.indexOf("]"))+p2+targetPart.substring(targetPart.indexOf("]")+1);
		}
		if (replacementPart.contains("}")&&targetPart.contains("}")) {
			replacementPart=replacementPart.substring(0, replacementPart.indexOf("}"))+p3+replacementPart.substring(replacementPart.indexOf("}")+1);
			targetPart=targetPart.substring(0, targetPart.indexOf("}"))+p3+targetPart.substring(targetPart.indexOf("}")+1);
		}
		return replaceS(inputString, targetPart, replacementPart);
	}

}
