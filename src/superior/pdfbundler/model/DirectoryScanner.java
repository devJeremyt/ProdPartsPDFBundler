package superior.pdfbundler.model;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import superior.pdfbundler.resources.ExceptionMessages;

/**
 * The DirectoryScanner Class
 * 
 * @author Jeremy Trimble
 * @version 1.0
 * @date 12/14/2019
 *
 */
public class DirectoryScanner {
	private Queue<File> queue;
	private ArrayList<Part> partsList;
	private File dir;
	
	/**
	 * Creates a new DirectoryScanner
	 * 
	 * @param directory
	 * @param listOfParts
	 */
	public DirectoryScanner(File directory, ArrayList<Part> listOfParts) {
		if (directory == null || !directory.exists()) {
			throw new IllegalArgumentException(ExceptionMessages.DIRECTORYNOTFOUND);
		}
		if (listOfParts.isEmpty()) {
			throw new IllegalArgumentException(ExceptionMessages.LISTCONTAINSNOPARTS);
		}
		this.dir = directory;
		this.partsList = listOfParts;
		this.queue = new LinkedList<File>();
	}

	public void searchForParts(){
		this.queue.add(this.dir);
		while(!this.queue.isEmpty() && this.queue.peek() != null) {
			File current = this.queue.remove();
			if(current.isDirectory() && current.listFiles() != null) {
				for(File file : current.listFiles()) {
					this.queue.add(file);
				}
			} else {
				for(Part part : this.partsList) {
					if(current.getName().contains(part.getName())) {
						part.setFileLocation(current.getAbsolutePath());
						part.setFound(true);
					}
				}	
			}
		}	
	}
}
