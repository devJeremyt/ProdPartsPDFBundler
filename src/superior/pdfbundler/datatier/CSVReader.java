package superior.pdfbundler.datatier;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import superior.pdfbundler.model.Orders;
import superior.pdfbundler.model.Part;
import superior.pdfbundler.model.SalesOrder;
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
		//ArrayList<SalesOrder> SOs = new ArrayList<SalesOrder>()
		try (Scanner scanner = new Scanner(file)) {
			scanner.nextLine();
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] fields = line.split(",");
				String name = fields[3];
				//Sales Order = new salesorder(fields[1])
				Part part = new Part(name, null);
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
	
	public static final Orders readSalesOrderList(File file) {
		if (file == null) {
			throw new IllegalArgumentException(ExceptionMessages.FILECANNOTBENULL);
		}
		Orders list = new Orders();
		try (Scanner scanner = new Scanner(file)) {
			scanner.nextLine();
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] fields = line.split(",");
				//Skip over line if the SO field is empty, for lines like totals at the end of the file
				if (fields[1].equals("")) {
					continue;
				}
				String number = fields[1];
				String partNumber = fields[2];
				Part part = new Part(partNumber, number);
				addPartsToOrders(list, number, part);
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

	private static void addPartsToOrders(Orders list, String number, Part part) {
		if (!list.contains(number)) {
			SalesOrder order = new SalesOrder(number);
			list.add(order);
			order.addPart(part);
		} else {
			SalesOrder order = list.getOrder(number);
			if (!order.getParts().contains(part)) {
				order.addPart(part);
			}
		}
	}
}
