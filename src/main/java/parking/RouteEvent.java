package parking;

public class RouteEvent extends HackathonEvent {


	
	private Vehicle vehicle;
	private int Length;
	private GateEvent Gate;
	
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
  