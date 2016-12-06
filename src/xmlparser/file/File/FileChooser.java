package xmlparser.file.File;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.swing.JFileChooser;

import xmlparser.file.object.Campo;

public class FileChooser {
	public String SearchPathForInputFiles() throws IOException {
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

	public String SearchPathForOutputFiles() throws IOException {
		JFileChooser chooser = new JFileChooser();
		chooser.setDialogTitle("Select Directory for Output Files!");
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int result = chooser.showOpenDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) {
			return chooser.getSelectedFile().getAbsolutePath();
		} else {
			return null;
		}
	}
	
	public File[] finder(String dirName, String extension) {
		File dir = new File(dirName);
		return dir.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String filename) {
				return filename.endsWith(extension);
			}
		});

	}
}
