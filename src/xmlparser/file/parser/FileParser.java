package xmlparser.file.parser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import xmlparser.file.object.Campo;

public class FileParser {
		public List<String> parsingValues(File file, String expression) {

			XPath xpath = XPathFactory.newInstance().newXPath();
			List<String> fields = new ArrayList<>();

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder;
			try {
				docBuilder = dbf.newDocumentBuilder();
				Document doc = docBuilder.parse(file);

				NodeList listProperty = (NodeList) xpath.compile(expression).evaluate(doc, XPathConstants.NODESET);

				for (int i = 0; i < listProperty.getLength(); i++) {
					fields.add(listProperty.item(i).getTextContent());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return fields;
		}
}
