package models;

import java.util.List;

import views.Observers;

public interface Observable {	
	public void addObserver(Observers o);
	public void notifyObservers();
}
