package br.com.sants.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventManager implements br.com.sants.util.Observable{
    Map<String, List<EventListener>> listeners = new HashMap<>();

    public EventManager(String... operations) {
        for (String operation : operations) {
            this.listeners.put(operation, new ArrayList<>());
        }
    }

	@Override
	public void addObserver(String eventType, EventListener observer) {
		// TODO Auto-generated method stub
		List<EventListener> users = listeners.get(eventType);
        users.add(observer);
	}

	@Override
	public void removeObserver(String eventType, EventListener observer) {
		// TODO Auto-generated method stub
		List<EventListener> users = listeners.get(eventType);
        users.remove(observer);
	}

	@Override
	public void notifyObservers(String eventType) {
		// TODO Auto-generated method stub
		List<EventListener> users = listeners.get(eventType);
        for (EventListener listener : users) {
            listener.update(eventType);
        }
	}

    
}
