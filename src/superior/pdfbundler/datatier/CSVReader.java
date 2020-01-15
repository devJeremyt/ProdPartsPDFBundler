package superior.pdfbundler.datatier;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import superior.pdfbundler.model.Part;
import superior.pdfbundler.resources.ExceptionMessages;

/**
 * The CSVReader class
 * 
 * @author Jeremy Trimble
 * @version 1.0
 *
 */
public class CSVReader {

	public static final ArrayList<Part> readPartsList(File file) {
		if (file == null) {
			throw new IllegalArgumentException(ExceptionMessages.FILECANNOTBENULL);
		}
		ArrayList<Part> list = new ArrayList<Part>();
		
		try (Scanner scanner = new Scanner(file)) {
			scanner.nextLine();
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] fields = line.split(",");
				String name = fields[2];
				Part part = new Part(name);
				if (!list.contains(part)) {
					list.add(part);
				}
			}
		} catch (Exception e) {
			System.err.println(ExceptionMessages.CSVREADFAILED);
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("There isn't a CSV by the name of location provided. Check the spelling of the file location of the CSV being uploaded.");
			alert.setHeaderText("Invalid CSV");
			alert.setTitle("Invalid CSV");
			alert.showAndWait();
		}
		
		return list;
	}
}
