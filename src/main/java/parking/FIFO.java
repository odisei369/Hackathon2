package parking;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

import parking.GateEvent;

import java.util.stream.Collectors;


// class GateEvent{
//     Vehicle vehicle;
//     Route route;
//     int start;
//     int end;
//     boolean loading;
//     boolean unload;
//     Gate gate;

//     public GateEvent(Vehicle vehicle, int start, Boolean load, Route route, Gate gate){
//         this.vehicle = vehicle;
//         this.route = route;
//         this.gate = gate;
//         this.start = start;
//         this.loading = load;
//         this.end = start + vehicle.numberOfPallet;
//     }
// }

class FIFO{
    private int currentRoute = 0;
    private FIFOVehicle[] vehicles;
    private Gate[] gates;
    private Route[] routes;
    private List<ChangeEvent> changeEvents = new ArrayList<>();
    private List<GateEvent> gateEvents = new ArrayList<>();


    public FIFO(Route[] routes, Gate[] gates, FIFOVehicle[] vehicles){
        this.routes = routes;
        this.gates = gates;
        this.vehicles = vehicles;
        eventAt(0, 11, 50);
    }

    public void eventAt(int vehicleId, int time, int addedTime){
            changeEvents.add(new ChangeEvent(vehicleId, time, addedTime));
    }

    private boolean tryLoadTruck(int vehicleId, int timestamp){
        for(int gateId = 0; gateId < gates.length; gateId++){
            if(gates[gateId].busyUntil <= timestamp){
                gates[gateId].busyUntil = timestamp + vehicles[vehicleId].numberOfPallet * 1;
                Route route = routes[currentRoute++];
                GateEvent gateEvent = new GateEvent();
                gateEvent.setRoute(route);
                gateEvent.setVehicle(vehicles[vehicleId].getVehicle());
                gateEvent.setLoading(true);
                gateEvent.setGate(gates[gateId]);
                gateEvent.setStart(timestamp);
                gateEvent.setDuration(timestamp + vehicles[vehicleId].numberOfPallet * 1);
                gateEvents.add(gateEvent);
                vehicles[vehicleId].route = route;
                vehicles[vehicleId].status = "loading";
                vehicles[vehicleId].currentTakenGate = gates[gateId];
                System.out.println("vehicle " + vehicleId + " loading");
                route.start = timestamp + vehicles[vehicleId].numberOfPallet;
                route.completionTime = route.takeSoMuchTime + route.start;
                return true;
            }
        }
        return false;
    }

    private boolean tryUnload(int vehicleId, int timestamp){
        for(int gateId = 0; gateId < gates.length; gateId++){
            if(gates[gateId].busyUntil <= timestamp){
                gates[gateId].busyUntil = timestamp + vehicles[vehicleId].numberOfPallet * 1;
                Route route = vehicles[vehicleId].route;
                vehicles[vehicleId].route = null;
                vehicles[vehicleId].status = "unloading";
                vehicles[vehicleId].currentTakenGate = gates[gateId];
                GateEvent gateEvent = new GateEvent();
                gateEvent.setRoute(route);
                gateEvent.setVehicle(vehicles[vehicleId].getVehicle());
                gateEvent.setLoading(false);
                gateEvent.setGate(gates[gateId]);
                gateEvent.setStart(timestamp);
                gateEvent.setDuration(timestamp + vehicles[vehicleId].numberOfPallet * 1);
                gateEvents.add(gateEvent);
                System.out.println("vehicle " + vehicleId + " unloading");
                return true;
            }
        }
        System.out.println("vehicle " + vehicleId + " waiting for unload");
        return false;
    }
    private void isUnloadEnded(int vehicleId, int timestamp){
        if(vehicles[vehicleId].currentTakenGate.busyUntil <= timestamp && !(currentRoute == routes.length)){
            vehicles[vehicleId].currentTakenGate = null;
            if(!tryLoadTruck(vehicleId, timestamp)){
                vehicles[vehicleId].status = "doing nothing";
            }
        }
    }

    public List<ChangeEvent> containsName(final List<ChangeEvent> list, final int time){
        return list.stream().filter(o -> o.getTime().equals(time)).collect(Collectors.toList());
    }

    public void simulate(){
        //routes are sorted from bigger one
        int timestamp = 0;
        while(timestamp != 480){
            List<ChangeEvent> events = containsName(changeEvents, timestamp);
            events.forEach(action -> {vehicles[action.vehicleId].route.completionTime += action.addedTime;});
            for(int vehicleId=0; vehicleId<vehicles.length; vehicleId++){
                if(vehicles[vehicleId].route == null && vehicles[vehicleId].status == "doing nothing" && !(currentRoute == routes.length)){
                    tryLoadTruck(vehicleId, timestamp);
                } else if(vehicles[vehicleId].status == "unloading"){
                    isUnloadEnded(vehicleId, timestamp);
                } else if(vehicles[vehicleId].status == "waiting for unload"){
                    tryUnload(vehicleId, timestamp);
                } else if(vehicles[vehicleId].route.start == timestamp){
                    vehicles[vehicleId].currentTakenGate = null;
                    vehicles[vehicleId].status = "on route";
                    System.out.println("vehicle " + vehicleId + " on route");
                } else if(vehicles[vehicleId].route.completionTime <= timestamp){
                    vehicles[vehicleId].status = "waiting for unload";
                    tryUnload(vehicleId, timestamp);
                }
                }
            timestamp++;
        }
    }
}

class ChangeEvent{
    int vehicleId;
    int time;
    int addedTime;
    ChangeEvent(int vehicleId, int time, int addedTime){
        this.vehicleId = vehicleId;
        this.time = time;
        this.addedTime = addedTime;
    }
    Integer getTime(){
        return time;
    }
}

class FIFOVehicle{
    int id;
    int numberOfPallet = 10;
    Route route;
    String status = "doing nothing";
    Gate currentTakenGate;
    FIFOVehicle(int id){
        this.id = id;
    }
    private Vehicle vehicle;
    
    Vehicle getVehicle() {
    	if (vehicle == null) 
    		vehicle = new Vehicle(10);
    	return vehicle;
    }
}

class Route{
    int id;
    int start;
    int takeSoMuchTime;
	int completionTime;
    Route(int id, int bur){
        this.id = id;
        takeSoMuchTime = bur;
    }
}

class Gate{
    int id;
    int busyUntil = 0;
    Gate(int id){
        this.id = id;
    }

}
// public class MainApp {

//     public static void main(String[] args){
// 		Scanner s = new Scanner(System.in);
// 		System.out.println("Enter the number of routes:");
// 		int n = s.nextInt();

//         Route[] myRoutes = new Route[n];
// 		for(int i=0;i<n;i++){
// 			System.out.println("Enter time for route " + i +": ");
// 			int bur = s.nextInt();
// 			myRoutes[i] = new Route(i,bur);
// 		}

//         System.out.println("Enter the number of gates:");
//         int numOfGates = s.nextInt();
//         Gate[] myGates = new Gate[numOfGates];
//         for(int i=0;i<numOfGates;i++){
// 			myGates[i] = new Gate(i);
// 		}

//         System.out.println("Enter the number of vehicles:");
//         int numOfVehicles = s.nextInt();
//         Vehicle[] myVehicles = new Vehicle[numOfVehicles];
//         for(int i=0;i<numOfVehicles;i++){
// 			myVehicles[i] = new Vehicle(i);
// 		}

//         FIFO fifo = new FIFO(myRoutes, myGates, myVehicles);
//         fifo.simulate();
//     }

// }