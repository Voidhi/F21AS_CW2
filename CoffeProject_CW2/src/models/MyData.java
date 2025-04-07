package models;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import views.Observers;

public class MyData implements Observable{
	// Singleton :
	private static MyData myInstance;

	private static final String csvPath = "";
	private SharedQueue myQueue;
	private final List<Observers> observers = new ArrayList<>();
	
	
	public void addObserver(Observers o) {
	    observers.add(o);
	}
	
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
	public void notifyObservers() {
	    for (Observers o : observers) {
	        o.Update(); // will trigger updateQueue() and updateServers() in GUI
	    }
	}

}
