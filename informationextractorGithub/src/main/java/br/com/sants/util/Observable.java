package br.com.sants.util;

public interface Observable {
	public void addObserver( String eventType, EventListener observer );
    public void removeObserver( String eventType, EventListener observer );
    public void notifyObservers(String eventType);

}
