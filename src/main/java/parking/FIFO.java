package parking;

import java.util.ArrayList;
import java.util.List;

import parking.GateEvent;

import java.util.stream.Collectors;

class FIFO{
    private int currentRoute = 0;
    private FIFOVehicle[] vehicles;
    private Gate[] gates;
    private Route[] routes;
    private List<ChangeEvent> changeEvents = new ArrayList<>();
    private List<GateEvent> gateEvents;
    private List<RouteEvent> routeEvents;
    private List<Vehicle> listOfVehicles;


    public FIFO(Route[] routes, Gate[] gates, FIFOVehicle[] vehicles, List<GateEvent> gateEvents, List<RouteEvent> routeEvents, List<Vehicle> listOfVehicles){
        this.routeEvents = routeEvents;
        this.gateEvents = gateEvents;
        this.listOfVehicles = listOfVehicles;
        this.routes = routes;
        this.gates = gates;
        this.vehicles = vehicles;
    }
    
    public void simulate(){
        gateEvents.clear();
        routeEvents.clear();
        clearData();
        int timestamp = 0;
        while(timestamp != 840){
            List<ChangeEvent> currentChangeEvents = getChangeEventsStartingAt(changeEvents, timestamp);
            int finalTimestamp = timestamp;
            currentChangeEvents.forEach(chEvent -> {
                if(vehicles[chEvent.vehicleId].route != null && finalTimestamp == chEvent.startTime)
                    vehicles[chEvent.vehicleId].route.completionTime += chEvent.duration;
                RouteEvent ro = listOfVehicles.get(chEvent.vehicleId).getRouteEventAt(finalTimestamp); //not affected by 'safety' if above
                ro.elongate(chEvent.duration);

            });
            for(int vehicleId=0; vehicleId<vehicles.length; vehicleId++){
                processVehicle(vehicleId,timestamp);
            }
            timestamp++;
        }
    }
    
    public void processVehicle(int vehicleId, int timestamp) {
    	if(isVehicleFreeAndRoutesAvailable(vehicleId)){
            tryLoadTruck(vehicleId, timestamp, -1);
        } else if(vehicles[vehicleId].status == "unloading"){
            isUnloadEnded(vehicleId, timestamp);
        } else if(vehicles[vehicleId].status == "waiting for unload"){
            tryUnload(vehicleId, timestamp);
        } else if(vehicles[vehicleId].route != null && vehicles[vehicleId].route.start == timestamp){
            vehicles[vehicleId].currentTakenGate = null;
            vehicles[vehicleId].status = "on route";
            System.out.println("vehicle " + vehicleId + " on route");
        } else if(vehicles[vehicleId].route != null && vehicles[vehicleId].route.completionTime <= timestamp){
            vehicles[vehicleId].status = "waiting for unload";
            tryUnload(vehicleId, timestamp);
        }
    }
    
    public boolean isVehicleFreeAndRoutesAvailable(int vehicleId) {
    	return vehicles[vehicleId].route == null && vehicles[vehicleId].status == "doing nothing" && !(currentRoute == routes.length);
    }

    public void addChangeEventIfValidChange(int vehicleId, int time, int addedTime){
            List<HackathonEvent> events = listOfVehicles.get(vehicleId).getAllEvents();
            events.forEach(event ->{
                if(event.getStart() < time && time < event.getEnd() && event.getType().equals(RouteEvent.className))
                    changeEvents.add(new ChangeEvent(vehicleId, time, addedTime));
                    simulate();
            });
    }

    private boolean tryLoadTruck(int vehicleId, int timestamp, int oldGateId){
        for(int gateId = 0; gateId < gates.length; gateId++){
            if(oldGateId >= 0)
                gateId = oldGateId; //checking if any gate of bigger index is free
            if(isGateFreeAtTime(gateId,timestamp)){
                assignNextRouteToVehAndGate(gateId,vehicleId,timestamp);
                return true;
            }
        }
        return false;
    }
    
