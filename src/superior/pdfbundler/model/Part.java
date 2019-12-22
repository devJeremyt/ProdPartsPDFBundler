package superior.pdfbundler.model;

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
 * @Date 12/22/2019
 *
 */
public class Part {
	
	private String fileLocation;
	private StringProperty name;
	private BooleanProperty found;
	
	public Part(String name) {
		if (name == null) {
			throw  new IllegalArgumentException(ExceptionMessages.NAMECANNOTBENULL);
		}
		
		this.name = new SimpleStringProperty(name);
		this.found = new SimpleBooleanProperty(false);
		
		this.fileLocation = null;
	}

	/**
	 * Returns the file location
	 * 
	 * @return the location of the file for the part
	 */
	public String getFileLocation() {
		return this.fileLocation;
	}

	/**
	 * Sets the file location to the string provided
	 * 
	 * @param fileLocation the absolute location of the file
	 */
	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}

	/**
	 * Return if the file is found or not
	 * 
	 * 
	 * @return true if the file was found, else false
	 */
	public String getFound() {
		if(this.found.get()==true) {
			return "Found";
		} else {
			return "Not Found";
		}
		
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
	
	public String toString() {
		return this.name.get();
	}
}
