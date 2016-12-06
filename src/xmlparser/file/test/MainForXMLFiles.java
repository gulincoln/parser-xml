package xmlparser.file.test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.w3c.dom.Document;

import xmlparser.file.File.FileChooser;
import xmlparser.file.xml.XMLFile;

public class MainForXMLFiles {
	private static String FilePathSRC;
	private static String FilePathDEST;
	
	private static final String expressionFields = "//fields/fullName";
	private static final String expressionTypes = "//fields/type";

	public static void main(String[] args) throws Exception{
		FilePathSRC = new FileChooser().SearchPathForInputFiles();
		FilePathDEST = new FileChooser().SearchPathForOutputFiles();
		
		List<String> expressions = new ArrayList<>();
		expressions.add(expressionFields);
		expressions.add(expressionTypes);
		
		File[] listXMLFiles = new FileChooser().finder(FilePathSRC,".object");
		Document doc;

		for(File f:listXMLFiles){
			doc = new XMLFile().parsingValuesForXMLFiles(f);
			new XMLFile().buildXML(doc,FilePathDEST,f.getName());
		}
		List<File> resultFiles = Arrays.asList(new FileChooser().finder(FilePathDEST,".xml")) ;
		new XMLFile().concatXMLFiles(resultFiles, FilePathDEST);
	}
}
