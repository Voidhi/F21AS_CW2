package models;

import java.util.LinkedList;

public class MyData implements Observable{
	// Singleton :
	private static MyData myInstance;

	private static final String csvPath = "";
	private SharedQueue myQueue;
	
	/**
	 * Private constructor - Thread-safe singleton
	 * @param path
	 */
	private MyData(String path) {
		// TODO : init with csv files
	}
	/**
	 * Public singleton accessor - Thread-safe singleton
	 * @return unique instance
	 */
	public static MyData getInstance() {
		if (myInstance == null) 
			synchronized(MyData.class) {
				if (myInstance == null)
					myInstance = new MyData(csvPath);
			}
		return myInstance;
	}
	
	
	
	/**
	 * Observator/MVC pattern
	 */
	public void notifyObservers() {}
}
