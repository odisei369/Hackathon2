package parking;

public class RouteEvent extends HackathonEvent {

	private GateEvent gateEvent;
	
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
	
	
	
}
  