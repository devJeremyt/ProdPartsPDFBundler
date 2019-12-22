package superior.pdfbundler.model;

import java.io.File;
import java.util.ArrayList;

import superior.pdfbundler.datatier.CSVReader;

/**
 * The Manager Class
 * 
 * @author Jeremy Trimble
 * @version 1.0
 * @date 12/14/2019
 *
 */
public class Manager {
	
	ArrayList<Part> parts;
	
	public Manager() {
		this.parts = new ArrayList<Part>();
	}
	
	public void loadCSVFile(File file) {
		this.parts = CSVReader.readPartsList(file);
	}

}