    public void assignNextRouteToVehAndGate(int gateId, int vehicleId, int timestamp) {
    	gates[gateId].busyUntil = timestamp + vehicles[vehicleId].numberOfPallet * 1;
        Route route = routes[currentRoute++];
        GateEvent gateEvent = new GateEvent();
        gateEvent.setVehicle(vehicles[vehicleId].getVehicle());
        gateEvent.setLoadingTrue();
        gateEvent.setGate(gateId);
        gateEvent.setVehicleId(vehicleId);
        gateEvent.setStart(timestamp);
        gateEvent.setNumberOfPallets(vehicles[vehicleId].numberOfPallet);

        RouteEvent re = new RouteEvent();


        re.setDuration(route.takeSoMuchTime);
        re.setGateEvent(gateEvent);
        re.setVehicleId(vehicleId);
        Vehicle vh = listOfVehicles.get(vehicleId);
        vh.addGateEvent(gateEvent);
        vh.addRoute(re);
        routeEvents.add(re);
        gateEvents.add(gateEvent);
        vehicles[vehicleId].route = route;
        vehicles[vehicleId].status = "loading";
        vehicles[vehicleId].currentTakenGate = gates[gateId];
        System.out.println("vehicle " + vehicleId + " loading");
        route.start = timestamp + vehicles[vehicleId].numberOfPallet;
        route.completionTime = route.takeSoMuchTime + route.start;
    }
    
    public boolean isGateFreeAtTime(int gateId, int timestamp) {
    	return gates[gateId].busyUntil <= timestamp;
    }

    private boolean tryUnload(int vehicleId, int timestamp){
        for(int gateId = 0; gateId < gates.length; gateId++){
            if(isGateFreeAtTime(gateId,timestamp)){
                unloadVehAtGateAtTime(vehicleId,gateId,timestamp);
                return true;
            }
        }
        //if no free gates right now then:
        System.out.println("vehicle " + vehicleId + " waiting for unload");
        return false;
    }
    
    public void unloadVehAtGateAtTime(int vehicleId, int gateId, int timestamp) {
    	gates[gateId].busyUntil = timestamp + vehicles[vehicleId].numberOfPallet * 1;
        Route route = vehicles[vehicleId].route; //we don't seem to need to store this
        vehicles[vehicleId].route = null;
        vehicles[vehicleId].status = "unloading";
        vehicles[vehicleId].currentTakenGate = gates[gateId];
        GateEvent gateEvent = new GateEvent();
        gateEvent.setNumberOfPallets(vehicles[vehicleId].numberOfPallet);
        Vehicle vh = listOfVehicles.get(vehicleId);
        vh.addGateEvent(gateEvent);
        gateEvent.setVehicle(vehicles[vehicleId].getVehicle());
        gateEvent.setVehicleId(vehicleId);
        gateEvent.setUnloadingTrue();
        gateEvent.setGate(gateId);
        gateEvent.setStart(timestamp);
        gateEvents.add(gateEvent);
        System.out.println("vehicle " + vehicleId + " unloading");
    }
    
    private void isUnloadEnded(int vehicleId, int timestamp){
        if(vehicles[vehicleId].currentTakenGate.busyUntil <= timestamp && !(currentRoute == routes.length)){
            Gate gate  =  vehicles[vehicleId].currentTakenGate;
            vehicles[vehicleId].currentTakenGate = null;
            if(!tryLoadTruck(vehicleId, timestamp, gate.id)){
                vehicles[vehicleId].status = "doing nothing";
            }
        }
    }

    public List<ChangeEvent> getChangeEventsStartingAt(final List<ChangeEvent> list, final int time){
        return list.stream().filter(o -> o.getStartTime().equals(time)).collect(Collectors.toList());
    }

    private void clearData(){
        listOfVehicles.forEach(vehicle -> {
            vehicle.newLists();
        });
        currentRoute = 0;
        for (int i = 0; i< vehicles.length; i++){
            vehicles[i].route = null;
            vehicles[i].currentTakenGate = null;
            vehicles[i].status = "doing nothing";
        }
        for (int i = 0; i < gates.length; i++){
            gates[i].busyUntil = 0;
        }
    }


}

class ChangeEvent{
    int vehicleId;
    int startTime;
    int duration;
    ChangeEvent(int vehicleId, int time, int addedTime){
        this.vehicleId = vehicleId;
        this.startTime = time;
        this.duration = addedTime;
    }
    Integer getStartTime(){
        return startTime;
    }
}

class FIFOVehicle{
    int id;
    int numberOfPallet = 10;
    Route route;
    String status = "doing nothing";
    Gate currentTakenGate;
    FIFOVehicle(int id, int numberOfPallet){
        this.id = id;
        this.numberOfPallet = numberOfPallet;
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