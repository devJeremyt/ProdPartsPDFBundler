package superior.pdfbundler.model;

import java.util.ArrayList;

public class SalesOrder {

	private ArrayList<Part> parts;
	private String number;
	
	
	
	
	public SalesOrder(String number) {
		super();
		this.number = number;
		this.parts = new ArrayList<Part>();
	}
	public ArrayList<Part> getParts() {
		return this.parts;
	}
	public void addPart(Part part) {
		if (part != null) {
			this.parts.add(part);
		}
	}
	
	public String getNumber() {
		return this.number;
	}
	
	public ArrayList<String> getPartLocations() {
		ArrayList<String> locations = new ArrayList<String>();
		for (Part part : this.parts) {
			locations.addAll(part.getFileLocations());
		}
		return locations;
	}
	
	@Override
	public boolean equals(Object obj) {
		SalesOrder cast = (SalesOrder) obj;
		if (cast.getNumber().equals(this.number)) {
			return true;
		}
		return false;
		
	}
		
}
