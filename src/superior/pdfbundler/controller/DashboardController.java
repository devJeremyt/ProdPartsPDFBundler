package superior.pdfbundler.controller;

import java.io.File;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
import superior.pdfbundler.model.Part;
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
    private TableColumn<Part, String> partNumberColumn;

    @FXML
    private TableColumn<Part, String> drawingFoundColumn;

    @FXML
    private TextField csvFileLocation;

    @FXML
    private Button exportButton;
    
    private DirectoryScanner dirscan;
    private File csvFile;
    private static final String SEARCHDIR = new File(DashboardController.class.getName() + ".class").getAbsoluteFile().getParent();
    private ArrayList<Part> partsList;
    
	
    /**
     * Sets the File that the parts are in and updates csv file location textbox
     */
	public void importCSV() {
		System.out.println(DashboardController.SEARCHDIR);
		FileChooser chooser = new FileChooser();
		chooser.getExtensionFilters().add(new ExtensionFilter("CSV", "*.csv"));
		this.csvFile = chooser.showOpenDialog(this.csvFileLocation.getScene().getWindow());
		this.csvFileLocation.setText(this.csvFile.getAbsolutePath());
		this.partsList = CSVReader.readPartsList(this.csvFile);
		this.dirscan = new DirectoryScanner(new File(SEARCHDIR), this.partsList);
		
		this.dirscan.searchForParts();
		this.populatePartsTable();
		
	}
	
	private void populatePartsTable() {
		this.partNumberColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
		this.drawingFoundColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("found"));
		this.partsTable.setItems(FXCollections.observableArrayList(this.partsList));
	}

	public void exportPDF() {
		FileChooser chooser = new FileChooser();
		chooser.getExtensionFilters().add(new ExtensionFilter("PDF", "*.pdf"));
		chooser.setInitialFileName("SO0.pdf");
		chooser.setTitle("Save PDF Export");
		File file = chooser.showSaveDialog(this.exportButton.getScene().getWindow());
		
		System.out.println(file.getAbsolutePath());
		if (this.csvFile.exists()) {
			Merger merger = new Merger(file);
			merger.mergePartsPDFs(file, this.getPartsLocations());
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText(ExceptionMessages.CSVREADFAILED);
			alert.setHeaderText(ExceptionMessages.CSVNOTFOUND);
			alert.setTitle(ExceptionMessages.CSVNOTFOUND);
			alert.showAndWait();
		}
		
	}
	
	private ArrayList<String> getPartsLocations() {
		ArrayList<String> locations = new ArrayList<String>();
		for (Part part : this.partsList) {
			locations.addAll(part.getFileLocations());
		}
		
		return locations;
	}
}
