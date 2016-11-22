package xmlparser.file.pdf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import xmlparser.file.object.Campo;

public class PDFFiles {

	public String SearchPathForPDFFiles() throws IOException {
		JFileChooser chooser = new JFileChooser();
		chooser.setDialogTitle("Search xml files for Objects Salesforce");
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int result = chooser.showOpenDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) {
			return chooser.getSelectedFile().getAbsolutePath();
		} else {
			return null;
		}
	}

	public void exportToPDF(Map<String, List<Campo>> mapaFields, String namePDF) {
		com.itextpdf.text.Document document = new com.itextpdf.text.Document(PageSize.A4);

		try {
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(namePDF));
			document.open();

			for (Map.Entry<String, List<Campo>> pair : mapaFields.entrySet()) {
				document.addTitle(pair.getKey());
				document.add(new Paragraph(pair.getKey() + "\n\n"));

				
				PdfPTable tabela = new PdfPTable(2);
				createTable(tabela);
				
				for (Campo campo : pair.getValue()) {
					fillTable(campo.getValue(), tabela);
					fillTable(campo.getType(), tabela);
				}
				document.add(tabela);
			}
			JOptionPane.showMessageDialog(null, "Arquivo PDF criado com sucesso");
			document.close();
		} catch (DocumentException | FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void createTable(PdfPTable table) throws DocumentException {
		
		Phrase phrase = new Phrase("fields");
		PdfPCell cel1 = new PdfPCell(phrase);
		cel1.setColspan(0);
		
		Phrase phrase2 = new Phrase("types");
		PdfPCell cel2 = new PdfPCell(phrase2);
		cel1.setColspan(1);
		
		table.addCell(cel1);
		table.addCell(cel2);
		
		table.setWidths(new float[] {20f, 10f});

	}

	public void fillTable(String value, PdfPTable table) throws DocumentException {
		
		PdfPCell cel1 = new PdfPCell(new Phrase(value));
		cel1.setColspan(0);
		
		table.addCell(value);
	}
}
