package models;

public class MyData implements Observable{
	// Singleton :
	private static MyData myInstance;
	// TODO : add here other data..
	private static final String csvPath = "";
	
	
	
	
	/**
	 * Private constructor - Thread-safe singleton
	 * @param path
	 */
	private MyData(String path) {/*Initialisation, lecture des csv,,,*/}
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
	public void notifyObservers() {
		
	}
}
