package superior.pdfbundler.model;

import java.util.ArrayList;

public class Orders {
	private ArrayList<SalesOrder> orders;

	public Orders() {
		super();
		this.orders = new ArrayList<SalesOrder>();
	}
	
	public boolean contains(String number) {
		for (SalesOrder order : this.orders) {
			if (order.getNumber().equals(number)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean add(SalesOrder order) {
		if (order != null) {
			return this.orders.add(order);
		}
		return false;
	}
	
	public SalesOrder getOrder(String number) {
		for (SalesOrder order : this.orders) {
			if (order.getNumber().equals(number)) {
				return order;
			}
		}
		return null;
	}
	
	public ArrayList<SalesOrder> getAllOrders() {
		return this.orders;
	}
	
	public ArrayList<Part> flattenPartList() {
		if (this.orders == null) {
			throw new IllegalArgumentException("Orders cannot be null");
		}
		ArrayList<Part> parts = new ArrayList<Part>();
		for (SalesOrder order : this.orders) {
			for (Part part : order.getParts()) {
				if (!parts.contains(part)) {
					parts.add(part);
				}
			}
		}
		return parts;
	}
	
	public ArrayList<Part> fullPartList() {
		if (this.orders == null) {
			throw new IllegalArgumentException("Orders cannot be null.");
		}
		ArrayList<Part> parts = new ArrayList<Part>();
		for (SalesOrder order : this.orders) {
			for (Part part : order.getParts()) {
				parts.add(part);
			}
		}
		return parts;
	}
	
	public void updateOrdersParts(ArrayList<Part> parts) {
		for (Part part : parts) {
			for (SalesOrder order : this.orders) {
				for (Part orderPart : order.getParts()) {
					if (part.equals(orderPart)) {
						orderPart.addFileLocations(part.getFileLocations());
						orderPart.setFound(part.getFoundBool());
					}
				}
			}
		} 
	}
	

}
