package xmlparser.file.test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import xmlparser.file.object.Campo;
import xmlparser.file.parser.FileParser;
import xmlparser.file.pdf.PDFFiles;
import xmlparser.file.xml.XMLFiles;

public class XMLParser {
	private static String FilePathSRC;
	private static String FilePathDEST;
	
	private static final String expressionFields = "//fields/fullName";
	private static final String expressionTypes = "//fields/type";
	
	public static void main(String[] args) throws Exception {
		FilePathSRC = new XMLFiles().SearchPathForObjectsXML();
		FilePathDEST = new PDFFiles().SearchPathForPDFFiles();
		
		File[] listXMLFiles = new XMLFiles().finder(FilePathSRC);
		List<Campo> saida = new ArrayList<>();
		Map<String, List<Campo>> mapa = new HashMap<>();
		
		for(File f:listXMLFiles){
			String namePDF = FilePathDEST.concat("/"+f.getName()+".pdf");
			for (int i = 0; i < listXMLFiles.length; i++) {
				List<String> values = new FileParser().parsingValues(listXMLFiles[i], expressionFields);
				List<String> types = new FileParser().parsingValues(listXMLFiles[i], expressionTypes);
				for(int j=0;j<types.size();j++){
					saida.add(new Campo(values.get(j),types.get(j)));
				}
			}
			mapa.put(f.getName(), saida);
			new PDFFiles().exportToPDF(mapa, namePDF);
		}
		
	}
}
