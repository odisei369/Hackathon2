package parking;

import java.util.*;

public class Vehicle {
	
	enum VehicleStatus {
		ON_TOUR, AT_GATE, WAITING
	}
	
	private static final int DEFAULT_CAPACITY = 20;
	
	private List<GateEvent> gateEventList;
	
	private int capacity;
	
	private List<RouteEvent> routeList;
	
	public Vehicle(int capacity) {
		this.capacity = capacity;
		gateEventList = new ArrayList<>();
		routeList = new ArrayList<>();
	}
	
	public Vehicle() {
		capacity = DEFAULT_CAPACITY;
		gateEventList = new ArrayList<>();
		routeList = new ArrayList<>();
	}
	
	public VehicleStatus getStatusAtTime(int time) {
		return null; //to implement
	}
	
	public void addRoute(RouteEvent route) {
		routeList.add(route);
	}
	
	public void removeRoute(RouteEvent route) {
		routeList.remove(route);
	}
	
	public void addGateEvent(GateEvent gate) {
		gateEventList.add(gate);
	}
	
	public void removeGateEvent(GateEvent gate) {
		gateEventList.remove(gate);
	}
	

	public List<GateEvent> getGateEventList() {
		return gateEventList;
	}

	public int getCapacity() {
		return capacity;
	}

	public List<RouteEvent> getRouteList() {
		return routeList;
	}
	
	
	
	

}
