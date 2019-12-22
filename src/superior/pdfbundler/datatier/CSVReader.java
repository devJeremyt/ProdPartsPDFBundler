package superior.pdfbundler.datatier;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import superior.pdfbundler.model.Part;
import superior.pdfbundler.resources.ExceptionMessages;

/**
 * The CSVReader class
 * 
 * @author Jeremy Trimble
 * @version 1.0
 * @date 12/14/2019
 *
 */
public class CSVReader {

	public static final ArrayList<Part> readPartsList(File file){
		if (file == null) {
			throw new IllegalArgumentException(ExceptionMessages.FILLCANNOTBENULL);
		}
		ArrayList<Part> list = new ArrayList<Part>();
		
		try (Scanner scanner = new Scanner(file)){
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] fields = line.split(",");
				String name = fields[0];
				Part part = new Part(name);
				list.add(part);
			}
		} catch (Exception e) {
			System.err.println(ExceptionMessages.CSVREADFAILED);
		}
		
		return list;
	}
}
