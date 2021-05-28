package superior.pdfbundler.model;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Consumer;
import java.util.stream.Stream;

import superior.pdfbundler.resources.ExceptionMessages;

/**
 * The DirectoryScanner Class
 * 
 * @author Jeremy Trimble
 * @version 1.0
 *
 */
public class DirectoryScanner {
	private Queue<File> queue;
	private ArrayList<Part> partsList;
	private File dir;
	private ArrayList<File> retainedList;
	
	/**
	 * Creates a new DirectoryScanner
	 * 
	 * @param directory
	 * @param listOfParts
	 */
	public DirectoryScanner(File directory, ArrayList<Part> listOfParts) {
		if (directory == null) {
			throw new IllegalArgumentException(ExceptionMessages.DIRECTORYNOTFOUND);
		}
		if (listOfParts.isEmpty()) {
			throw new IllegalArgumentException(ExceptionMessages.LISTCONTAINSNOPARTS);
		}
		this.dir = directory;
		
		this.partsList = listOfParts;
		this.queue = new LinkedList<File>();
		this.retainedList = new ArrayList<File>();
	}

	public void searchForParts() {
		this.queue.add(this.dir);
		while (!this.queue.isEmpty() && this.queue.peek() != null) {
			File current = this.queue.remove();
			if (current.isDirectory() && current.listFiles() != null) {
				for (File file : current.listFiles()) {
					this.queue.add(file);
				}
			} else {
				this.retainedList.add(current);
				for (Part part : this.partsList) {
					System.out.println(part.getName() + ":" + current.getName());
					if (current.getName().toLowerCase().contains(part.getName().toLowerCase())) {
						part.addFileLocation(current.getAbsolutePath());
						part.setFound(true);
					}
				}	
			}
		}	
	}
	
	public void nioSearchForParts() throws IOException {
		Consumer<Path> path = p -> {
			File current = p.toFile();
			if (current.isFile()) {
				for (Part part : this.partsList) {
					if (current.getName().toLowerCase().contains(part.getName().toLowerCase()) && current.getName().endsWith("pdf")) {
						part.addFileLocation(current.getAbsolutePath());
						part.setFound(true);
					}
				}
				
			}
		};
		try (Stream<Path> filePath = Files.walk(Paths.get(this.dir.getPath()))) {
			filePath.forEach(path);
		} catch (Exception ex) {
			System.out.println(ex);
		}
		
	}
	
	/**Leaving in for no good reason**/
	
//	public void researchForParts(ArrayList<Part> listOfParts) {
//		this.partsList = listOfParts;
//		
//		for (File current : this.retainedList) {
//			for (Part part : this.partsList) {
//				System.out.println(part.getName() + ":" + current.getName());
//				if (current.getName().toLowerCase().contains(part.getName().toLowerCase())) {
//					part.addFileLocation(current.getAbsolutePath());
//					part.setFound(true);
//				}
//			}
//		}
//	}
//	
	
//	public void newSearchForParts() {
//		this.queue.add(this.dir);
//		while (!this.queue.isEmpty() && this.queue.peek() != null) {
//			File current = this.queue.remove();
//			File[] currentDir = current.listFiles();
//			if (current.isDirectory() && currentDir != null) {
//				for (File file : currentDir) {
//					this.queue.add(file);
//				}
//			} else {
//				for (Part part : this.partsList) {
//					System.out.println(part.getName() + ":" + current.getName());
//					if (current.getName().toLowerCase().contains(part.getName().toLowerCase())) {
//						part.addFileLocation(current.getAbsolutePath());
//						part.setFound(true);
//					}
//				}	
//			}
//		}	
//	}
	
	
//	public void newThreadedSearch() {
//		ExecutorService exe = Executors.newWorkStealingPool();
//		
//		DirectoryScanner.this.processFolder(this.dir.toPath().toString(), exe);
//	}
//	
//	private void processFolder(String inputPath, ExecutorService exe) {
//		System.out.println(inputPath);
//		File inputFolder = new File(inputPath);
//		
//		for (String filename : inputFolder.list()) {
//			String filePath = inputFolder.toPath().resolve(filename).toString();
//			if (new File(filePath).isDirectory()) {
//				exe.execute(new Runnable() {
//					public void run() {
//						DirectoryScanner.this.processFolder(filePath, exe);
//					}
//				});
//			} else {
//				exe.execute(new Runnable() {
//					public void run() {
//						for (Part part : DirectoryScanner.this.partsList) {
//							System.out.println(part.getName() + ":" + filename);
//							if (filename.toLowerCase().contains(part.getName().toLowerCase())) {
//								part.addFileLocation(filePath);
//								part.setFound(true);
//							}
//						}
//					}
//				});
//			}
//		}
//		
//	}

//	public void threadedSearch() {
//		ExecutorService exe = Executors.newFixedThreadPool(4);
//		Queue<File> threadedQueue = new ConcurrentLinkedQueue<File>();
//		System.out.println("Started Searched");
//		
//		threadedQueue.add(this.dir);
//		while (!threadedQueue.isEmpty() && threadedQueue.peek() != null) {
//			File current = threadedQueue.remove();
//			System.out.println(current + " removed from queue");
//			if (current.isDirectory() && current.listFiles() != null) {
//				exe.execute(new Runnable() {
//					public void run() {
//						for (File file : current.listFiles()) {
//							threadedQueue.add(file);
//							System.out.println("Added" + file);
//						}
//					
//					};
//				
//				});
//			} else {
//				exe.execute(new Runnable() {
//					public void run() {
//						System.out.println("Runnable Started");
//						for (Part part : DirectoryScanner.this.partsList) {
//							System.out.println(part.getName() + ":" + current.getName());
//							if (current.getName().toLowerCase().contains(part.getName().toLowerCase())) {
//								part.addFileLocation(current.getAbsolutePath());
//								part.setFound(true);
//							}
//						}
//					}
//				});
//					
//			}
//		}
//	}
//	
//	public void anotherthreadedSearch() {
//		ExecutorService exe = Executors.newFixedThreadPool(4);
//		Queue<File> threadedQueue = new ConcurrentLinkedQueue<File>();
//		System.out.println("Started Searched");
//		
//		threadedQueue.add(this.dir);
//		
//		this.scanSingleDirectory(threadedQueue.remove(), exe, threadedQueue);
//	}
//
//	private void scanSingleDirectory(File current, ExecutorService exe, Queue<File> threadedQueue) {
//		System.out.println(current + " removed from Queue");
//		exe.execute(new Runnable() {
//			public void run() {
//				if (current.isDirectory() && current.listFiles() != null) {
//					for (File file : current.listFiles()) {
//						threadedQueue.add(file);
//						System.out.println("Added" + file);
//					}
//				} else {
//					for (Part part : DirectoryScanner.this.partsList) {
//						System.out.println(part.getName() + ":" + current.getName());
//						if (current.getName().toLowerCase().contains(part.getName().toLowerCase())) {
//							part.addFileLocation(current.getAbsolutePath());
//							part.setFound(true);
//						}
//					}
//				}
//				
//				System.out.println(!threadedQueue.isEmpty());
//				System.out.println(threadedQueue.peek() != null);
//				if (!threadedQueue.isEmpty() && threadedQueue.peek() != null) {
//					System.out.println("Called again");
//					DirectoryScanner.this.scanSingleDirectory(threadedQueue.remove(), exe, threadedQueue);
//				}
//			}
//		});
//	}
	
}

