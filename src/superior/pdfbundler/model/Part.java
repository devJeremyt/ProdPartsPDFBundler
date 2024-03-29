package superior.pdfbundler.model;

import java.util.ArrayList;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import superior.pdfbundler.resources.ExceptionMessages;

/**
 * 
 * The Part Class
 * 
 * @author Jeremy Trimble
 * @version 1.0
 *
 */
public class Part {
	
	private ArrayList<String> fileLocations;
	private StringProperty name;
	private BooleanProperty found;
	private StringProperty orderNumber;
	
	public Part(String name, String orderNumber) {
		if (name == null) {
			throw  new IllegalArgumentException(ExceptionMessages.NAMECANNOTBENULL);
		}
		
		this.name = new SimpleStringProperty(name);
		this.found = new SimpleBooleanProperty(false);
		this.orderNumber = new SimpleStringProperty(orderNumber);
		
		this.fileLocations = new ArrayList<String>();
	}

	/**
	 * Returns the file location
	 * 
	 * @return the location of the file for the part
	 */
	public ArrayList<String> getFileLocations() {
		return this.fileLocations;
	}


	public void addFileLocation(String fileLocation) {
		this.fileLocations.add(fileLocation);
	}
	
	public Boolean addFileLocations(ArrayList<String> locations) {
		return this.fileLocations.addAll(locations);
	}

	/**
	 * Return if the file is found or not
	 * 
	 * 
	 * @return true if the file was found, else false
	 */
	public String getFound() {
		if (this.found.get()) {
			return "Found";
		} else {
			return "Not Found";
		}
		
	}
	
	public Boolean getFoundBool() {
		return this.found.get();
	}

	
	/**
	 * Sets if the part is found
	 * 
	 * @param found if the part is found or not
	 */
	public void setFound(boolean found) {
		this.found.setValue(found);
	}

	/**
	 * Returns the name of the part
	 * 
	 * 
	 * @return the name of the part
	 */
	public String getName() {
		return this.name.get();
	}
	
	public String getOrderNumber() {
		return this.orderNumber.get();
	}
	
	@Override
	public String toString() {
		return this.name.get();
	}

	
	@Override
	public boolean equals(Object obj) {
		Part cast = (Part) obj;
		if (cast.toString().equals(this.toString())) {
			return true;
		}
		
		return false;
	}

}
