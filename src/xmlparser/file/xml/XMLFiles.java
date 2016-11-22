package xmlparser.file.xml;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import javax.swing.JFileChooser;

public class XMLFiles {
	public String SearchPathForObjectsXML() throws IOException {
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

	public File[] finder(String dirName) {
		File dir = new File(dirName);
		return dir.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String filename) {
				return filename.endsWith(".object");
			}
		});

	}
}
