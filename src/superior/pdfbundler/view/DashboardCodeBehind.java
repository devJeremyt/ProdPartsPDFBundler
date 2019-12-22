package superior.pdfbundler.view;

import java.io.File;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import superior.pdfbundler.datatier.CSVReader;
import superior.pdfbundler.model.DirectoryScanner;
import superior.pdfbundler.model.Manager;
import superior.pdfbundler.model.Part;
import superior.pdfbundler.resources.ExceptionMessages;

public class DashboardCodeBehind {
    @FXML
    private Button importButton;

    @FXML
    private TableView<Part> partsTable;
    
    @FXML
    private TableColumn<Part, String> partNumberColumn;

    @FXML
    private TableColumn<Part, Boolean> drawingFoundColumn;

    @FXML
    private TextField csvFileLocation;

    @FXML
    private Button exportButton;
    
    private Manager manager;
    private DirectoryScanner dirscan;
    private File csvFile;
    private static final String searchDir = "C:\\";
    private ArrayList<Part> partsList;
    
    
    public void initialze() {
    	this.manager = new Manager();
    }
	
    /**
     * Sets the File that the parts are in and updates csv file location textbox
     */
	public void importCSV() {
		FileChooser chooser = new FileChooser();	
		this.csvFile = chooser.showOpenDialog(this.csvFileLocation.getScene().getWindow());
		this.csvFileLocation.setText(this.csvFile.getAbsolutePath());
		this.partsList = CSVReader.readPartsList(this.csvFile);
		this.dirscan = new DirectoryScanner(new File(searchDir), partsList);
		this.populatePartsTable();
		
	}
	
	private void populatePartsTable() {
		this.partsTable.setItems(FXCollections.observableArrayList(this.partsList));
		this.partNumberColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("nameProperty"));
		this.drawingFoundColumn.setCellValueFactory(new PropertyValueFactory<Part, Boolean>("foundProperty"));
	}

	public void exportPDF() {
		this.csvFile = new File(this.csvFileLocation.getText());
		if(this.csvFile.exists()) {
			
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText(ExceptionMessages.CSVREADFAILED);
			alert.setHeaderText(ExceptionMessages.CSVNOTFOUND);
			alert.setTitle(ExceptionMessages.CSVNOTFOUND);
			alert.showAndWait();
		}
	}
}
