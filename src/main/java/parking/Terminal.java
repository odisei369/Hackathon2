package parking;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Terminal {

	public static final int TOTAL_GATES_NO = 30;
	public static final int TOTAL_VEHICLES_NO = 100;
	public static final int TOTAL_ROUTES_NO = 600;

	private int totalGates;
	private List<Vehicle> listOfVehicles;
	private List<RouteEvent> listOfRouteEvents;
	private List<GateEvent> listOfGateEvents;
	private FIFO fifo;
	static private Terminal terminal;

	private int randomInt(int min, int max){
		return ThreadLocalRandom.current().nextInt(min, max + 1);
	}

	private void initializeFifoWithRandomData(){
		Route[] routes = new Route[TOTAL_ROUTES_NO];
		for (int i = 0; i<routes.length; i++){
			routes[i] = new Route(i, randomInt(5,40));
		}
		Gate[] gates = new Gate[TOTAL_GATES_NO];
		for (int i = 0; i<gates.length; i++){
			gates[i] = new Gate(i);
		}
		listOfVehicles = new ArrayList<>();
		FIFOVehicle[] vehicles = new FIFOVehicle[TOTAL_VEHICLES_NO];
		for (int i = 0; i<vehicles.length; i++){
			vehicles[i] = new FIFOVehicle(i, randomInt(5, 15));
			listOfVehicles.add(new Vehicle(vehicles[i].numberOfPallet));
		}
		listOfGateEvents = new ArrayList<>();
		listOfRouteEvents = new ArrayList<>();
		fifo = new FIFO(routes, gates, vehicles, listOfGateEvents, listOfRouteEvents, listOfVehicles);
		fifo.simulate();
	}

	private Terminal() {
		initializeFifoWithRandomData();
		System.out.println("number of gate events: " + listOfGateEvents.size());
		System.out.println("number of route events: " + listOfRouteEvents.size());
		System.out.println("number of vehicle events: " + listOfVehicles.size());


//		Vehicle[] elements = {
//				new Vehicle(10), new Vehicle(30), new Vehicle(40)
//		};
//		listOfVehicles = Arrays.asList(elements);
//		listOfRouteEvents = new ArrayList<>();
//
//		listOfGateEvents = new ArrayList<>();
//		for (int i = 0; i < 100; i++) {
//			RouteEvent newElement = new RouteEvent(i);
//			if (i%5==0) {
//				newElement.setVehicle(listOfVehicles.get(1));
//				listOfVehicles.get(1).addRoute(newElement);
//			}
//			listOfRouteEvents.add(newElement);
//			if (i%3==0)
//			{
//				GateEvent newGateElement = new GateEvent();
//				newGateElement.setGate(i);
//				if (i%6==0) {
//					Vehicle secondVeh = listOfVehicles.get(1);
//					newGateElement.setVehicle(secondVeh);
//					secondVeh.addGateEvent(newGateElement);
//					listOfGateEvents.add(newGateElement);
//				}
//
//			}
//		}
		
	}
	
	public static Terminal getTerminal() {
		if (terminal == null)
			terminal = new Terminal();
		return terminal;
	}
	
	
	
	public List<Vehicle> getListOfVehicles() {
		return listOfVehicles;
	}
	public List<HackathonEvent> getAllEvents() {
		List<HackathonEvent> result = new ArrayList<>(listOfRouteEvents);
		result.addAll(listOfGateEvents);
		return result;
	}
	
	public void applyDelay(int id, int delayDur, int delayStart) {
		fifo.eventAt(id, delayStart, delayDur);
		fifo.simulate();
		
	}
}
