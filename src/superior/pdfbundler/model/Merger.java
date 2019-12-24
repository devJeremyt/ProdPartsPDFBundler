package superior.pdfbundler.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;

import superior.pdfbundler.resources.ExceptionMessages;

/**
 * The Merger Class
 * @author Jeremy Trimble
 * @version 1.0
 *
 */
public class Merger {
	
	private PDFMergerUtility merger;
	private PDDocument newPDF;
	
	
	/**
	 * The Merger Object
	 * 
	 * @param file the file that the pdfs will be saved to
	 */
	public Merger(File file) {
		if (file == null) {
			throw new IllegalArgumentException(ExceptionMessages.FILECANNOTBENULL);
		}
		this.merger = new PDFMergerUtility();
//		this.newPDF = new PDDocument();
//		try {
//			this.newPDF.save(file);
//			this.newPDF.close();
//		} catch (IOException e) {
//			System.err.println(ExceptionMessages.PDFNOTCREATED);
//			e.printStackTrace();
//		}
	}
	
	
	/**
	 * Merges the provided list of pdfs into the file provided
	 * 
	 * @precondition file != null && file.exist() && pdfLocation != null
	 * @postcondition all the PDF files in the list are merged and saved into the file
	 * 
	 * @param file the file which is to have the pdfs saved too it
	 * @param pdfLocations the list of file locations for the pdfs
	 */
	public void mergePartsPDFs(File file, ArrayList<String> pdfLocations) {
		if (file == null) {
			throw new IllegalArgumentException(ExceptionMessages.FILECANNOTBENULL);
		}
		if (pdfLocations == null) {
			throw new IllegalArgumentException(ExceptionMessages.PDFLOCATIONSNULL);
		}
		try {
			PDDocument doc = new PDDocument();
			doc.save(file);
			for (String current : pdfLocations) {
				if (current != null) {
					File newFile = new File(current);
					this.merger.addSource(newFile);
					System.out.println("Added" + newFile);
				}
			}
		
			this.merger.setDestinationFileName(file.getAbsolutePath());
			this.merger.mergeDocuments(null);
			
			doc.close();
			
		} catch (FileNotFoundException fnfe) {
			System.err.println(ExceptionMessages.FILENOTFOUND);
		} catch (IOException ioe) {
			System.err.println(ExceptionMessages.IOEXCEPTION);
		}
		
	}
}
