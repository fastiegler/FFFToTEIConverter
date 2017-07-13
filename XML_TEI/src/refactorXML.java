import java.util.ArrayList;
public class refactorXML {

//test
	private static FileReaderWriter file;
	private static ArrayList<String> delL;
	public static void main(String[] args) {
		initDelList();
		
		file=new FileReaderWriter("in.FFF","out.xml");
		while(replaceLine());
	}

	private static void initDelList() {
		delL=new ArrayList<>();
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
		String line= file.getNextLine();
		if(line==null){
			return false;
		}
		String updatedLine=line;
		for (String string : delL) {
			updatedLine=updatedLine.replaceAll(string, "");
		}
		if(!updatedLine.equals("")){
			updatedLine+="\n";
		}
		file.appendToOutput(updatedLine);
		return true;
	}

	
		
	
}
