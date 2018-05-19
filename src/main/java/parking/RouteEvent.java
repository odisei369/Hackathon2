package parking;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class RouteEvent extends HackathonEvent {

<<<<<<< HEAD

	
	private Vehicle vehicle;
	private int Length;
	private GateEvent Gate;
=======
	private GateEvent gateEvent;
	
	private int duration;
	
	public RouteEvent() {
		
	}
	
	public RouteEvent(int length) {
		setDuration(length);
	}
	
	@Override
	public String getType() {

		return "RouteEvent";
	}
	
	public GateEvent getGateEvent() {
		return gateEvent;
	}

	public void setGateEvent(GateEvent gateEvent) {
		this.gateEvent = gateEvent;
	}

	@Override
	public int getDuration() {
		return duration;
	}
	
	public void setDuration(int length) {
		duration = length;
	}
	
	public void elongate(int howMuch) {
		duration += howMuch;
	}
	
	public int getStart() {
		return gateEvent.getEnd();
	}
	
	
	
>>>>>>> 7ce9d57b03919cc9f129e5751e8512243df4f29f
	
	private static int DEFAULT_ROUTE_LENGTH = 10;
	
	public RouteEvent()
	{
		this.Length = DEFAULT_ROUTE_LENGTH;
		vehicle = new Vehicle();
		Gate = new GateEvent();
		
	}
	
	public RouteEvent(Vehicle v)
	{
		this.Length = DEFAULT_ROUTE_LENGTH;
		vehicle=v;
		Gate= new GateEvent();
	}
	public RouteEvent(Vehicle v, GateEvent ge)
	{
		this.Length = DEFAULT_ROUTE_LENGTH;
		vehicle=v;
		Gate = ge;
	}
	
	
	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return "RouteEvent" ;
	}
	@Override
	public Vehicle getVehicle()
	{
		return this.vehicle;
	}
	@Override
	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}
	@Override 
	public int getEnd() {
		return Gate.getStart()+this.Length;
	}
	@Override
	public int getStart() {
		return Gate.getStart();
	}
}
  