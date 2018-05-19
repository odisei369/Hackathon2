package parking;

import java.util.*;

public class Terminal {

	private List<Vehicle> listOfVehicles;
	private List<RouteEvent> listOfRouteEvents;
	private List<GateEvent> listOfGateEvents;
	
	static private Terminal terminal;
	
	private Terminal() {
		Vehicle[] elements = {
				new Vehicle(10), new Vehicle(30), new Vehicle(40)
		};
		listOfVehicles = Arrays.asList(elements);
		listOfRouteEvents = new ArrayList<>();

		listOfGateEvents = new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			RouteEvent newElement = new RouteEvent(i);
			if (i%5==0) {
				newElement.setVehicle(listOfVehicles.get(1));
				listOfVehicles.get(1).addRoute(newElement);
			}
			listOfRouteEvents.add(newElement);
			if (i%3==0)
			{
				GateEvent newGateElement = new GateEvent();
				newGateElement.setGate(i);
				if (i%6==0) {
					Vehicle secondVeh = listOfVehicles.get(1);
					newGateElement.setVehicle(secondVeh);
					secondVeh.addGateEvent(newGateElement);
					listOfGateEvents.add(newGateElement);
				}
				
			}
		}
		
	}
	
	public static Terminal getTerminal() {
		if (terminal == null)
			terminal = new Terminal();
		return terminal;
	}
	
	public List<Vehicle> getListOfVehicles() {
		return listOfVehicles;
	}
}
