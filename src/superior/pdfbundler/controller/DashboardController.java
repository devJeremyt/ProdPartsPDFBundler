package superior.pdfbundler.controller;

import java.io.File;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import superior.pdfbundler.datatier.CSVReader;
import superior.pdfbundler.model.DirectoryScanner;
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
    private static final String SEARCHDIR = "C:\\";
    private ArrayList<Part> partsList;
    
	
    /**
     * Sets the File that the parts are in and updates csv file location textbox
     */
	public void importCSV() {
		FileChooser chooser = new FileChooser();	
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
		this.csvFile = new File(this.csvFileLocation.getText());
		if (this.csvFile.exists()) {
			System.out.println("Do something");
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText(ExceptionMessages.CSVREADFAILED);
			alert.setHeaderText(ExceptionMessages.CSVNOTFOUND);
			alert.setTitle(ExceptionMessages.CSVNOTFOUND);
			alert.showAndWait();
		}
		
	}
}
