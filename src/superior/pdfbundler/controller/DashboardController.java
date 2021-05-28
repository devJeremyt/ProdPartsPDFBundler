package superior.pdfbundler.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import superior.pdfbundler.datatier.CSVReader;
import superior.pdfbundler.model.DirectoryScanner;
import superior.pdfbundler.model.Merger;
import superior.pdfbundler.model.Orders;
import superior.pdfbundler.model.Part;
import superior.pdfbundler.model.SalesOrder;
import superior.pdfbundler.resources.ExceptionMessages;

/**
 * The DashboardController Class
 * 
 * @author Jeremy Trimble
 * @version 1.0
 *
 */
public class DashboardController {
	
    @FXML
    private Button importButton;
   

    @FXML
    private TableView<Part> partsTable;
    @FXML
    private TableColumn<Part, String> soColumn;
    
    @FXML
    private TableColumn<Part, String> partNumberColumn;

    @FXML
    private TableColumn<Part, String> drawingFoundColumn;

    @FXML
    private Button exportButton;
    
    private DirectoryScanner dirscan;
    private File csvFile;
    private static final String SEARCHDIR = new File(DashboardController.class.getName() + ".class").getAbsoluteFile().getParent();
    private Orders orders;
    
	
    /**
     * Sets the File that the parts are in and updates csv file location textbox
     */
	public void importCSV() {
		FileChooser chooser = new FileChooser();
		chooser.getExtensionFilters().add(new ExtensionFilter("CSV", "*.csv"));
		this.csvFile = chooser.showOpenDialog(this.importButton.getScene().getWindow());
		this.orders = CSVReader.readSalesOrderList(this.csvFile);
		ArrayList<Part> allParts = this.orders.flattenPartList();
		this.dirscan = new DirectoryScanner(new File(SEARCHDIR), allParts);
		try {
			this.dirscan.nioSearchForParts();
		} catch (IOException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText(ExceptionMessages.CSVREADFAILED);
			alert.setHeaderText(ExceptionMessages.CSVNOTFOUND);
			alert.setTitle(ExceptionMessages.CSVNOTFOUND);
			alert.showAndWait();
		}
		this.orders.updateOrdersParts(allParts);
		this.populatePartsTable();
		
		this.showCompletionAlert();
	}

	
	private void populatePartsTable() {
		this.partNumberColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
		this.drawingFoundColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("found"));
		this.soColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("orderNumber"));
		this.partsTable.setItems(FXCollections.observableArrayList(this.orders.fullPartList()));
	}
	
	
//	public void refreshCSV() {
//		this.csvFile = new File(this.csvFileLocation.getText());
//		this.orders = CSVReader.readSalesOrderList(this.csvFile);
////		this.dirscan.researchForParts(this.orders);
//		this.populatePartsTable();
//		
//		this.showCompletionAlert();
//			
//	}

	public void exportPDF() {		
		DirectoryChooser chooser = new DirectoryChooser();
		chooser.setTitle("Select Export Folder");
		File file = chooser.showDialog(this.exportButton.getScene().getWindow());
		if (this.csvFile.exists()) {
			for (SalesOrder order : this.orders.getAllOrders()) {
				if (!order.getPartLocations().isEmpty()) {
					
					Merger merger = new Merger();
					File finalFile = new File(file.getAbsoluteFile() + "//" + order.getNumber() + ".pdf");
					merger.mergePartsPDFs(finalFile, order.getPartLocations());
				}
			}
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setContentText("The pdf has been created.");
			alert.setHeaderText("PDF Created");
			alert.setTitle("PDF Created");
			alert.showAndWait();
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText(ExceptionMessages.CSVREADFAILED);
			alert.setHeaderText(ExceptionMessages.CSVNOTFOUND);
			alert.setTitle(ExceptionMessages.CSVNOTFOUND);
			alert.showAndWait();
		}
		
	}
	
	private void showCompletionAlert() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setContentText("The search has completed.");
		alert.setHeaderText("Search Complete");
		alert.setTitle("Search Complete");
		alert.showAndWait();
	}
	
}
