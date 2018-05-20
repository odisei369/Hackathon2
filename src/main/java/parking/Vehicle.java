package parking;

import java.util.*;
import java.util.stream.Collectors;

public class Vehicle {
	
	enum VehicleStatus {
		ON_TOUR, AT_GATE, WAITING
	}
	
	private static final int DEFAULT_CAPACITY = 20;
	
	private List<GateEvent> gateEventList;
	
	private int capacity;

	private int id;
	
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

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
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
	
	public List<HackathonEvent> getAllEvents() {
		List<HackathonEvent> result = new ArrayList<>(gateEventList);
		result.addAll(routeList);
		Comparator<HackathonEvent> comp = Comparator.comparing(e -> e.getStart());
		return result.stream().sorted(comp).collect(Collectors.toList());
	}
	public void newLists(){
		gateEventList = new ArrayList<>();
		routeList = new ArrayList<>();
	}
	
	
	

}
