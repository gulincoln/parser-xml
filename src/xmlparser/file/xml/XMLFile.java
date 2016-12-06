package xmlparser.file.xml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class XMLFile {
	private final String beans = "beans";

	private List<String> ListOfExpressions() {
		List<String> expressions = new ArrayList<>();

		String expressionFields = "//fields/fullName";
		String expressionTypes = "//fields/type";

		expressions.add(expressionFields);
		expressions.add(expressionTypes);

		return expressions;
	}

	public Document parsingValuesForXMLFiles(File file) throws Exception {
		String nameObject = (file.getName());

		XPath xpath = XPathFactory.newInstance().newXPath();

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		List<String> expressions = ListOfExpressions();
		try {
			Document doc = dbf.newDocumentBuilder().parse(file);
			Document newXmlDocument = dbf.newDocumentBuilder().newDocument();

			Element elemBeans = newXmlDocument.createElement(beans);
			newXmlDocument.appendChild(elemBeans);

			Element bean = newXmlDocument.createElement("bean");
			bean.setAttribute("id", nameObject);
			bean.setAttribute("class", "com.salesforce.dataloader.process.ProcessRunner");
			bean.setAttribute("singleton", "false");
			elemBeans.appendChild(bean);

			NodeList FieldSet = doc.getElementsByTagName("fields");

			for (int i = 0; i < FieldSet.getLength(); i++) {
				Element elemFieldSet = newXmlDocument.createElement(FieldSet.item(i).getNodeName());
				bean.appendChild(elemFieldSet);

				NodeList properties = FieldSet.item(i).getChildNodes();

				for (int j = 0; j < properties.getLength(); j++) {
					if(!(properties.item(j).getNodeName().equals("formula") || properties.item(j).getNodeName().equals("inlineHelpText"))){
						Node fullName = newXmlDocument.importNode(properties.item(j), true);
						elemFieldSet.appendChild(fullName);
					}
				}
			}

			return newXmlDocument;
		} catch (SAXException | IOException | ParserConfigurationException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void buildXML(Document doc, String dir, String nameFile) throws Exception {
		String outputFile = dir + "/" + nameFile + ".xml";

		// write the content into xml file
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File(outputFile));

		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

		transformer.transform(source, result);

		System.out.println("file saved!");

	}

	public void concatXMLFiles(List<File> xmlfiles, String dir) throws Exception {
		Writer outputWriter = new FileWriter(dir + "/mergedFile.xml");
		XMLOutputFactory xmlOutFactory = XMLOutputFactory.newFactory();
		XMLEventWriter xmlEventWriter = xmlOutFactory.createXMLEventWriter(outputWriter);
		XMLEventFactory xmlEventFactory = XMLEventFactory.newFactory();

		xmlEventWriter.add(xmlEventFactory.createStartDocument());
		xmlEventWriter.add(xmlEventFactory.createStartElement("", null, "rootSet"));

		XMLInputFactory xmlInFactory = XMLInputFactory.newFactory();
		for (File rootFile : xmlfiles) {
			XMLEventReader xmlEventReader = xmlInFactory.createXMLEventReader(new StreamSource(rootFile));
			XMLEvent event = xmlEventReader.nextEvent();
			// Skip ahead in the input to the opening document element
			while (event.getEventType() != XMLEvent.START_ELEMENT) {
				event = xmlEventReader.nextEvent();
			}

			do {
				xmlEventWriter.add(event);
				event = xmlEventReader.nextEvent();
			} while (event.getEventType() != XMLEvent.END_DOCUMENT);
			xmlEventReader.close();
		}

		xmlEventWriter.add(xmlEventFactory.createEndElement("", null, "rootSet"));
		xmlEventWriter.add(xmlEventFactory.createEndDocument());

		JOptionPane.showMessageDialog(null, "arquivo merged criado com sucesso!");

		xmlEventWriter.close();
		outputWriter.close();
	}
}
